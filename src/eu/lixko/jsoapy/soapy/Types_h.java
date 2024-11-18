package eu.lixko.jsoapy.soapy;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

public class Types_h {
    private static ApiMethod SoapySDRKwargs_fromString = new ApiMethod("SoapySDRKwargs_fromString", FunctionDescriptor.of(
        SoapySDRKwargs_h.layout(),
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRKwargs_fromString(SegmentAllocator allocator, MemorySegment markup) {
        return (MemorySegment) NativeUtils.call(SoapySDRKwargs_fromString, allocator, markup);
    }

    private static ApiMethod SoapySDRKwargs_toString = new ApiMethod("SoapySDRKwargs_toString", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRKwargs_toString(MemorySegment args) {
        return (MemorySegment) NativeUtils.call(SoapySDRKwargs_toString, args);
    }

    private static ApiMethod SoapySDR_free = new ApiMethod("SoapySDR_free", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS
    ));
    public static void SoapySDR_free(MemorySegment ptr) {
        NativeUtils.call(SoapySDR_free, ptr);
    }

    private static ApiMethod SoapySDRStrings_clear = new ApiMethod("SoapySDRStrings_clear", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static void SoapySDRStrings_clear(MemorySegment elems, long length) {
        NativeUtils.call(SoapySDRStrings_clear, elems, length);
    }

    private static ApiMethod SoapySDRKwargs_set = new ApiMethod("SoapySDRKwargs_set", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRKwargs_set(MemorySegment args, MemorySegment key, MemorySegment val) {
        return (int) NativeUtils.call(SoapySDRKwargs_set, args, key, val);
    }

    private static ApiMethod SoapySDRKwargs_get = new ApiMethod("SoapySDRKwargs_get", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRKwargs_get(MemorySegment args, MemorySegment key) {
        return (MemorySegment) NativeUtils.call(SoapySDRKwargs_get, args, key);
    }

    private static ApiMethod SoapySDRKwargs_clear = new ApiMethod("SoapySDRKwargs_clear", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS
    ));
    public static void SoapySDRKwargs_clear(MemorySegment args) {
        NativeUtils.call(SoapySDRKwargs_clear, args);
    }

    private static ApiMethod SoapySDRKwargsList_clear = new ApiMethod("SoapySDRKwargsList_clear", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static void SoapySDRKwargsList_clear(MemorySegment args, long length) {
        NativeUtils.call(SoapySDRKwargsList_clear, args, length);
    }

    private static ApiMethod SoapySDRArgInfo_clear = new ApiMethod("SoapySDRArgInfo_clear", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS
    ));
    public static void SoapySDRArgInfo_clear(MemorySegment info) {
        NativeUtils.call(SoapySDRArgInfo_clear, info);
    }

    private static ApiMethod SoapySDRArgInfoList_clear = new ApiMethod("SoapySDRArgInfoList_clear", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static void SoapySDRArgInfoList_clear(MemorySegment info, long length) {
        NativeUtils.call(SoapySDRArgInfoList_clear, info, length);
    }


}

