package eu.lixko.jsoapy.soapy;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import eu.lixko.jsoapy.soapy.SoapySDRDevice.NativeStreamFormat;

public class SoapySDRStream implements AutoCloseable {
	
	protected final MemorySegment ptr;
	protected final SoapySDRDevice dev;
	protected final SoapySDRDeviceDirection direction; 
	protected final StreamFormat format;
	protected final List<NativeStreamFormat> channelFormats;
	/** May be null! */
	protected final List<Long> channels;
	protected final long numChannels;
	protected final SoapySDRKwargs args;
	
	protected long blockSize;
	protected Arena blockArena = null;
	/** Buffer containing all data -- numChannels * sizeof(sample) in size. */
	protected MemorySegment mem_block = null;
	protected MemorySegment mem_block_ptrs = null;
	
	protected final Arena paramArena = Arena.ofAuto();
	protected MemorySegment mem_flags = paramArena.allocate(ValueLayout.JAVA_INT);
	protected MemorySegment mem_timeNs = paramArena.allocate(ValueLayout.JAVA_LONG);
	protected MemorySegment mem_handle = paramArena.allocate(ValueLayout.ADDRESS);
	// ideally there would be only one direction per stream (I think), but what if...
	protected MemorySegment mem_directBufs;
	protected MemorySegment mem_readBufs;
	protected MemorySegment mem_writeBufs;
	
	protected LongBuffer buf_directBufs;
	protected LongBuffer buf_readBufs;
	protected LongBuffer buf_writeBufs;
	
	protected SoapySDRStream(
		SoapySDRDevice dev,
		MemorySegment ptr,
		SoapySDRDeviceDirection direction, 
		StreamFormat format, 
		long[] channels, 
		SoapySDRKwargs args
	) {
		this.dev = dev;
		this.direction = direction;
		this.format = format;
		if (channels == null) {
			this.channels = List.of();
			this.channelFormats = List.of(this.dev.getNativeStreamFromat(direction, 0));
		} else {
			this.channels = Arrays.stream(channels).boxed().collect(Collectors.toUnmodifiableList());
			this.channelFormats = Arrays.stream(channels).mapToObj(channel -> this.dev.getNativeStreamFromat(direction, channel)).collect(Collectors.toUnmodifiableList());
		}
		
		this.numChannels = channels != null ? channels.length : this.dev.getNumChannels(direction);
		this.args = args;
		this.mem_directBufs = paramArena.allocate(ValueLayout.ADDRESS, numChannels);
		this.mem_readBufs = paramArena.allocate(ValueLayout.ADDRESS, numChannels);
		this.mem_writeBufs = paramArena.allocate(ValueLayout.ADDRESS, numChannels);
		
		// TODO: We're using LongBuffers here -- that won't work on 32-bit machines.
		this.buf_directBufs = this.mem_directBufs.asReadOnly().asByteBuffer().asLongBuffer();
		this.buf_readBufs = this.mem_readBufs.asReadOnly().asByteBuffer().asLongBuffer();
		this.buf_writeBufs = this.mem_writeBufs.asReadOnly().asByteBuffer().asLongBuffer();
		
		if (ptr == null) {
			MemorySegment channelsPtr = null;
			if (channels != null) {
				// TODO: Same here, won't work on 32-bit machines (half of the pointers will be empty)
				channelsPtr = paramArena.allocateFrom(ValueLayout.JAVA_LONG, channels);
			}
			
			ptr = Device_h.SoapySDRDevice_setupStream(
				this.dev.getAddress(),
				direction.ordinal(),
				format.addr(), 
				channelsPtr,
				channels != null ? channels.length : 0,
				args != null ? args.getStruct() : null
			);
		}
		this.ptr = ptr;
		
		this.setBlockSize(40000); // 1 MiB by default
	}
	
	/**
	 * This can be used to "clone" a stream object, e. g. for the purpose of multi-threading. This class itself is not thread-safe,
	 * but the underlying Soapy driver may be (no guarantee).
	 * @param stream
	 */
	protected SoapySDRStream(
		SoapySDRStream stream
	) {
		this(
			stream.dev,
			stream.ptr,
			stream.direction,
			stream.format,
			stream.channels.stream().mapToLong(Long::longValue).toArray(),
			stream.args
		);
	}

	public SoapySDRDevice getDevice() {
		return this.dev;
	}
	
	public MemorySegment getAddress() {
		return this.ptr;
	}
	
	public SoapySDRDeviceDirection getDirection() {
		return this.direction;
	}
	
	/**
	 * This only applies to {@link #readStream} and {@link #writeStream}! Zero-copy buffers will be in the native format.
	 * @return
	 */
	public StreamFormat getFormat() {
		return this.format;
	}
	
	public NativeStreamFormat getNativeFormat(long channel) {
		return channelFormats.get((int) channel);
	}
	
	public List<Long> getChannels() {
		return this.channels;
	}
	
	public SoapySDRKwargs getArgs() {
		return this.args;
	}
	
	public long getBlockSize() {
		return this.blockSize;
	}
	
	/**
	 * Used for {@link #readStream} and {@link #writeStream}.
	 * @return The array of void* buffers numChannels in size, containing data either received, or ready to be transmitted.
	 */
	public MemorySegment getData(long channel) {
		long chs = this.format.byteSize() * this.blockSize;
		return this.mem_block.asSlice(chs * channel, chs);
	}
	
	/**
	 * Set the input buffer size (for the {@link #readStream} method) in samples.
	 * The resulting buffer will be sizeof(sample format) * numChannels * blockSize B long. 
	 * @param blockSize
	 */
	public void setBlockSize(long blockSize) {
		if (blockSize == this.blockSize && this.blockArena != null)
			return;
		
		this.blockSize = blockSize;
		
		if (this.blockArena != null) {
			blockArena.close();
		}
		
		blockArena = Arena.ofShared();
		if (this.mem_block_ptrs == null) {
			this.mem_block_ptrs = paramArena.allocate(this.numChannels * ValueLayout.ADDRESS.byteSize());
		}
		
		this.mem_block = blockArena.allocate(this.format.byteSize() * this.blockSize * this.numChannels);
		for (long i = 0; i < numChannels; i++) {
			this.mem_block_ptrs.setAtIndex(AddressLayout.ADDRESS, i, this.mem_block.asSlice(i * this.format.byteSize() * this.blockSize));
		}
	}
	
	public long lastFlags() {
		return this.mem_flags.get(ValueLayout.JAVA_INT, 0);
	}
	
	public long lastTimeNs() {
		return this.mem_timeNs.get(ValueLayout.JAVA_LONG, 0);
	}

	public LongBuffer getDirectBufs() {
		// this.mem_directBufs.asSlice(0, ValueLayout.ADDRESS).asReadOnly();
		return this.buf_directBufs;
	}
	
	public int closeStream() {
		return Device_h.SoapySDRDevice_closeStream(this.dev.getAddress(), this.ptr);
	}
	
	@Override
	public void close() {
		this.closeStream();
	}
	
	public long getStreamMTU() {
		return Device_h.SoapySDRDevice_getStreamMTU(this.dev.getAddress(), this.ptr);
	}
	
	public int activateStream(int flags, long timeNs, long numElems) {
		return Device_h.SoapySDRDevice_activateStream(this.dev.getAddress(), this.ptr, flags, timeNs, numElems);
	}
	
	public int activateStream() {
		return this.activateStream(0, 0, 0);
	}
	
	public int deactivateStream(int flags, long timeNs) {
		return Device_h.SoapySDRDevice_deactivateStream(this.dev.getAddress(), this.ptr, flags, timeNs);
	}
	
	public int deactivateStream() {
		return this.deactivateStream(0, 0);
	}
	
	public int readStream(long timeoutUs) {
		return Device_h.SoapySDRDevice_readStream(this.dev.getAddress(), ptr, mem_block_ptrs, this.blockSize, mem_flags, mem_timeNs, timeoutUs);
	}
	
	public int writeStream(int flags, long timeNs, long timeoutUs) {
		this.mem_flags.set(ValueLayout.JAVA_INT, 0, flags);
		return Device_h.SoapySDRDevice_writeStream(this.dev.getAddress(), ptr, mem_block_ptrs, this.blockSize, mem_flags, timeNs, timeoutUs);
	}
	
	// Not tested. Only LimeSuite seems to support it?
	public int readStreamStatus(long chanMask, int flags, long timeoutUs) {
		this.mem_flags.set(ValueLayout.JAVA_INT, 0, flags);
		try (Arena arena = Arena.ofConfined()) {
			return Device_h.SoapySDRDevice_readStreamStatus(this.dev.getAddress(), ptr, arena.allocateFrom(ValueLayout.JAVA_LONG, chanMask), mem_flags, mem_timeNs, timeoutUs);
		}
	}
	
	// Direct buffer access API
	
	public long getNumDirectAccessBuffers() {
		return Device_h.SoapySDRDevice_getNumDirectAccessBuffers(this.dev.getAddress(), ptr);
	}
	
	/**
	 * 
	 * @param handle
	 * @param list Supply an empty ArrayList into which the addresses will be returned.
	 * @return
	 */
	public int getDirectAccessBufferAddrs(long handle) {			
		int ret = Device_h.SoapySDRDevice_getDirectAccessBufferAddrs(this.dev.getAddress(), ptr, handle, mem_directBufs);
		/* buffs.ensureCapacity(this.numChannels);
		for (int i = 0; i < this.numChannels; i++) {
			buffs.set(i, MemorySegment.ofAddress(mem_directBufs.getAtIndex(AddressLayout.JAVA_LONG, i)));
		} */
		return ret;
	}
	
	public StreamReadBuffer acquireReadBuffer(long timeoutUs) {
		int numElems = Device_h.SoapySDRDevice_acquireReadBuffer(dev.getAddress(), ptr, mem_handle, mem_readBufs, mem_flags, mem_timeNs, timeoutUs);
		var buf = new StreamReadBuffer(numElems);
		return buf;
	}
	
	public StreamWriteBuffer acquireWriteBuffer(long timeoutUs) {
		int numElems = Device_h.SoapySDRDevice_acquireWriteBuffer(this.dev.getAddress(), ptr, mem_handle, mem_writeBufs, timeoutUs);
		var buf = new StreamWriteBuffer(numElems);
		return buf;
		
	}
	
	protected abstract class StreamBuffer implements AutoCloseable {
		final protected long handle;
		final protected long numElems;
		
		protected StreamBuffer(long numElems) {
			this.handle = mem_handle.getAtIndex(ValueLayout.JAVA_INT, 0);
			this.numElems = numElems;
		}
		
		public long getNumChannels() {
			return numChannels;
		}
		
		public long getNumElems() {
			return this.numElems;
		}
		
		public SoapySDRStream getStream() {
			return SoapySDRStream.this;
		}
		
		protected abstract MemorySegment getBuffers();
		
		public MemorySegment getBuffer(long channel) {
			return this.getBuffers().getAtIndex(ValueLayout.ADDRESS, channel).reinterpret(this.numElems * getNativeFormat(channel).format().byteSize());
		}
		
		@Override
		public void close() {
			this.release();
		}
		
		public abstract void release();
	}
	
	public class StreamReadBuffer extends StreamBuffer {
		protected StreamReadBuffer(long numElems) {
			super(numElems);
		}

		@Override
		public void release() {
			Device_h.SoapySDRDevice_releaseReadBuffer(dev.getAddress(), ptr, handle);
		}

		@Override
		protected MemorySegment getBuffers() {
			return mem_readBufs;
		}
		
	}
	
	public class StreamWriteBuffer extends StreamBuffer {
		protected StreamWriteBuffer(long numElems) {
			super(numElems);
		}
		
		public void release(long numElems, int flags, long timeNs) {
			// this.stream.mem_flags.set(ValueLayout.JAVA_INT, 0, flags);
			Device_h.SoapySDRDevice_releaseWriteBuffer(dev.getAddress(), ptr, handle, numElems, mem_flags, timeNs);
		}
		
		@Override
		public void release() {
			release(getBlockSize(), 0, 0);
		}

		@Override
		protected MemorySegment getBuffers() {
			return mem_writeBufs;
		}
	}
	
}
