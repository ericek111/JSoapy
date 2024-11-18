package eu.lixko.jsoapy.soapy;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SoapySDRConverterFunctionPriority {
		
	SOAPY_SDR_CONVERTER_GENERIC(0),
	SOAPY_SDR_CONVERTER_VECTORIZED(3),
	SOAPY_SDR_CONVERTER_CUSTOM(5);
	
	private int code;
	
	private static final Map<Integer, SoapySDRConverterFunctionPriority> CODE_TO_ENUM_MAP = new HashMap<>();

    static {
        for (SoapySDRConverterFunctionPriority type : EnumSet.allOf(SoapySDRConverterFunctionPriority.class)) {
        	CODE_TO_ENUM_MAP.put(type.code, type);
        }
    }
    
	private SoapySDRConverterFunctionPriority(int code) {
		this.code = code;
	}
	
	public int code() {
		return this.code;
	}
	
	public static SoapySDRConverterFunctionPriority fromCode(int code) {
        return CODE_TO_ENUM_MAP.get(code);
    }

}

