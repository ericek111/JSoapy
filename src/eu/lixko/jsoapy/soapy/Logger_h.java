package eu.lixko.jsoapy.soapy;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;

public class Logger_h {

    private static ApiMethod SoapySDR_log = new ApiMethod("SoapySDR_log", FunctionDescriptor.ofVoid(
		ValueLayout.JAVA_INT, 
		ValueLayout.ADDRESS
    ));
    public static void SoapySDR_log(SoapySDRLogLevel logLevel, MemorySegment message) {
    	NativeUtils.call(SoapySDR_log, logLevel.code(), message);
    }
    
	public static void SoapySDR_log(SoapySDRLogLevel logLevel, String message) {
    	try (Arena arena = Arena.ofConfined()) {
    		SoapySDR_log(logLevel, arena.allocateFrom(message));
    	}
    }
    
    private static ApiMethod SoapySDR_vlogf = new ApiMethod("SoapySDR_vlogf", FunctionDescriptor.ofVoid(
		ValueLayout.JAVA_INT,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS
	));
    public static void SoapySDR_vlogf(SoapySDRLogLevel logLevel, MemorySegment format, MemorySegment argList) {
    	NativeUtils.call(SoapySDR_vlogf, logLevel.code(), format, argList);
    }
    
    public static void SoapySDR_vlogf(SoapySDRLogLevel logLevel, String format, String message) {
    	try (Arena arena = Arena.ofConfined()) {
    		SoapySDR_vlogf(logLevel, arena.allocateFrom(format), arena.allocateFrom(message));
    	}
    }
    
    public interface SoapySDRLogHandler {
        void apply(SoapySDRLogLevel logLevel, String message);
    }
    
    private static ApiMethod SoapySDR_registerLogHandler = new ApiMethod("SoapySDR_registerLogHandler", FunctionDescriptor.ofVoid(
    	ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDR_registerLogHandler(SoapySDRLogHandler fi) {
    	var nativeCallback = SoapySDRLogHandlerNative.allocate(((logLevel, message) -> {
    		fi.apply(SoapySDRLogLevel.fromCode(logLevel), NativeUtils.segmentToString(message));
    	}), Arena.ofAuto());
    	return (MemorySegment) NativeUtils.call(SoapySDR_registerLogHandler, nativeCallback);
    }
    
    private static ApiMethod SoapySDR_setLogLevel = new ApiMethod("SoapySDR_setLogLevel", FunctionDescriptor.ofVoid(
    	ValueLayout.JAVA_INT
    ));
    public static void SoapySDR_setLogLevel(SoapySDRLogLevel logLevel) {
    	NativeUtils.call(SoapySDR_setLogLevel, logLevel.code());
    }
    
    private class SoapySDRLogHandlerNative {
        public interface Function {
            void apply(int logLevel, MemorySegment message);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.ofVoid(
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS
        );

        private static final MethodHandle UP$MH = NativeUtils.upcallHandle(SoapySDRLogHandlerNative.Function.class, "apply", $DESC);

        public static MemorySegment allocate(SoapySDRLogHandlerNative.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }
    }
}

