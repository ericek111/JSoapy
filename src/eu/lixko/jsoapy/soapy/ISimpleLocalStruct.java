package eu.lixko.jsoapy.soapy;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

public interface ISimpleLocalStruct {
	
	public MemorySegment makeIntoStruct(SegmentAllocator arena);
	
	public void writeStruct(MemorySegment struct);
	
	public void readStruct(MemorySegment struct);
	
}
