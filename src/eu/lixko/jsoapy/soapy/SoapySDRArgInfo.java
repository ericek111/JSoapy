package eu.lixko.jsoapy.soapy;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.List;

import eu.lixko.jsoapy.util.NativeUtils;

public class SoapySDRArgInfo implements ISimpleLocalStruct {
	
	public String key, value, name, description, units;
	
	public SoapySDRArgInfoType type;
	
	public SoapySDRRange range;
	
	public long numOptions;
	
	public List<String> options;
	
	public List<String> optionNames;
	
	public static MemorySegment allocate(SegmentAllocator arena) {
		return arena.allocate(SoapySDRRange_h.layout());
	}

	@Override
	public void readStruct(MemorySegment struct) {
		this.key = NativeUtils.segmentToString(struct.get(SoapySDRArgInfo_h.key$layout(), SoapySDRArgInfo_h.key$offset()));
		this.value = NativeUtils.segmentToString(struct.get(SoapySDRArgInfo_h.value$layout(), SoapySDRArgInfo_h.value$offset()));
		this.name = NativeUtils.segmentToString(struct.get(SoapySDRArgInfo_h.name$layout(), SoapySDRArgInfo_h.name$offset()));
		this.description = NativeUtils.segmentToString(struct.get(SoapySDRArgInfo_h.description$layout(), SoapySDRArgInfo_h.description$offset()));
		this.units = NativeUtils.segmentToString(struct.get(SoapySDRArgInfo_h.units$layout(), SoapySDRArgInfo_h.units$offset()));
		this.type = SoapySDRArgInfoType.values()[struct.get(SoapySDRArgInfo_h.type$layout(), SoapySDRArgInfo_h.type$offset())];
		this.range = SoapySDRRange.fromStruct(struct.asSlice(SoapySDRArgInfo_h.range$offset(), SoapySDRArgInfo_h.range$layout()));
		long numOptions = struct.get(SoapySDRArgInfo_h.numOptions$layout(), SoapySDRArgInfo_h.numOptions$offset());
		MemorySegment optionsPtr = struct.get(SoapySDRArgInfo_h.options$layout(), SoapySDRArgInfo_h.options$offset());
		MemorySegment optionNamesPtr = struct.get(SoapySDRArgInfo_h.optionNames$layout(), SoapySDRArgInfo_h.optionNames$offset());
		this.options = NativeUtils.getPtrArray(optionsPtr, numOptions, (MemorySegment item) -> NativeUtils.segmentToString(item));
		this.optionNames = NativeUtils.getPtrArray(optionNamesPtr, numOptions, (MemorySegment item) -> NativeUtils.segmentToString(item));
	}

	@Override
	public MemorySegment makeIntoStruct(SegmentAllocator arena) {
		MemorySegment ptr = allocate(arena);
		writeStruct(ptr);
		return ptr;
	}

	@Override
	public void writeStruct(MemorySegment struct) {
		// TODO Auto-generated method stub
		throw new Error("Not implemented yet!");
	}
	
	public static SoapySDRArgInfo fromStruct(MemorySegment struct) {
		var range = new SoapySDRArgInfo();
		range.readStruct(struct);
		return range;
	}
	
	public String toString() {
		String ret = "[key=" + this.key;
		
		if (!this.units.isEmpty()) {
			ret += ", units=" + this.units;
		}
		
		if (!this.value.isEmpty()) {
			ret += ", default=" + this.value;
		}
		
		ret += ", type=" + this.type.toString().toLowerCase();
		if (this.range.minimum < this.range.maximum) {
			ret += ", range=" + this.range.toString();
		}
		
		if (!this.options.isEmpty()) {
			ret += ", options=(" + String.join(", ", this.options) + ")";
		}
		
		ret += "]";
		
		return ret;
	}
	
	
}
