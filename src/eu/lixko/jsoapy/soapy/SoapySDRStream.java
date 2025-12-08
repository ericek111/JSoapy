package eu.lixko.jsoapy.soapy;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
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
	protected MemorySegment mem_direct_ptrs = null;
	
	protected final Arena paramArena = Arena.ofAuto();
	protected MemorySegment mem_flags = paramArena.allocate(ValueLayout.JAVA_INT);
	protected MemorySegment mem_timeNs = paramArena.allocate(ValueLayout.JAVA_LONG);
	protected MemorySegment mem_handle = paramArena.allocate(ValueLayout.ADDRESS);
	protected int lastBlockNumElems = 0;
	protected int lastDirectNumElems = 0;
	protected boolean isHoldingAnAcquiredBuffer = false;
	
	protected MemorySegment mem_directAccessBufs;
	protected long currentDirectAccessHandle = -1;
	
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
			this.channelFormats = List.of(this.dev.getNativeStreamFormat(direction, 0));
		} else {
			this.channels = Arrays.stream(channels).boxed().collect(Collectors.toUnmodifiableList());
			this.channelFormats = Arrays.stream(channels).mapToObj(channel -> this.dev.getNativeStreamFormat(direction, channel)).collect(Collectors.toUnmodifiableList());
		}
		
		this.numChannels = channels != null ? channels.length : this.dev.getNumChannels(direction);
		this.args = args;
		this.mem_directAccessBufs = paramArena.allocate(ValueLayout.ADDRESS, numChannels);
		
		this.mem_block_ptrs = paramArena.allocate(this.numChannels * ValueLayout.ADDRESS.byteSize());
		this.mem_direct_ptrs = paramArena.allocate(this.numChannels * ValueLayout.ADDRESS.byteSize());
		
		if (ptr == null) {
			MemorySegment channelsPtr = null;
			if (channels != null) {
				// Using ADDRESS here, because the channels argument is size_t. We're not actually filling it with real pointers, only numbers (0 .. numChannels).
				channelsPtr = paramArena.allocate(ValueLayout.ADDRESS, channels.length);
				for (int i = 0; i < channels.length; i++) {
					channelsPtr.setAtIndex(ValueLayout.ADDRESS, i, MemorySegment.ofAddress(channels[0])); 
				}
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
		
		long mtu = this.getStreamMTU();
		this.setBlockSize(mtu > 0 ? mtu : 40000); // 1 MiB by default
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
	
	/**
	 * @return The input buffer size in samples.
	 */
	public long getBlockSize() {
		return this.blockSize;
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
	
	public int lastHandle() {
		return this.mem_handle.getAtIndex(ValueLayout.JAVA_INT, 0);
	}
	
	public int closeStream() {
		return Device_h.SoapySDRDevice_closeStream(this.dev.getAddress(), this.ptr);
	}
	
	@Override
	public void close() {
		this.closeStream();
	}
	
	/**
	 * Get the stream's maximum transmission unit (MTU) in number of elements.
	 * 
	 * @return the MTU in number of stream elements (never zero) 
	 */
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
		assert this.direction == SoapySDRDeviceDirection.RX;
		
		int numElems = Device_h.SoapySDRDevice_readStream(this.dev.getAddress(), ptr, mem_block_ptrs, this.blockSize, mem_flags, mem_timeNs, timeoutUs);
		this.lastBlockNumElems = numElems;
		return numElems;
	}
	
	public int writeStream(int flags, long timeNs, long timeoutUs) {
		assert this.direction == SoapySDRDeviceDirection.TX;
		
		this.mem_flags.set(ValueLayout.JAVA_INT, 0, flags);
		int numElems =  Device_h.SoapySDRDevice_writeStream(this.dev.getAddress(), ptr, mem_block_ptrs, this.blockSize, mem_flags, timeNs, timeoutUs);
		this.lastBlockNumElems = numElems;
		return numElems;
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
	
	public MemorySegment getDirectAccessBuffer(long handle, long channel) {
		if (handle != currentDirectAccessHandle) {
			int err = Device_h.SoapySDRDevice_getDirectAccessBufferAddrs(this.dev.getAddress(), ptr, handle, mem_directAccessBufs);
			if (err != 0) {
				return null;
			}
			currentDirectAccessHandle = handle;
		}
		
		return this.mem_directAccessBufs.getAtIndex(ValueLayout.ADDRESS, channel);
	}
	
	public MemorySegment getDirectBuffer(long channel) {
		assert isHoldingAnAcquiredBuffer == true;
		return this.mem_direct_ptrs.getAtIndex(ValueLayout.ADDRESS, channel).reinterpret(this.lastDirectNumElems * getNativeFormat(channel).format().byteSize());
	}
	
	/**
	 * Used for {@link #readStream} and {@link #writeStream}.
	 * @return The array of void* buffers numChannels in size, containing data either received, or ready to be transmitted.
	 */
	public MemorySegment getNormalBuffer(long channel) {
		return this.mem_block.asSlice(this.format.byteSize() * this.blockSize * channel, this.format.byteSize() * this.lastBlockNumElems);
	}
	
	public int acquireReadBuffer(long timeoutUs) {
		assert this.direction == SoapySDRDeviceDirection.RX;
		assert isHoldingAnAcquiredBuffer == false;
		
		int numElems = Device_h.SoapySDRDevice_acquireReadBuffer(dev.getAddress(), ptr, mem_handle, mem_direct_ptrs, mem_flags, mem_timeNs, timeoutUs);
		this.lastDirectNumElems = numElems;
		isHoldingAnAcquiredBuffer = true;
		return numElems;
	}
	
	/**
	 * Does not presently support out-of-order releases or acquiring a buffer without releasing the previous one. If you need it, open an issue.
	 * It could be done by expanding the ptr array buffer to getNumDirectAccessBuffers * numChannels and slicing it by handles.
	 * It'd probably be a good idea to have an ArrayList of objects containing the addresses and handles (see this class in the initial commit).
	 */
	public void releaseReadBuffer() {
		assert this.direction == SoapySDRDeviceDirection.RX;
		assert isHoldingAnAcquiredBuffer == true;
		
		Device_h.SoapySDRDevice_releaseReadBuffer(dev.getAddress(), ptr, this.lastHandle());
		isHoldingAnAcquiredBuffer = false;
	}
	
	public int acquireWriteBuffer(long timeoutUs) {
		assert this.direction == SoapySDRDeviceDirection.TX;
		assert isHoldingAnAcquiredBuffer == false;
		
		int numElems = Device_h.SoapySDRDevice_acquireWriteBuffer(this.dev.getAddress(), ptr, mem_handle, mem_direct_ptrs, timeoutUs);
		this.lastDirectNumElems = numElems;
		isHoldingAnAcquiredBuffer = true;
		return numElems;
	}
	
	/**
	 * Does not presently support out-of-order releases. If you need it, open an issue.
	 */
	public void releaseWriteBuffer(long numElems, int flags, long timeNs) {
		assert this.direction == SoapySDRDeviceDirection.TX;
		assert isHoldingAnAcquiredBuffer == true;
		
		Device_h.SoapySDRDevice_releaseWriteBuffer(dev.getAddress(), ptr, this.lastHandle(), numElems, mem_flags, timeNs);
		isHoldingAnAcquiredBuffer = false;
	}
	
}
