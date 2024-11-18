package eu.lixko.jsoapy.soapy;

import java.lang.foreign.*;
import java.util.*;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;

import static java.lang.foreign.ValueLayout.*;

public class Converters_h {
    
    private static ApiMethod SoapySDRConverter_listTargetFormats = new ApiMethod("SoapySDRConverter_listTargetFormats", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRConverter_listTargetFormats(MemorySegment sourceFormat, MemorySegment length) {
    	return (MemorySegment) NativeUtils.call(SoapySDRConverter_listTargetFormats, sourceFormat, length);
    }
    
    private static ApiMethod SoapySDRConverter_listSourceFormats = new ApiMethod("SoapySDRConverter_listSourceFormats", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRConverter_listSourceFormats(MemorySegment targetFormat, MemorySegment length) {
    	return (MemorySegment) NativeUtils.call(SoapySDRConverter_listSourceFormats, targetFormat, length);
    }

    private static ApiMethod SoapySDRConverter_listPriorities = new ApiMethod("SoapySDRConverter_listPriorities", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS
    ));
    public static List<SoapySDRConverterFunctionPriority> SoapySDRConverter_listPriorities(MemorySegment sourceFormat, MemorySegment targetFormat) {
    	try (var arena = Arena.ofConfined()) {
    		MemorySegment lengthOut = arena.allocate(ValueLayout.JAVA_LONG);
    		var prioritiesMem = (MemorySegment) NativeUtils.call(SoapySDRConverter_listPriorities, sourceFormat, targetFormat, lengthOut);
    		prioritiesMem.toArray(JAVA_INT);
    		// TODO: We're going through enum members here. What's the size? Let's assume 4. (warning: reinterpret is unsafe)
    		// , MemoryLayout.sequenceLayout(lengthOut.get(JAVA_LONG, 0), Linker.nativeLinker().canonicalLayouts().get("int"))
    		// ValueLayout.ADDRESS.withTargetLayout();
    		int[] prioritiesArr = prioritiesMem.reinterpret(Integer.BYTES * lengthOut.get(JAVA_LONG, 0)).toArray(JAVA_INT);
    		
    		List<SoapySDRConverterFunctionPriority> ret = new ArrayList<>(prioritiesArr.length);
    		for (int priority : prioritiesArr) {
    			ret.add(SoapySDRConverterFunctionPriority.fromCode(priority));
    		}
    		return ret;
    	}
    }
    
    private static ApiMethod SoapySDRConverter_getFunction = new ApiMethod("SoapySDRConverter_getFunction", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRConverter_getFunction(MemorySegment sourceFormat, MemorySegment targetFormat) {
        var mh$ = SoapySDRConverter_getFunction.HANDLE;
        try {
            return (MemorySegment)mh$.invokeExact(sourceFormat, targetFormat);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }
    
    private static ApiMethod SoapySDRConverter_getFunctionWithPriority = new ApiMethod("SoapySDRConverter_getFunctionWithPriority", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.JAVA_INT
    ));
    public static MemorySegment SoapySDRConverter_getFunctionWithPriority(MemorySegment sourceFormat, MemorySegment targetFormat, int priority) {
        var mh$ = SoapySDRConverter_getFunctionWithPriority.HANDLE;
        try {
            return (MemorySegment)mh$.invokeExact(sourceFormat, targetFormat, priority);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }
    
    private static ApiMethod SoapySDRConverter_listAvailableSourceFormats = new ApiMethod("SoapySDRConverter_listAvailableSourceFormats", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRConverter_listAvailableSourceFormats(MemorySegment length) {
    	return (MemorySegment) NativeUtils.call(SoapySDRConverter_listAvailableSourceFormats, length);
    }
    
}

