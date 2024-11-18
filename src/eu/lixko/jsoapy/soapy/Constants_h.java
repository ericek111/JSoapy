package eu.lixko.jsoapy.soapy;

import java.lang.foreign.MemorySegment;

import eu.lixko.jsoapy.util.NativeUtils;

public class Constants_h {

    public static final int SOAPY_SDR_TX = 0;
	public static final int SOAPY_SDR_RX = 1;
    public static final int SOAPY_SDR_END_BURST = 1 << 1;
    public static final int SOAPY_SDR_HAS_TIME = 1 << 2;
    public static final int SOAPY_SDR_END_ABRUPT = 1 << 3;
    public static final int SOAPY_SDR_ONE_PACKET = 1 << 4;
    public static final int SOAPY_SDR_MORE_FRAGMENTS = 1 << 5;
    public static final int SOAPY_SDR_WAIT_TRIGGER = 1 << 6;
    public static final int SOAPY_SDR_USER_FLAG0 = 1 << 16;
    public static final int SOAPY_SDR_USER_FLAG1 = 1 << 17;
    public static final int SOAPY_SDR_USER_FLAG2 = 1 << 18;
    public static final int SOAPY_SDR_USER_FLAG3 = 1 << 19;
    public static final int SOAPY_SDR_USER_FLAG4 = 1 << 20;
    
    public static final MemorySegment SOAPY_SDR_TRUE = NativeUtils.allocateFrom("true");
    public static final MemorySegment SOAPY_SDR_FALSE = NativeUtils.allocateFrom("false");

}
