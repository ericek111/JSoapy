package eu.lixko.jsoapy.soapy;

import java.lang.foreign.*;
import java.lang.module.ModuleDescriptor.Version;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;


public class Version_h {
	
    public static final int SOAPY_SDR_API_VERSION = (int)524800L;
    private static String apiVersion = null;
    private static Version apiVersionObj = null;
    private static String abiVersion = null;
    private static String libVersion = null;

    private static ApiMethod SoapySDR_getAPIVersion = new ApiMethod("SoapySDR_getAPIVersion", FunctionDescriptor.of(ValueLayout.ADDRESS));
    public static String SoapySDR_getAPIVersion() {
    	if (apiVersion == null) {
    		apiVersion = NativeUtils.segmentToString((MemorySegment) NativeUtils.call(SoapySDR_getAPIVersion));
    		apiVersionObj = Version.parse(apiVersion);
    	}
    	return apiVersion;
    }
    
    private static ApiMethod SoapySDR_getABIVersion = new ApiMethod("SoapySDR_getABIVersion", FunctionDescriptor.of(ValueLayout.ADDRESS));
    public static String SoapySDR_getABIVersion() {
    	if (apiVersion == null) {
    		abiVersion = NativeUtils.segmentToString((MemorySegment) NativeUtils.call(SoapySDR_getABIVersion));
    	}
    	return abiVersion;
    }
    
    private static ApiMethod SoapySDR_getLibVersion = new ApiMethod("SoapySDR_getLibVersion", FunctionDescriptor.of(ValueLayout.ADDRESS));
    public static String SoapySDR_getLibVersion() {
    	if (libVersion == null) {
    		libVersion = NativeUtils.segmentToString((MemorySegment) NativeUtils.call(SoapySDR_getLibVersion));
    	}
    	return libVersion;
    }

    public static MemorySegment SOAPY_SDR_ABI_VERSION = NativeUtils.allocateFrom("0.8-2");
    
    public static int compareVersions(String a, String b) throws IllegalArgumentException {
    	return Version.parse(a).compareTo(Version.parse(b));
    }
    
    public static boolean minVersionMatches(String ver) throws IllegalArgumentException {
    	SoapySDR_getAPIVersion();
    	return apiVersionObj.compareTo(Version.parse(ver)) <= 0;
    }
    
}

