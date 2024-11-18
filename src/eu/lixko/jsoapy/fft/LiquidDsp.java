package eu.lixko.jsoapy.fft;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.FloatBuffer;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;

public class LiquidDsp {
	static {
		System.loadLibrary("liquid");
	}
	
    private static ApiMethod liquid_firdes_kaiser = new ApiMethod("liquid_firdes_kaiser", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_FLOAT,
        ValueLayout.JAVA_FLOAT,
        ValueLayout.JAVA_FLOAT,
        ValueLayout.ADDRESS
    ));
    public static int liquid_firdes_kaiser(MemorySegment coeffs, float _fc, float _as, float _mu) {
        return (int) NativeUtils.call(liquid_firdes_kaiser, (int) (coeffs.byteSize() / Float.BYTES), _fc, _as, _mu, coeffs);
    }
    
    private static ApiMethod firpfbch_crcf_create = new ApiMethod("firpfbch_crcf_create", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment firpfbch_crcf_create(int _type, int _M, int _p, MemorySegment _h) {
        return (MemorySegment) NativeUtils.call(firpfbch_crcf_create, _type, _M, _p, _h);
    }
    
    private static ApiMethod firpfbch_crcf_create_kaiser = new ApiMethod("firpfbch_crcf_create_kaiser", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_FLOAT
    ));
    public static MemorySegment firpfbch_crcf_create_kaiser(int _type, int _M, int _m, float _As) {
        return (MemorySegment) NativeUtils.call(firpfbch_crcf_create_kaiser, _type, _M, _m, _As);
    }
    
    private static ApiMethod firpfbch_crcf_synthesizer_execute = new ApiMethod("firpfbch_crcf_synthesizer_execute", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch_crcf_synthesizer_execute(MemorySegment _q, MemorySegment _x, MemorySegment _y) {
        return (int) NativeUtils.call(firpfbch_crcf_synthesizer_execute, _q, _x, _y);
    }

    private static ApiMethod firpfbch_crcf_analyzer_execute = new ApiMethod("firpfbch_crcf_analyzer_execute", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch_crcf_analyzer_execute(MemorySegment _q, MemorySegment _x, MemorySegment _y) {
        return (int) NativeUtils.call(firpfbch_crcf_analyzer_execute, _q, _x, _y);
    }
    
    private static ApiMethod firpfbch2_crcf_create = new ApiMethod("firpfbch2_crcf_create", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment firpfbch2_crcf_create(int _type, int _M, int _m, MemorySegment _h) {
        return (MemorySegment) NativeUtils.call(firpfbch2_crcf_create, _type, _M, _m, _h);
    }

    private static ApiMethod firpfbch2_crcf_create_kaiser = new ApiMethod("firpfbch2_crcf_create_kaiser", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_FLOAT
    ));
    public static MemorySegment firpfbch2_crcf_create_kaiser(int _type, int _M, int _m, float _As) {
        return (MemorySegment) NativeUtils.call(firpfbch2_crcf_create_kaiser, _type, _M, _m, _As);
    }

    private static ApiMethod firpfbch2_crcf_copy = new ApiMethod("firpfbch2_crcf_copy", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment firpfbch2_crcf_copy(MemorySegment _q) {
        return (MemorySegment) NativeUtils.call(firpfbch2_crcf_copy, _q);
    }

    private static ApiMethod firpfbch2_crcf_destroy = new ApiMethod("firpfbch2_crcf_destroy", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch2_crcf_destroy(MemorySegment _q) {
        return (int) NativeUtils.call(firpfbch2_crcf_destroy, _q);
    }

    private static ApiMethod firpfbch2_crcf_reset = new ApiMethod("firpfbch2_crcf_reset", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch2_crcf_reset(MemorySegment _q) {
        return (int) NativeUtils.call(firpfbch2_crcf_reset, _q);
    }

    private static ApiMethod firpfbch2_crcf_print = new ApiMethod("firpfbch2_crcf_print", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch2_crcf_print(MemorySegment _q) {
        return (int) NativeUtils.call(firpfbch2_crcf_print, _q);
    }

    private static ApiMethod firpfbch2_crcf_get_type = new ApiMethod("firpfbch2_crcf_get_type", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch2_crcf_get_type(MemorySegment _q) {
        return (int) NativeUtils.call(firpfbch2_crcf_get_type, _q);
    }

    private static ApiMethod firpfbch2_crcf_get_M = new ApiMethod("firpfbch2_crcf_get_M", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch2_crcf_get_M(MemorySegment _q) {
        return (int) NativeUtils.call(firpfbch2_crcf_get_M, _q);
    }

    private static ApiMethod firpfbch2_crcf_get_m = new ApiMethod("firpfbch2_crcf_get_m", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch2_crcf_get_m(MemorySegment _q) {
        return (int) NativeUtils.call(firpfbch2_crcf_get_m, _q);
    }

    private static ApiMethod firpfbch2_crcf_execute = new ApiMethod("firpfbch2_crcf_execute", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int firpfbch2_crcf_execute(MemorySegment _q, MemorySegment _x, MemorySegment _y) {
        return (int) NativeUtils.call(firpfbch2_crcf_execute, _q, _x, _y);
    }
}
