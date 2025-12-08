package eu.lixko.jsoapy.soapy;

import java.lang.foreign.MemorySegment;
import java.util.List;

import eu.lixko.jsoapy.soapy.SoapySDRDevice.NativeStreamFormat;
import eu.lixko.jsoapy.util.NativeUtils;

public class Converters {
	
	public static SoapySDRConverterFunction getFunction(StreamFormat sourceFormat, StreamFormat targetFormat) throws NoSuchConversionException {
		MemorySegment convFunction = Converters_h.SoapySDRConverter_getFunction(sourceFormat.addr(), targetFormat.addr());
		if (convFunction.address() == 0) {
			throw new NoSuchConversionException(sourceFormat, targetFormat);
		}
		
		return new SoapySDRConverterFunction(convFunction);
	}
	
	public static SoapySDRConverterFunction getFunctionWithPriority(StreamFormat sourceFormat, StreamFormat targetFormat, SoapySDRConverterFunctionPriority priority) throws NoSuchConversionException {
		MemorySegment convFunction = Converters_h.SoapySDRConverter_getFunctionWithPriority(sourceFormat.addr(), targetFormat.addr(), priority.ordinal());
		if (convFunction.address() == 0) {
			throw new NoSuchConversionException(sourceFormat, targetFormat);
		}
		
		return new SoapySDRConverterFunction(convFunction);
	}
	
	public static NativeFormatConverterFunction getFunction(NativeStreamFormat nativeFormat, StreamFormat targetFormat) throws NoSuchConversionException {
		var convFunction = getFunction(nativeFormat.format(), targetFormat);
		return new NativeFormatConverterFunction(convFunction.addr, nativeFormat.fullScale());
	}

	public static List<StreamFormat> listTargetFormats(StreamFormat sourceFormat) {
		return NativeUtils.invokeGetPtrArray(
			lengthOut -> (MemorySegment) Converters_h.SoapySDRConverter_listTargetFormats(sourceFormat.addr(), lengthOut),
			item -> StreamFormat.valueOf(NativeUtils.segmentToString(item))
		);
	}
	
	public static List<StreamFormat> listSourceFormats(StreamFormat targetFormat) {
		return NativeUtils.invokeGetPtrArray(
			lengthOut -> (MemorySegment) Converters_h.SoapySDRConverter_listSourceFormats(targetFormat.addr(), lengthOut),
			item -> StreamFormat.valueOf(NativeUtils.segmentToString(item))
		);
	}
	
	public static List<String> listAvailableSourceFormats() {
		return NativeUtils.invokeGetStrArray((lengthOut) -> (MemorySegment) Converters_h.SoapySDRConverter_listAvailableSourceFormats(lengthOut));
	}
	
	public static List<SoapySDRConverterFunctionPriority> listPriorities(StreamFormat sourceFormat, StreamFormat targetFormat) {
		return Converters_h.SoapySDRConverter_listPriorities(sourceFormat.addr(), targetFormat.addr());
	}
	
	
	public static void convert(StreamFormat sourceFormat, StreamFormat targetFormat, MemorySegment inBuf, MemorySegment outBuf, long numElems, double optScaler) throws NoSuchConversionException {
		var convFunction = getFunction(sourceFormat, targetFormat);
		convFunction.invoke(inBuf, outBuf, numElems, optScaler);
	}
	
	public static void convertWithPriority(StreamFormat sourceFormat, StreamFormat targetFormat, SoapySDRConverterFunctionPriority priority, MemorySegment inBuf, MemorySegment outBuf, long numElems, double optScaler) throws NoSuchConversionException {
		var convFunction = getFunctionWithPriority(sourceFormat, targetFormat, priority);
		convFunction.invoke(inBuf, outBuf, numElems, optScaler);
	}
	
	public static class NoSuchConversionException extends UnsupportedOperationException {

		private static final long serialVersionUID = -3755587097171197604L;
		
		public NoSuchConversionException(StreamFormat source, StreamFormat target) {
		    super("Can't convert from " + source.name() + " to " + target.name() + ".");
		}

	}

}
