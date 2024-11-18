package eu.lixko.jsoapy.fft;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;


public class Volk {
	
	static {
		System.loadLibrary("volk");
	}
	
	public static ApiMethod volk_32fc_32f_multiply_32fc = new ApiMethod(1, "volk_32fc_32f_multiply_32fc", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    ));
    public static void volk_32fc_32f_multiply_32fc(MemorySegment cVector, MemorySegment aVector, MemorySegment bVector, int num_points) {
    	NativeUtils.call(volk_32fc_32f_multiply_32fc, cVector, aVector, bVector, num_points);	
    }
    
	public static ApiMethod volk_32fc_s32f_power_spectrum_32f = new ApiMethod(1, "volk_32fc_s32f_power_spectrum_32f", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_FLOAT,
        ValueLayout.JAVA_INT
    ));
    public static void volk_32fc_s32f_power_spectrum_32f(MemorySegment logPowerOutput, MemorySegment complexFFTInput, float normalizationFactor, int num_points) {
    	NativeUtils.call(volk_32fc_s32f_power_spectrum_32f, logPowerOutput, complexFFTInput, normalizationFactor, num_points);
    }
    
	public static ApiMethod volk_16u_byteswap_get_func_desc = new ApiMethod(1, "volk_16u_byteswap_get_func_desc", FunctionDescriptor.of(
        ValueLayout.ADDRESS
    ));
    public static void volk_16u_byteswap_get_func_desc(MemorySegment logPowerOutput, MemorySegment complexFFTInput, float normalizationFactor, int num_points) {
    	NativeUtils.call(volk_16u_byteswap_get_func_desc, logPowerOutput, complexFFTInput, normalizationFactor, num_points);
    }
    
	public static ApiMethod volk_16u_byteswap = new ApiMethod(1, "volk_16u_byteswap", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static void volk_16u_byteswap(MemorySegment intsToSwap, int num_points) {
    	NativeUtils.call(volk_16u_byteswap, intsToSwap, num_points);
    }
	    
}
