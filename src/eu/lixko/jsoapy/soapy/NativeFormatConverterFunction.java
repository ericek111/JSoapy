package eu.lixko.jsoapy.soapy;

import java.lang.foreign.MemorySegment;

/**
 * Extra.
 */
public class NativeFormatConverterFunction extends SoapySDRConverterFunction {
	
	protected final double fullScale;

	protected NativeFormatConverterFunction(MemorySegment addr, double fullScale) {
		super(addr);
		this.fullScale = fullScale;
	}

    public void invoke(MemorySegment inputPointer, MemorySegment outputPointer, long numElements) {
    	invoke(this.addr, inputPointer, outputPointer, numElements, this.fullScale);
    }

    public void invokeLongs(long inputPointer, long outputPointer, long numElements) {
    	invokeLongs(this.addr, inputPointer, outputPointer, numElements, this.fullScale);
    }

}
