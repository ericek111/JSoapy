package eu.lixko.jsoapy.soapy;

import static java.lang.foreign.ValueLayout.ADDRESS;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.HashMap;
import java.util.Map;

import eu.lixko.jsoapy.util.NativeUtils;

public class SoapySDRKwargs implements AutoCloseable {
	protected MemorySegment mem;
	// public SoapySDRKwargs
	protected SegmentAllocator arena = null;
	
	public SoapySDRKwargs() {
		this.arena = Arena.ofConfined();
		this.mem = arena.allocate(SoapySDRKwargs_h.layout());
	}
	
	/**
	 * 
	 * @param struct
	 * @param arena Can be null if it's a Kwargs struct allocated by the API. In that case, it'll be free-d after {@link #close}
	 */
	public SoapySDRKwargs(MemorySegment struct, SegmentAllocator arena) {
		// hold onto the Arena to make sure the struct is not freed
		this.arena = arena;
		this.mem = struct;
	}
	
	public void set(String key, String value) {
		try (var methArena = Arena.ofConfined()) {
			MemorySegment keyMem = methArena.allocateFrom(key);
			MemorySegment valueMem = methArena.allocateFrom(value);
			if (Types_h.SoapySDRKwargs_set(mem, keyMem, valueMem) != 0) {
				throw new Error("Cannot SoapySDRKwargs_set");
			}
		}
	}
	
	public String get(String key) {
		try (var methArena = Arena.ofConfined()) {
			MemorySegment keyMem = methArena.allocateFrom(key);
			MemorySegment valueMem = Types_h.SoapySDRKwargs_get(mem, keyMem);
			String value = NativeUtils.segmentToString(valueMem);
			return value;
		}
	}
	
	public String toString() {
		MemorySegment valueMem = Types_h.SoapySDRKwargs_toString(mem);
		return NativeUtils.segmentToString(valueMem);
	}
	
	public Map<String, String> toMap() {
		return toMap(this.mem);
	}
	
    public MemorySegment getStruct() {
    	return this.mem;
    }
	
	@Override
	public void close() {
		Types_h.SoapySDRKwargs_clear(mem);
		mem = null;
	}
	
	public boolean isClosed() {
		return mem == null;
	}
	
	public static SoapySDRKwargs fromString(SegmentAllocator arena, String str) {
		MemorySegment addr = Types_h.SoapySDRKwargs_fromString(arena, arena.allocateFrom(str));
		return new SoapySDRKwargs(addr, arena);
	}
	
    public static Map<String, String> toMap(MemorySegment addr) {
    	HashMap<String, String> ret = new HashMap<>();
    	long size = SoapySDRKwargs_h.size(addr);
    	MemoryLayout memLayout = MemoryLayout.sequenceLayout(size, ADDRESS);
    	AddressLayout ptr = ValueLayout.ADDRESS.withTargetLayout(memLayout);
    	MemorySegment keyBase = SoapySDRKwargs_h.keys(addr).reinterpret(memLayout.byteSize());
    	MemorySegment valBase = SoapySDRKwargs_h.vals(addr).reinterpret(memLayout.byteSize());
    	
    	for (int i = 0; i < size; i++) {
    		String key = NativeUtils.segmentToString(keyBase.getAtIndex(ptr, i));
    		String value = NativeUtils.segmentToString(valBase.getAtIndex(ptr, i));
    		ret.put(key, value);
    	}

     	return ret;
    }
    
    public static SoapySDRKwargs makeStruct(SegmentAllocator allocator, Map<String, String> map) {
    	MemorySegment struct = SoapySDRKwargs_h.allocate(allocator);
    	for (Map.Entry<String, String> entry : map.entrySet()) {
    		try (Arena arena = Arena.ofConfined()) {
    			Types_h.SoapySDRKwargs_set(struct, arena.allocateFrom(entry.getKey()), arena.allocateFrom(entry.getValue()));    			
    		}
    	}
    	return new SoapySDRKwargs(struct, allocator);
    }
	
 }
