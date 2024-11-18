package eu.lixko.jsoapy.soapy;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Errors_h {
	
	// Error messages are derived from the enum value names.
	
	SOAPY_SDR_TIMEOUT(-1),
	SOAPY_SDR_STREAM_ERROR(-2),
	SOAPY_SDR_CORRUPTION(-3),
	SOAPY_SDR_OVERFLOW(-4),
	SOAPY_SDR_NOT_SUPPORTED(-5),
	SOAPY_SDR_TIME_ERROR(-6),
	SOAPY_SDR_UNDERFLOW(-7);
	
	private int code;
	private String message;
	
	private static final Map<Integer, Errors_h> CODE_TO_ENUM_MAP = new HashMap<>();

    static {
        for (Errors_h type : EnumSet.allOf(Errors_h.class)) {
        	CODE_TO_ENUM_MAP.put(type.code, type);
        }
    }
    
	private Errors_h(int code) {
		this.code = code;
		this.message = this.name().replace("SOAPY_SDR_", "");
	}
	
	public String message() {
		return this.message;
	}
	
	public int code() {
		return this.code;
	}
	
	public static Errors_h fromCode(int code) {
        return CODE_TO_ENUM_MAP.get(code);
    }
	
	public static String SoapySDR_errToStr(int errorCode) {
		return fromCode(errorCode).message();
	}

}

