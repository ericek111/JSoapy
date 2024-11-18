package eu.lixko.jsoapy.soapy;

import java.lang.invoke.*;

import eu.lixko.jsoapy.util.NativeUtils;

import java.lang.foreign.*;

/**
 * {@snippet lang=c :
 * typedef void (*SoapySDRConverterFunction)(const void *, void *, const size_t, const double)
 * }
 */
public class SoapySDRConverterFunction {

    SoapySDRConverterFunction() {
        // Should not be called directly
    }

    public interface Function {
        void apply(long inputPointer, long outputPointer, long numElements, double optScalar);
    }

    private static final FunctionDescriptor $DESC = FunctionDescriptor.ofVoid(
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE
    );


    public static FunctionDescriptor descriptor() {
        return $DESC;
    }

    private static final MethodHandle UP$MH = NativeUtils.upcallHandle(SoapySDRConverterFunction.Function.class, "apply", $DESC);

    public static MemorySegment allocate(SoapySDRConverterFunction.Function fi, Arena arena) {
        return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
    }

    private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

    public static void invoke(MemorySegment funcPtr, MemorySegment inputPointer, MemorySegment outputPointer, long numElements, double optScalar) {
        try {
             DOWN$MH.invokeExact(funcPtr, inputPointer, outputPointer, numElements, optScalar);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    
    public static void invokeLongs(MemorySegment funcPtr, long inputPointer, long outputPointer, long numElements, double optScalar) {
        try {
             DOWN$MH.invokeExact(funcPtr, inputPointer, outputPointer, numElements, optScalar);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}

