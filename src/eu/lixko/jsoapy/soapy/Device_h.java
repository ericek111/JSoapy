package eu.lixko.jsoapy.soapy;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.util.NativeUtils.ApiMethod;

public class Device_h {

    private static ApiMethod SoapySDRDevice_lastStatus = new ApiMethod("SoapySDRDevice_lastStatus", FunctionDescriptor.of(
        ValueLayout.JAVA_INT
    ));
    public static int SoapySDRDevice_lastStatus() {
        return (int) NativeUtils.call(SoapySDRDevice_lastStatus);
    }

    private static ApiMethod SoapySDRDevice_lastError = new ApiMethod("SoapySDRDevice_lastError", FunctionDescriptor.of(
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_lastError() {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_lastError);
    }

    private static ApiMethod SoapySDRDevice_enumerate = new ApiMethod("SoapySDRDevice_enumerate", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_enumerate(MemorySegment args, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_enumerate, args, length);
    }

    private static ApiMethod SoapySDRDevice_enumerateStrArgs = new ApiMethod("SoapySDRDevice_enumerateStrArgs", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_enumerateStrArgs(MemorySegment args, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_enumerateStrArgs, args, length);
    }

    private static ApiMethod SoapySDRDevice_make = new ApiMethod("SoapySDRDevice_make", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_make(MemorySegment args) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_make, args);
    }

    private static ApiMethod SoapySDRDevice_makeStrArgs = new ApiMethod("SoapySDRDevice_makeStrArgs", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_makeStrArgs(MemorySegment args) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_makeStrArgs, args);
    }

    private static ApiMethod SoapySDRDevice_unmake = new ApiMethod("SoapySDRDevice_unmake", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_unmake(MemorySegment device) {
        return (int) NativeUtils.call(SoapySDRDevice_unmake, device);
    }

    private static ApiMethod SoapySDRDevice_make_list = new ApiMethod("SoapySDRDevice_make_list", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static MemorySegment SoapySDRDevice_make_list(MemorySegment argsList, long length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_make_list, argsList, length);
    }

    private static ApiMethod SoapySDRDevice_make_listStrArgs = new ApiMethod("SoapySDRDevice_make_listStrArgs", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static MemorySegment SoapySDRDevice_make_listStrArgs(MemorySegment argsList, long length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_make_listStrArgs, argsList, length);
    }

    private static ApiMethod SoapySDRDevice_unmake_list = new ApiMethod("SoapySDRDevice_unmake_list", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_unmake_list(MemorySegment devices, long length) {
        return (int) NativeUtils.call(SoapySDRDevice_unmake_list, devices, length);
    }

    private static ApiMethod SoapySDRDevice_getDriverKey = new ApiMethod("SoapySDRDevice_getDriverKey", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getDriverKey(MemorySegment device) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getDriverKey, device);
    }

    private static ApiMethod SoapySDRDevice_getHardwareKey = new ApiMethod("SoapySDRDevice_getHardwareKey", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getHardwareKey(MemorySegment device) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getHardwareKey, device);
    }

    private static ApiMethod SoapySDRDevice_getHardwareInfo = new ApiMethod("SoapySDRDevice_getHardwareInfo", FunctionDescriptor.of(
        SoapySDRKwargs_h.layout(),
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getHardwareInfo(SegmentAllocator allocator, MemorySegment device) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getHardwareInfo, allocator, device);
    }

    private static ApiMethod SoapySDRDevice_setFrontendMapping = new ApiMethod("SoapySDRDevice_setFrontendMapping", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setFrontendMapping(MemorySegment device, int direction, MemorySegment mapping) {
        return (int) NativeUtils.call(SoapySDRDevice_setFrontendMapping, device, direction, mapping);
    }

    private static ApiMethod SoapySDRDevice_getFrontendMapping = new ApiMethod("SoapySDRDevice_getFrontendMapping", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    ));
    public static MemorySegment SoapySDRDevice_getFrontendMapping(MemorySegment device, int direction) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getFrontendMapping, device, direction);
    }

    private static ApiMethod SoapySDRDevice_getNumChannels = new ApiMethod("SoapySDRDevice_getNumChannels", FunctionDescriptor.of(
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    ));
    public static long SoapySDRDevice_getNumChannels(MemorySegment device, int direction) {
        return (long) NativeUtils.call(SoapySDRDevice_getNumChannels, device, direction);
    }

    private static ApiMethod SoapySDRDevice_getChannelInfo = new ApiMethod("SoapySDRDevice_getChannelInfo", FunctionDescriptor.of(
        SoapySDRKwargs_h.layout(),
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static MemorySegment SoapySDRDevice_getChannelInfo(SegmentAllocator allocator, MemorySegment device, int direction, long channel) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getChannelInfo, allocator, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_getFullDuplex = new ApiMethod("SoapySDRDevice_getFullDuplex", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_getFullDuplex(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_getFullDuplex, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_getStreamFormats = new ApiMethod("SoapySDRDevice_getStreamFormats", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getStreamFormats(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getStreamFormats, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_getNativeStreamFormat = new ApiMethod("SoapySDRDevice_getNativeStreamFormat", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getNativeStreamFormat(MemorySegment device, int direction, long channel, MemorySegment fullScale) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getNativeStreamFormat, device, direction, channel, fullScale);
    }

    private static ApiMethod SoapySDRDevice_getStreamArgsInfo = new ApiMethod("SoapySDRDevice_getStreamArgsInfo", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getStreamArgsInfo(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getStreamArgsInfo, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_setupStream = new ApiMethod("SoapySDRDevice_setupStream", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_setupStream(MemorySegment device, int direction, MemorySegment format, MemorySegment channels, long numChans, MemorySegment args) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_setupStream, device, direction, format, channels, numChans, args);
    }

    private static ApiMethod SoapySDRDevice_closeStream = new ApiMethod("SoapySDRDevice_closeStream", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_closeStream(MemorySegment device, MemorySegment stream) {
        return (int) NativeUtils.call(SoapySDRDevice_closeStream, device, stream);
    }

    private static ApiMethod SoapySDRDevice_getStreamMTU = new ApiMethod("SoapySDRDevice_getStreamMTU", FunctionDescriptor.of(
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static long SoapySDRDevice_getStreamMTU(MemorySegment device, MemorySegment stream) {
        return (long) NativeUtils.call(SoapySDRDevice_getStreamMTU, device, stream);
    }

    private static ApiMethod SoapySDRDevice_activateStream = new ApiMethod("SoapySDRDevice_activateStream", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_activateStream(MemorySegment device, MemorySegment stream, int flags, long timeNs, long numElems) {
        return (int) NativeUtils.call(SoapySDRDevice_activateStream, device, stream, flags, timeNs, numElems);
    }

    private static ApiMethod SoapySDRDevice_deactivateStream = new ApiMethod("SoapySDRDevice_deactivateStream", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_deactivateStream(MemorySegment device, MemorySegment stream, int flags, long timeNs) {
        return (int) NativeUtils.call(SoapySDRDevice_deactivateStream, device, stream, flags, timeNs);
    }

    private static ApiMethod SoapySDRDevice_readStream = new ApiMethod("SoapySDRDevice_readStream", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_readStream(MemorySegment device, MemorySegment stream, MemorySegment buffs, long numElems, MemorySegment flags, MemorySegment timeNs, long timeoutUs) {
        var mh$ = SoapySDRDevice_readStream.HANDLE;
        try {
            return (int)mh$.invokeExact(device, stream, buffs, numElems, flags, timeNs, timeoutUs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }
    
    private static ApiMethod SoapySDRDevice_writeStream = new ApiMethod("SoapySDRDevice_writeStream", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_writeStream(MemorySegment device, MemorySegment stream, MemorySegment buffs, long numElems, MemorySegment flags, long timeNs, long timeoutUs) {
        var mh$ = SoapySDRDevice_writeStream.HANDLE;
        try {
            return (int)mh$.invokeExact(device, stream, buffs, numElems, flags, timeNs, timeoutUs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_readStreamStatus = new ApiMethod("SoapySDRDevice_readStreamStatus", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_readStreamStatus(MemorySegment device, MemorySegment stream, MemorySegment chanMask, MemorySegment flags, MemorySegment timeNs, long timeoutUs) {
        var mh$ = SoapySDRDevice_readStreamStatus.HANDLE;
        try {
            return (int)mh$.invokeExact(device, stream, chanMask, flags, timeNs, timeoutUs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_getNumDirectAccessBuffers = new ApiMethod("SoapySDRDevice_getNumDirectAccessBuffers", FunctionDescriptor.of(
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static long SoapySDRDevice_getNumDirectAccessBuffers(MemorySegment device, MemorySegment stream) {
        var mh$ = SoapySDRDevice_getNumDirectAccessBuffers.HANDLE;
        try {
            return (long)mh$.invokeExact(device, stream);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_getDirectAccessBufferAddrs = new ApiMethod("SoapySDRDevice_getDirectAccessBufferAddrs", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_getDirectAccessBufferAddrs(MemorySegment device, MemorySegment stream, long handle, MemorySegment buffs) {
        var mh$ = SoapySDRDevice_getDirectAccessBufferAddrs.HANDLE;
        try {
            return (int)mh$.invokeExact(device, stream, handle, buffs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_acquireReadBuffer = new ApiMethod("SoapySDRDevice_acquireReadBuffer", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_acquireReadBuffer(MemorySegment device, MemorySegment stream, MemorySegment handle, MemorySegment buffs, MemorySegment flags, MemorySegment timeNs, long timeoutUs) {
        var mh$ = SoapySDRDevice_acquireReadBuffer.HANDLE;
        try {
            return (int)mh$.invokeExact(device, stream, handle, buffs, flags, timeNs, timeoutUs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_releaseReadBuffer = new ApiMethod("SoapySDRDevice_releaseReadBuffer", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static void SoapySDRDevice_releaseReadBuffer(MemorySegment device, MemorySegment stream, long handle) {
        var mh$ = SoapySDRDevice_releaseReadBuffer.HANDLE;
        try {
            mh$.invokeExact(device, stream, handle);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_acquireWriteBuffer = new ApiMethod("SoapySDRDevice_acquireWriteBuffer", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_acquireWriteBuffer(MemorySegment device, MemorySegment stream, MemorySegment handle, MemorySegment buffs, long timeoutUs) {
        var mh$ = SoapySDRDevice_acquireWriteBuffer.HANDLE;
        try {
            return (int)mh$.invokeExact(device, stream, handle, buffs, timeoutUs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_releaseWriteBuffer = new ApiMethod("SoapySDRDevice_releaseWriteBuffer", FunctionDescriptor.ofVoid(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static void SoapySDRDevice_releaseWriteBuffer(MemorySegment device, MemorySegment stream, long handle, long numElems, MemorySegment flags, long timeNs) {
        var mh$ = SoapySDRDevice_releaseWriteBuffer.HANDLE;
        try {
            mh$.invokeExact(device, stream, handle, numElems, flags, timeNs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static ApiMethod SoapySDRDevice_listAntennas = new ApiMethod("SoapySDRDevice_listAntennas", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listAntennas(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listAntennas, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_setAntenna = new ApiMethod("SoapySDRDevice_setAntenna", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setAntenna(MemorySegment device, int direction, long channel, MemorySegment name) {
        return (int) NativeUtils.call(SoapySDRDevice_setAntenna, device, direction, channel, name);
    }

    private static ApiMethod SoapySDRDevice_getAntenna = new ApiMethod("SoapySDRDevice_getAntenna", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static MemorySegment SoapySDRDevice_getAntenna(MemorySegment device, int direction, long channel) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getAntenna, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_hasDCOffsetMode = new ApiMethod("SoapySDRDevice_hasDCOffsetMode", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_hasDCOffsetMode(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_hasDCOffsetMode, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_setDCOffsetMode = new ApiMethod("SoapySDRDevice_setDCOffsetMode", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_BOOLEAN
    ));
    public static int SoapySDRDevice_setDCOffsetMode(MemorySegment device, int direction, long channel, boolean automatic) {
        return (int) NativeUtils.call(SoapySDRDevice_setDCOffsetMode, device, direction, channel, automatic);
    }

    private static ApiMethod SoapySDRDevice_getDCOffsetMode = new ApiMethod("SoapySDRDevice_getDCOffsetMode", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_getDCOffsetMode(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_getDCOffsetMode, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_hasDCOffset = new ApiMethod("SoapySDRDevice_hasDCOffset", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_hasDCOffset(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_hasDCOffset, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_setDCOffset = new ApiMethod("SoapySDRDevice_setDCOffset", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setDCOffset(MemorySegment device, int direction, long channel, double offsetI, double offsetQ) {
        return (int) NativeUtils.call(SoapySDRDevice_setDCOffset, device, direction, channel, offsetI, offsetQ);
    }

    private static ApiMethod SoapySDRDevice_getDCOffset = new ApiMethod("SoapySDRDevice_getDCOffset", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_getDCOffset(MemorySegment device, int direction, long channel, MemorySegment offsetI, MemorySegment offsetQ) {
        return (int) NativeUtils.call(SoapySDRDevice_getDCOffset, device, direction, channel, offsetI, offsetQ);
    }

    private static ApiMethod SoapySDRDevice_hasIQBalance = new ApiMethod("SoapySDRDevice_hasIQBalance", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_hasIQBalance(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_hasIQBalance, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_setIQBalance = new ApiMethod("SoapySDRDevice_setIQBalance", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setIQBalance(MemorySegment device, int direction, long channel, double balanceI, double balanceQ) {
        return (int) NativeUtils.call(SoapySDRDevice_setIQBalance, device, direction, channel, balanceI, balanceQ);
    }

    private static ApiMethod SoapySDRDevice_getIQBalance = new ApiMethod("SoapySDRDevice_getIQBalance", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_getIQBalance(MemorySegment device, int direction, long channel, MemorySegment balanceI, MemorySegment balanceQ) {
        return (int) NativeUtils.call(SoapySDRDevice_getIQBalance, device, direction, channel, balanceI, balanceQ);
    }

    private static ApiMethod SoapySDRDevice_hasIQBalanceMode = new ApiMethod("SoapySDRDevice_hasIQBalanceMode", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_hasIQBalanceMode(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_hasIQBalanceMode, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_setIQBalanceMode = new ApiMethod("SoapySDRDevice_setIQBalanceMode", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_BOOLEAN
    ));
    public static int SoapySDRDevice_setIQBalanceMode(MemorySegment device, int direction, long channel, boolean automatic) {
        return (int) NativeUtils.call(SoapySDRDevice_setIQBalanceMode, device, direction, channel, automatic);
    }

    private static ApiMethod SoapySDRDevice_getIQBalanceMode = new ApiMethod("SoapySDRDevice_getIQBalanceMode", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_getIQBalanceMode(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_getIQBalanceMode, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_hasFrequencyCorrection = new ApiMethod("SoapySDRDevice_hasFrequencyCorrection", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_hasFrequencyCorrection(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_hasFrequencyCorrection, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_setFrequencyCorrection = new ApiMethod("SoapySDRDevice_setFrequencyCorrection", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setFrequencyCorrection(MemorySegment device, int direction, long channel, double value) {
        return (int) NativeUtils.call(SoapySDRDevice_setFrequencyCorrection, device, direction, channel, value);
    }

    private static ApiMethod SoapySDRDevice_getFrequencyCorrection = new ApiMethod("SoapySDRDevice_getFrequencyCorrection", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static double SoapySDRDevice_getFrequencyCorrection(MemorySegment device, int direction, long channel) {
        return (double) NativeUtils.call(SoapySDRDevice_getFrequencyCorrection, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_listGains = new ApiMethod("SoapySDRDevice_listGains", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listGains(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listGains, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_hasGainMode = new ApiMethod("SoapySDRDevice_hasGainMode", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_hasGainMode(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_hasGainMode, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_setGainMode = new ApiMethod("SoapySDRDevice_setGainMode", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_BOOLEAN
    ));
    public static int SoapySDRDevice_setGainMode(MemorySegment device, int direction, long channel, boolean automatic) {
        return (int) NativeUtils.call(SoapySDRDevice_setGainMode, device, direction, channel, automatic);
    }

    private static ApiMethod SoapySDRDevice_getGainMode = new ApiMethod("SoapySDRDevice_getGainMode", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static boolean SoapySDRDevice_getGainMode(MemorySegment device, int direction, long channel) {
        return (boolean) NativeUtils.call(SoapySDRDevice_getGainMode, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_setGain = new ApiMethod("SoapySDRDevice_setGain", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setGain(MemorySegment device, int direction, long channel, double value) {
        return (int) NativeUtils.call(SoapySDRDevice_setGain, device, direction, channel, value);
    }

    private static ApiMethod SoapySDRDevice_setGainElement = new ApiMethod("SoapySDRDevice_setGainElement", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setGainElement(MemorySegment device, int direction, long channel, MemorySegment name, double value) {
        return (int) NativeUtils.call(SoapySDRDevice_setGainElement, device, direction, channel, name, value);
    }

    private static ApiMethod SoapySDRDevice_getGain = new ApiMethod("SoapySDRDevice_getGain", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static double SoapySDRDevice_getGain(MemorySegment device, int direction, long channel) {
        return (double) NativeUtils.call(SoapySDRDevice_getGain, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_getGainElement = new ApiMethod("SoapySDRDevice_getGainElement", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static double SoapySDRDevice_getGainElement(MemorySegment device, int direction, long channel, MemorySegment name) {
        return (double) NativeUtils.call(SoapySDRDevice_getGainElement, device, direction, channel, name);
    }

    private static ApiMethod SoapySDRDevice_getGainRange = new ApiMethod("SoapySDRDevice_getGainRange", FunctionDescriptor.of(
        SoapySDRRange_h.layout(),
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static MemorySegment SoapySDRDevice_getGainRange(SegmentAllocator allocator, MemorySegment device, int direction, long channel) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getGainRange, allocator, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_getGainElementRange = new ApiMethod("SoapySDRDevice_getGainElementRange", FunctionDescriptor.of(
        SoapySDRRange_h.layout(),
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getGainElementRange(SegmentAllocator allocator, MemorySegment device, int direction, long channel, MemorySegment name) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getGainElementRange, allocator, device, direction, channel, name);
    }

    private static ApiMethod SoapySDRDevice_setFrequency = new ApiMethod("SoapySDRDevice_setFrequency", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setFrequency(MemorySegment device, int direction, long channel, double frequency, MemorySegment args) {
        return (int) NativeUtils.call(SoapySDRDevice_setFrequency, device, direction, channel, frequency, args);
    }

    private static ApiMethod SoapySDRDevice_setFrequencyComponent = new ApiMethod("SoapySDRDevice_setFrequencyComponent", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setFrequencyComponent(MemorySegment device, int direction, long channel, MemorySegment name, double frequency, MemorySegment args) {
        return (int) NativeUtils.call(SoapySDRDevice_setFrequencyComponent, device, direction, channel, name, frequency, args);
    }

    private static ApiMethod SoapySDRDevice_getFrequency = new ApiMethod("SoapySDRDevice_getFrequency", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static double SoapySDRDevice_getFrequency(MemorySegment device, int direction, long channel) {
        return (double) NativeUtils.call(SoapySDRDevice_getFrequency, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_getFrequencyComponent = new ApiMethod("SoapySDRDevice_getFrequencyComponent", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static double SoapySDRDevice_getFrequencyComponent(MemorySegment device, int direction, long channel, MemorySegment name) {
        return (double) NativeUtils.call(SoapySDRDevice_getFrequencyComponent, device, direction, channel, name);
    }

    private static ApiMethod SoapySDRDevice_listFrequencies = new ApiMethod("SoapySDRDevice_listFrequencies", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listFrequencies(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listFrequencies, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_getFrequencyRange = new ApiMethod("SoapySDRDevice_getFrequencyRange", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getFrequencyRange(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getFrequencyRange, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_getFrequencyRangeComponent = new ApiMethod("SoapySDRDevice_getFrequencyRangeComponent", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getFrequencyRangeComponent(MemorySegment device, int direction, long channel, MemorySegment name, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getFrequencyRangeComponent, device, direction, channel, name, length);
    }

    private static ApiMethod SoapySDRDevice_getFrequencyArgsInfo = new ApiMethod("SoapySDRDevice_getFrequencyArgsInfo", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getFrequencyArgsInfo(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getFrequencyArgsInfo, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_setSampleRate = new ApiMethod("SoapySDRDevice_setSampleRate", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setSampleRate(MemorySegment device, int direction, long channel, double rate) {
        return (int) NativeUtils.call(SoapySDRDevice_setSampleRate, device, direction, channel, rate);
    }

    private static ApiMethod SoapySDRDevice_getSampleRate = new ApiMethod("SoapySDRDevice_getSampleRate", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static double SoapySDRDevice_getSampleRate(MemorySegment device, int direction, long channel) {
        return (double) NativeUtils.call(SoapySDRDevice_getSampleRate, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_listSampleRates = new ApiMethod("SoapySDRDevice_listSampleRates", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listSampleRates(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listSampleRates, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_getSampleRateRange = new ApiMethod("SoapySDRDevice_getSampleRateRange", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getSampleRateRange(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getSampleRateRange, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_setBandwidth = new ApiMethod("SoapySDRDevice_setBandwidth", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setBandwidth(MemorySegment device, int direction, long channel, double bw) {
        return (int) NativeUtils.call(SoapySDRDevice_setBandwidth, device, direction, channel, bw);
    }

    private static ApiMethod SoapySDRDevice_getBandwidth = new ApiMethod("SoapySDRDevice_getBandwidth", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static double SoapySDRDevice_getBandwidth(MemorySegment device, int direction, long channel) {
        return (double) NativeUtils.call(SoapySDRDevice_getBandwidth, device, direction, channel);
    }

    private static ApiMethod SoapySDRDevice_listBandwidths = new ApiMethod("SoapySDRDevice_listBandwidths", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listBandwidths(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listBandwidths, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_getBandwidthRange = new ApiMethod("SoapySDRDevice_getBandwidthRange", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getBandwidthRange(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getBandwidthRange, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_setMasterClockRate = new ApiMethod("SoapySDRDevice_setMasterClockRate", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setMasterClockRate(MemorySegment device, double rate) {
        return (int) NativeUtils.call(SoapySDRDevice_setMasterClockRate, device, rate);
    }

    private static ApiMethod SoapySDRDevice_getMasterClockRate = new ApiMethod("SoapySDRDevice_getMasterClockRate", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS
    ));
    public static double SoapySDRDevice_getMasterClockRate(MemorySegment device) {
        return (double) NativeUtils.call(SoapySDRDevice_getMasterClockRate, device);
    }

    private static ApiMethod SoapySDRDevice_getMasterClockRates = new ApiMethod("SoapySDRDevice_getMasterClockRates", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getMasterClockRates(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getMasterClockRates, device, length);
    }

    private static ApiMethod SoapySDRDevice_setReferenceClockRate = new ApiMethod("SoapySDRDevice_setReferenceClockRate", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_DOUBLE
    ));
    public static int SoapySDRDevice_setReferenceClockRate(MemorySegment device, double rate) {
        return (int) NativeUtils.call(SoapySDRDevice_setReferenceClockRate, device, rate);
    }

    private static ApiMethod SoapySDRDevice_getReferenceClockRate = new ApiMethod("SoapySDRDevice_getReferenceClockRate", FunctionDescriptor.of(
        ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS
    ));
    public static double SoapySDRDevice_getReferenceClockRate(MemorySegment device) {
        return (double) NativeUtils.call(SoapySDRDevice_getReferenceClockRate, device);
    }

    private static ApiMethod SoapySDRDevice_getReferenceClockRates = new ApiMethod("SoapySDRDevice_getReferenceClockRates", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getReferenceClockRates(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getReferenceClockRates, device, length);
    }

    private static ApiMethod SoapySDRDevice_listClockSources = new ApiMethod("SoapySDRDevice_listClockSources", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listClockSources(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listClockSources, device, length);
    }

    private static ApiMethod SoapySDRDevice_setClockSource = new ApiMethod("SoapySDRDevice_setClockSource", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setClockSource(MemorySegment device, MemorySegment source) {
        return (int) NativeUtils.call(SoapySDRDevice_setClockSource, device, source);
    }

    private static ApiMethod SoapySDRDevice_getClockSource = new ApiMethod("SoapySDRDevice_getClockSource", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getClockSource(MemorySegment device) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getClockSource, device);
    }

    private static ApiMethod SoapySDRDevice_listTimeSources = new ApiMethod("SoapySDRDevice_listTimeSources", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listTimeSources(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listTimeSources, device, length);
    }

    private static ApiMethod SoapySDRDevice_setTimeSource = new ApiMethod("SoapySDRDevice_setTimeSource", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setTimeSource(MemorySegment device, MemorySegment source) {
        return (int) NativeUtils.call(SoapySDRDevice_setTimeSource, device, source);
    }

    private static ApiMethod SoapySDRDevice_getTimeSource = new ApiMethod("SoapySDRDevice_getTimeSource", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getTimeSource(MemorySegment device) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getTimeSource, device);
    }

    private static ApiMethod SoapySDRDevice_hasHardwareTime = new ApiMethod("SoapySDRDevice_hasHardwareTime", FunctionDescriptor.of(
        ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static boolean SoapySDRDevice_hasHardwareTime(MemorySegment device, MemorySegment what) {
        return (boolean) NativeUtils.call(SoapySDRDevice_hasHardwareTime, device, what);
    }

    private static ApiMethod SoapySDRDevice_getHardwareTime = new ApiMethod("SoapySDRDevice_getHardwareTime", FunctionDescriptor.of(
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static long SoapySDRDevice_getHardwareTime(MemorySegment device, MemorySegment what) {
        return (long) NativeUtils.call(SoapySDRDevice_getHardwareTime, device, what);
    }

    private static ApiMethod SoapySDRDevice_setHardwareTime = new ApiMethod("SoapySDRDevice_setHardwareTime", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setHardwareTime(MemorySegment device, long timeNs, MemorySegment what) {
        return (int) NativeUtils.call(SoapySDRDevice_setHardwareTime, device, timeNs, what);
    }

    private static ApiMethod SoapySDRDevice_setCommandTime = new ApiMethod("SoapySDRDevice_setCommandTime", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_setCommandTime(MemorySegment device, long timeNs, MemorySegment what) {
        return (int) NativeUtils.call(SoapySDRDevice_setCommandTime, device, timeNs, what);
    }

    private static ApiMethod SoapySDRDevice_listSensors = new ApiMethod("SoapySDRDevice_listSensors", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listSensors(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listSensors, device, length);
    }

    private static ApiMethod SoapySDRDevice_getSensorInfo = new ApiMethod("SoapySDRDevice_getSensorInfo", FunctionDescriptor.of(
        SoapySDRArgInfo_h.layout(),
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getSensorInfo(SegmentAllocator allocator, MemorySegment device, MemorySegment key) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getSensorInfo, allocator, device, key);
    }

    private static ApiMethod SoapySDRDevice_readSensor = new ApiMethod("SoapySDRDevice_readSensor", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_readSensor(MemorySegment device, MemorySegment key) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_readSensor, device, key);
    }

    private static ApiMethod SoapySDRDevice_listChannelSensors = new ApiMethod("SoapySDRDevice_listChannelSensors", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listChannelSensors(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listChannelSensors, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_getChannelSensorInfo = new ApiMethod("SoapySDRDevice_getChannelSensorInfo", FunctionDescriptor.of(
        SoapySDRArgInfo_h.layout(),
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getChannelSensorInfo(SegmentAllocator allocator, MemorySegment device, int direction, long channel, MemorySegment key) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getChannelSensorInfo, allocator, device, direction, channel, key);
    }

    private static ApiMethod SoapySDRDevice_readChannelSensor = new ApiMethod("SoapySDRDevice_readChannelSensor", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_readChannelSensor(MemorySegment device, int direction, long channel, MemorySegment key) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_readChannelSensor, device, direction, channel, key);
    }

    private static ApiMethod SoapySDRDevice_listRegisterInterfaces = new ApiMethod("SoapySDRDevice_listRegisterInterfaces", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listRegisterInterfaces(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listRegisterInterfaces, device, length);
    }

    private static ApiMethod SoapySDRDevice_writeRegister = new ApiMethod("SoapySDRDevice_writeRegister", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT
    ));
    public static int SoapySDRDevice_writeRegister(MemorySegment device, MemorySegment name, int addr, int value) {
        return (int) NativeUtils.call(SoapySDRDevice_writeRegister, device, name, addr, value);
    }

    private static ApiMethod SoapySDRDevice_readRegister = new ApiMethod("SoapySDRDevice_readRegister", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    ));
    public static int SoapySDRDevice_readRegister(MemorySegment device, MemorySegment name, int addr) {
        return (int) NativeUtils.call(SoapySDRDevice_readRegister, device, name, addr);
    }

    private static ApiMethod SoapySDRDevice_writeRegisters = new ApiMethod("SoapySDRDevice_writeRegisters", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_writeRegisters(MemorySegment device, MemorySegment name, int addr, MemorySegment value, long length) {
        return (int) NativeUtils.call(SoapySDRDevice_writeRegisters, device, name, addr, value, length);
    }

    private static ApiMethod SoapySDRDevice_readRegisters = new ApiMethod("SoapySDRDevice_readRegisters", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_readRegisters(MemorySegment device, MemorySegment name, int addr, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_readRegisters, device, name, addr, length);
    }

    private static ApiMethod SoapySDRDevice_getSettingInfo = new ApiMethod("SoapySDRDevice_getSettingInfo", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getSettingInfo(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getSettingInfo, device, length);
    }
    
    
    private static ApiMethod SoapySDRDevice_getSettingInfoWithKey = new ApiMethod("SoapySDRDevice_getSettingInfoWithKey", FunctionDescriptor.of(
        SoapySDRArgInfo_h.layout(),
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    )); // since 0.8-1
    public static MemorySegment SoapySDRDevice_getSettingInfoWithKey(SegmentAllocator allocator, MemorySegment device, MemorySegment key) throws UnsupportedOperationException {
    	ApiMethod method = SoapySDRDevice_getSettingInfoWithKey;
    	if (method.HANDLE == null) {
    		throw new UnsupportedOperationException("Couldn't be resolved!");
    	}
        return (MemorySegment) NativeUtils.call(method, allocator, device, key);
    }

    private static ApiMethod SoapySDRDevice_writeSetting = new ApiMethod("SoapySDRDevice_writeSetting", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_writeSetting(MemorySegment device, MemorySegment key, MemorySegment value) {
        return (int) NativeUtils.call(SoapySDRDevice_writeSetting, device, key, value);
    }

    private static ApiMethod SoapySDRDevice_readSetting = new ApiMethod("SoapySDRDevice_readSetting", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_readSetting(MemorySegment device, MemorySegment key) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_readSetting, device, key);
    }

    private static ApiMethod SoapySDRDevice_getChannelSettingInfo = new ApiMethod("SoapySDRDevice_getChannelSettingInfo", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getChannelSettingInfo(MemorySegment device, int direction, long channel, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getChannelSettingInfo, device, direction, channel, length);
    }

    private static ApiMethod SoapySDRDevice_getChannelSettingInfoWithKey = new ApiMethod("SoapySDRDevice_getChannelSettingInfoWithKey", FunctionDescriptor.of(
        SoapySDRArgInfo_h.layout(),
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )); // since 0.8-1
    public static MemorySegment SoapySDRDevice_getChannelSettingInfoWithKey(SegmentAllocator allocator, MemorySegment device, int direction, long channel, MemorySegment key) throws UnsupportedOperationException {
    	ApiMethod method = SoapySDRDevice_getChannelSettingInfoWithKey;
    	if (method.HANDLE == null) {
    		throw new UnsupportedOperationException("Couldn't be resolved!");
    	}
        return (MemorySegment) NativeUtils.call(method, allocator, device, direction, channel, key);
    }

    private static ApiMethod SoapySDRDevice_writeChannelSetting = new ApiMethod("SoapySDRDevice_writeChannelSetting", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_writeChannelSetting(MemorySegment device, int direction, long channel, MemorySegment key, MemorySegment value) {
        return (int) NativeUtils.call(SoapySDRDevice_writeChannelSetting, device, direction, channel, key, value);
    }

    private static ApiMethod SoapySDRDevice_readChannelSetting = new ApiMethod("SoapySDRDevice_readChannelSetting", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_readChannelSetting(MemorySegment device, int direction, long channel, MemorySegment key) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_readChannelSetting, device, direction, channel, key);
    }

    private static ApiMethod SoapySDRDevice_listGPIOBanks = new ApiMethod("SoapySDRDevice_listGPIOBanks", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listGPIOBanks(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listGPIOBanks, device, length);
    }

    private static ApiMethod SoapySDRDevice_writeGPIO = new ApiMethod("SoapySDRDevice_writeGPIO", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    ));
    public static int SoapySDRDevice_writeGPIO(MemorySegment device, MemorySegment bank, int value) {
        return (int) NativeUtils.call(SoapySDRDevice_writeGPIO, device, bank, value);
    }

    private static ApiMethod SoapySDRDevice_writeGPIOMasked = new ApiMethod("SoapySDRDevice_writeGPIOMasked", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT
    ));
    public static int SoapySDRDevice_writeGPIOMasked(MemorySegment device, MemorySegment bank, int value, int mask) {
        return (int) NativeUtils.call(SoapySDRDevice_writeGPIOMasked, device, bank, value, mask);
    }

    private static ApiMethod SoapySDRDevice_readGPIO = new ApiMethod("SoapySDRDevice_readGPIO", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_readGPIO(MemorySegment device, MemorySegment bank) {
        return (int) NativeUtils.call(SoapySDRDevice_readGPIO, device, bank);
    }

    private static ApiMethod SoapySDRDevice_writeGPIODir = new ApiMethod("SoapySDRDevice_writeGPIODir", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    ));
    public static int SoapySDRDevice_writeGPIODir(MemorySegment device, MemorySegment bank, int dir) {
        return (int) NativeUtils.call(SoapySDRDevice_writeGPIODir, device, bank, dir);
    }

    private static ApiMethod SoapySDRDevice_writeGPIODirMasked = new ApiMethod("SoapySDRDevice_writeGPIODirMasked", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT
    ));
    public static int SoapySDRDevice_writeGPIODirMasked(MemorySegment device, MemorySegment bank, int dir, int mask) {
        return (int) NativeUtils.call(SoapySDRDevice_writeGPIODirMasked, device, bank, dir, mask);
    }

    private static ApiMethod SoapySDRDevice_readGPIODir = new ApiMethod("SoapySDRDevice_readGPIODir", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_readGPIODir(MemorySegment device, MemorySegment bank) {
        return (int) NativeUtils.call(SoapySDRDevice_readGPIODir, device, bank);
    }

    private static ApiMethod SoapySDRDevice_writeI2C = new ApiMethod("SoapySDRDevice_writeI2C", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_writeI2C(MemorySegment device, int addr, MemorySegment data, long numBytes) {
        return (int) NativeUtils.call(SoapySDRDevice_writeI2C, device, addr, data, numBytes);
    }

    private static ApiMethod SoapySDRDevice_readI2C = new ApiMethod("SoapySDRDevice_readI2C", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_readI2C(MemorySegment device, int addr, MemorySegment numBytes) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_readI2C, device, addr, numBytes);
    }

    private static ApiMethod SoapySDRDevice_transactSPI = new ApiMethod("SoapySDRDevice_transactSPI", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    ));
    public static int SoapySDRDevice_transactSPI(MemorySegment device, int addr, int data, long numBits) {
        return (int) NativeUtils.call(SoapySDRDevice_transactSPI, device, addr, data, numBits);
    }

    private static ApiMethod SoapySDRDevice_listUARTs = new ApiMethod("SoapySDRDevice_listUARTs", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_listUARTs(MemorySegment device, MemorySegment length) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_listUARTs, device, length);
    }

    private static ApiMethod SoapySDRDevice_writeUART = new ApiMethod("SoapySDRDevice_writeUART", FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static int SoapySDRDevice_writeUART(MemorySegment device, MemorySegment which, MemorySegment data) {
        return (int) NativeUtils.call(SoapySDRDevice_writeUART, device, which, data);
    }

    private static ApiMethod SoapySDRDevice_readUART = new ApiMethod("SoapySDRDevice_readUART", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    ));
    public static MemorySegment SoapySDRDevice_readUART(MemorySegment device, MemorySegment which, long timeoutUs) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_readUART, device, which, timeoutUs);
    }

    private static ApiMethod SoapySDRDevice_getNativeDeviceHandle = new ApiMethod("SoapySDRDevice_getNativeDeviceHandle", FunctionDescriptor.of(
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    ));
    public static MemorySegment SoapySDRDevice_getNativeDeviceHandle(MemorySegment device) {
        return (MemorySegment) NativeUtils.call(SoapySDRDevice_getNativeDeviceHandle, device);
    }




}

