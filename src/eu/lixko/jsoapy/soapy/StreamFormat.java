package eu.lixko.jsoapy.soapy;

import java.lang.foreign.MemorySegment;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import eu.lixko.jsoapy.util.NativeUtils;

public enum StreamFormat {
	
	CF64,
	CF32,
	CS32,
	CU32,
	CS16,
	CU16,
	CS12,
	CU12,
	CS8,
	CU8,
	CS4,
	CU4,
	F64,
	F32,
	S32,
	U32,
	S16,
	U16,
	S8,
	U8;
	
	private final MemorySegment addr;
	private final int bits;
	private final int byteSize;
	private final boolean isComplex;
    
	private StreamFormat() {
		String name = this.name();
		this.addr = NativeUtils.allocateFrom(name);
		this.bits = formatToBits(name);
		this.byteSize = this.bits / 8;
		this.isComplex = name.indexOf('C') != -1;
	}
	
	public MemorySegment addr() {
		return this.addr;
	}
	
	public int bits() {
		return this.bits;
	}
	
	public int byteSize() {
		return this.byteSize;
	}
	
	public boolean isComplex() {
		return this.isComplex;
	}
	
	public static int formatToBits(String format) {
        int size = 0;
        boolean isComplex = false;
        
        for (int i = 0; i < format.length(); i++) {
            char ch = format.charAt(i);
            if (ch == 'C') isComplex = true;
            if (Character.isDigit(ch)) size = (size * 10) + (ch - '0');
        }
        
        if (isComplex) size *= 2;
        return size;
	}
	
	/*
	 * Reimplemented in Java. Not sure if the division by 8 is OK? The byteSize stored in the enums is ceil-ed to a full byte.
	 */
	public static int SoapySDR_formatToSize(String format) {
		return formatToBits(format) / 8; // bits to bytes 
    }
}

