package eu.lixko.jsoapy.soapy;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.List;

public class SoapySDRRange implements ISimpleLocalStruct {
	
	protected double minimum = 0.0, maximum = 0.0, step = 0.0;
	
	protected SoapySDRRange() {
	}
	
	public double getMinimum() {
		return this.minimum;
	}

	public void setMinimum(double val) {
		this.minimum = val;
	}
	
	public double getMaximum() {
		return this.maximum;
	}
	
	public void setMaximum(double val) {
		this.maximum = val;
	}
	
	public double getStep() {
		return this.step;
	}
	
	public void setStep(double val) {
		this.step = val;
	}
	
	public static MemorySegment allocate(SegmentAllocator arena) {
		return arena.allocate(SoapySDRRange_h.layout());
	}

	@Override
	public MemorySegment makeIntoStruct(SegmentAllocator arena) {
		MemorySegment ptr = allocate(arena);
		writeStruct(ptr);
		return ptr;
	}

	@Override
	public void writeStruct(MemorySegment struct) {
		struct.set(ValueLayout.JAVA_DOUBLE, SoapySDRRange_h.minimum$offset(), this.minimum);
		struct.set(ValueLayout.JAVA_DOUBLE, SoapySDRRange_h.maximum$offset(), this.maximum);
		struct.set(ValueLayout.JAVA_DOUBLE, SoapySDRRange_h.step$offset(), this.step);		
	}

	@Override
	public void readStruct(MemorySegment struct) {
		struct = struct.reinterpret(SoapySDRRange_h.sizeof());
		this.minimum = struct.get(ValueLayout.JAVA_DOUBLE, SoapySDRRange_h.minimum$offset());
		this.maximum = struct.get(ValueLayout.JAVA_DOUBLE, SoapySDRRange_h.maximum$offset());
		this.step = struct.get(ValueLayout.JAVA_DOUBLE, SoapySDRRange_h.step$offset());		
	}
	
	public static SoapySDRRange fromStruct(MemorySegment struct) {
		var range = new SoapySDRRange();
		range.readStruct(struct);
		return range;
	}
	
	@Override
	public String toString() {
		String ret = "[" + this.minimum + ", " + this.maximum;
		if (this.step != 0.0) {
			ret += ", " + this.step;
		}
		ret += "]";
		return ret;
	}
	
	public String toString(double scale) {
		String ret = "[" + this.minimum/scale + ", " + this.maximum/scale;
		if (this.step != 0.0) {
			ret += ", " + this.step/scale;
		}
		ret += "]";
		return ret;
	}
	
	public static String rangeListToString(List<SoapySDRRange> ranges, double scale) {
		final long MAXRLEN = 10; // for abbreviating long lists
		
		String ret = "";
		
		for (int i = 0; i < ranges.size(); i++) {
			if (ranges.size() >= MAXRLEN && i >= MAXRLEN/2 && i < (ranges.size() - MAXRLEN/2)) {
				if (i == MAXRLEN) ret += ", ...";
				continue;
			}
			
			if (!ret.isEmpty()) ret += ", ";
			
			var range = ranges.get(i);
			if (range.getMinimum() == range.getMaximum()) {
				ret += range.getMinimum() / scale;
			} else {
				ret += range.toString(scale);
			}
		}
		
		return ret;
	}
}
