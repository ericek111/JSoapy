package eu.lixko.jsoapy.fft;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;

public class FFTW {
	
	static {
		System.loadLibrary("fftw3");
		System.loadLibrary("fftw3f");
	}
	
	public static final int FFTW_FORWARD = -1;
	public static final int FFTW_BACKWARD = 1;
	public static final int FFTW_ESTIMATE = 1 << 6;
	
    private static ApiMethod fftwf_malloc = new ApiMethod("fftwf_malloc", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static MemorySegment fftwf_malloc(long size) {
    	var seg = (MemorySegment) NativeUtils.call(fftwf_malloc, size);
    	return seg.reinterpret(size);
    }
    
    private static ApiMethod fftwf_alloc_complex = new ApiMethod("fftwf_alloc_complex", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    /** TODO: Probably broken */
    public static MemorySegment fftwf_alloc_complex(long size) {
    	var seg = (MemorySegment) NativeUtils.call(fftwf_alloc_complex, size);
    	return seg.reinterpret(size);
    }
    
    private static ApiMethod fftwf_free = new ApiMethod("fftwf_free", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS
    ));
    public static void fftwf_free(MemorySegment ptr) {
    	NativeUtils.call(fftwf_free, ptr);
    }
    
    private static ApiMethod fftwf_destroy_plan = new ApiMethod("fftwf_destroy_plan", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS
    ));
    public static void fftwf_destroy_plan(MemorySegment ptr) {
    	NativeUtils.call(fftwf_destroy_plan, ptr);
    }
    
    private static ApiMethod fftwf_execute = new ApiMethod("fftwf_execute", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS
    ));
    public static void fftwf_execute(MemorySegment ptr) {
    	NativeUtils.call(fftwf_execute, ptr);
    }
    
    private static ApiMethod fftwf_plan_dft_1d = new ApiMethod("fftwf_plan_dft_1d", FunctionDescriptor.of(
		ValueLayout.ADDRESS,
		ValueLayout.JAVA_LONG,
		ValueLayout.ADDRESS,
		ValueLayout.ADDRESS,
		ValueLayout.JAVA_INT,
		ValueLayout.JAVA_INT
    ));
    public static MemorySegment fftwf_plan_dft_1d(long n, MemorySegment in, MemorySegment out, int sign, int flags) {
    	return (MemorySegment) NativeUtils.call(fftwf_plan_dft_1d, n, in, out, sign, flags);
    }
}
