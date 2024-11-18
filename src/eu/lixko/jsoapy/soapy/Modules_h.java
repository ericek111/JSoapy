package eu.lixko.jsoapy.soapy;

import java.lang.foreign.*;
import java.util.*;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;

public class Modules_h {

    private static ApiMethod SoapySDR_getRootPath = new ApiMethod("SoapySDR_getRootPath", FunctionDescriptor.of(
        ValueLayout.ADDRESS
    ));
    public static String SoapySDR_getRootPath() {
        return NativeUtils.segmentToString((MemorySegment) NativeUtils.call(SoapySDR_getRootPath));
    }

    private static ApiMethod SoapySDR_listSearchPaths = new ApiMethod("SoapySDR_listSearchPaths", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static List<String> SoapySDR_listSearchPaths() {
    	return NativeUtils.invokeGetStrArray((lengthOut) -> (MemorySegment) NativeUtils.call(SoapySDR_listSearchPaths, lengthOut));
    }

    private static ApiMethod SoapySDR_listModules = new ApiMethod("SoapySDR_listModules", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static List<String> SoapySDR_listModules() {
    	return NativeUtils.invokeGetStrArray((lengthOut) -> (MemorySegment) NativeUtils.call(SoapySDR_listModules, lengthOut));
    }

    private static ApiMethod SoapySDR_listModulesPath = new ApiMethod("SoapySDR_listModulesPath", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static List<String> SoapySDR_listModulesPath(String path) {
    	try (Arena arena = Arena.ofConfined()) {
    		return NativeUtils.invokeGetStrArray((lengthOut) -> (MemorySegment) NativeUtils.call(SoapySDR_listModulesPath, arena.allocateFrom(path), lengthOut));
    	}
    }

    private static ApiMethod SoapySDR_loadModule = new ApiMethod("SoapySDR_loadModule", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static String SoapySDR_loadModule(String path) {
    	try (Arena arena = Arena.ofConfined()) {
    		return NativeUtils.segmentToString((MemorySegment) NativeUtils.call(SoapySDR_loadModule, arena.allocateFrom(path)));	
    	}
    }

    private static ApiMethod SoapySDR_getLoaderResult = new ApiMethod("SoapySDR_getLoaderResult", FunctionDescriptor.of(
        SoapySDRKwargs_h.layout(),
        ValueLayout.ADDRESS
    ));
    public static SoapySDRKwargs SoapySDR_getLoaderResult(SegmentAllocator allocator, String path) {
    	var structPtr = (MemorySegment) NativeUtils.call(SoapySDR_getLoaderResult, allocator, allocator.allocateFrom(path));
    	var ret = new SoapySDRKwargs(structPtr, allocator);
        return ret;
    }

    private static ApiMethod SoapySDR_getModuleVersion = new ApiMethod("SoapySDR_getModuleVersion", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static String SoapySDR_getModuleVersion(String path) {
    	try (Arena arena = Arena.ofConfined()) {
    		return NativeUtils.segmentToString((MemorySegment) NativeUtils.call(SoapySDR_getModuleVersion, arena.allocateFrom(path)));	
    	}
    }

    private static ApiMethod SoapySDR_unloadModule = new ApiMethod("SoapySDR_unloadModule", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static String SoapySDR_unloadModule(String path) {
    	try (Arena arena = Arena.ofConfined()) {
    		return NativeUtils.segmentToString((MemorySegment) NativeUtils.call(SoapySDR_unloadModule, arena.allocateFrom(path)));	
    	}
    }

    private static ApiMethod SoapySDR_loadModules = new ApiMethod("SoapySDR_loadModules", FunctionDescriptor.ofVoid());
    public static void SoapySDR_loadModules() {
        NativeUtils.call(SoapySDR_loadModules);
    }

    private static ApiMethod SoapySDR_unloadModules = new ApiMethod("SoapySDR_unloadModules", FunctionDescriptor.ofVoid());
    public static void SoapySDR_unloadModules() {
        NativeUtils.call(SoapySDR_unloadModules);
    }

}

