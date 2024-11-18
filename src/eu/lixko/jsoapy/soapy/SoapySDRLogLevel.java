package eu.lixko.jsoapy.soapy;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SoapySDRLogLevel {
	
	// Log level labels are derived from the enum value names.
	
	SOAPY_SDR_FATAL(1),
	SOAPY_SDR_CRITICAL(2),
	SOAPY_SDR_ERROR(3),
	SOAPY_SDR_WARNING(4),
	SOAPY_SDR_NOTICE(5),
	SOAPY_SDR_INFO(6),
	SOAPY_SDR_DEBUG(7),
	SOAPY_SDR_TRACE(8),
	SOAPY_SDR_SSI(9);
	
	private int code;
	private String label;
	
	private static final Map<Integer, SoapySDRLogLevel> CODE_TO_ENUM_MAP = new HashMap<>();
	private static final Map<String, SoapySDRLogLevel> LABEL_TO_ENUM_MAP = new HashMap<>();

    static {
        for (SoapySDRLogLevel type : EnumSet.allOf(SoapySDRLogLevel.class)) {
        	CODE_TO_ENUM_MAP.put(type.code, type);
        	LABEL_TO_ENUM_MAP.put(type.label, type);
        }
    }
    
	private SoapySDRLogLevel(int code) {
		this.code = code;
		this.label = this.name().replace("SOAPY_SDR_", "");
	}
	
	public String label() {
		return this.label;
	}
	
	public int code() {
		return this.code;
	}
	
	public static SoapySDRLogLevel fromCode(int code) {
        return CODE_TO_ENUM_MAP.get(code);
    }
	
	public static SoapySDRLogLevel fromLabel(String label) {
        return LABEL_TO_ENUM_MAP.get(label);
    }

}

