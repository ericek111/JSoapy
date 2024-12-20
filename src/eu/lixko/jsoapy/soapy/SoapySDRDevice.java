package eu.lixko.jsoapy.soapy;

import static java.lang.foreign.ValueLayout.JAVA_LONG;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;
import java.util.stream.Collectors;

import eu.lixko.jsoapy.util.NativeUtils;

public class SoapySDRDevice {
	
	protected MemorySegment addr;
	
	public SoapySDRDevice(MemorySegment addr) {
		if (addr.address() == 0) {
			throw new NullPointerException("SoapySDRDevice cannot be created, NULL passed.");
		}
		this.addr = addr;
	}
	
	public MemorySegment getAddress() {
		return this.addr;
	}

	public static int lastStatus() {
		return Device_h.SoapySDRDevice_lastStatus();
	}
	
	public static String lastError() {
		return NativeUtils.segmentToString(Device_h.SoapySDRDevice_lastError());
	}
	
	public static List<SoapySDRKwargs> enumerate(SoapySDRKwargs args) {
		return NativeUtils.invokeGetStructs(
			lengthOut -> Device_h.SoapySDRDevice_enumerate(args.getStruct(), lengthOut),
			item -> new SoapySDRKwargs(item, null),
			SoapySDRKwargs_h.layout()
		);
	}
	
	public static List<SoapySDRKwargs> enumerateStrArgs(String args) {
		try (Arena arena = Arena.ofConfined()) {
			return NativeUtils.invokeGetStructs(
				lengthOut -> Device_h.SoapySDRDevice_enumerateStrArgs(arena.allocateFrom(args), lengthOut),
				item -> new SoapySDRKwargs(item, null),
				SoapySDRKwargs_h.layout()
			);
		}
	}
	
	public static SoapySDRDevice make(SoapySDRKwargs args) {
		return new SoapySDRDevice(Device_h.SoapySDRDevice_make(args.getStruct()));
	}
	
	public static SoapySDRDevice makeStrArgs(String args) {
		try (Arena arena = Arena.ofConfined()) {
			return new SoapySDRDevice(Device_h.SoapySDRDevice_makeStrArgs(arena.allocateFrom(args)));
		}
	}
	
	public int unmake() {
		return Device_h.SoapySDRDevice_unmake(this.addr);
	}
	
	public boolean isClosed() {
		return this.addr == null;
	}
	
	public String getDriverKey() {
		return NativeUtils.segmentToString(Device_h.SoapySDRDevice_getDriverKey(this.addr));
	}
	
	public String getHardwareKey() {
		return NativeUtils.segmentToString(Device_h.SoapySDRDevice_getHardwareKey(this.addr));
	}
	
	public SoapySDRKwargs getHardwareInfo() {
		Arena arena = Arena.ofAuto();
		MemorySegment hwinfo = Device_h.SoapySDRDevice_getHardwareInfo(arena, addr);
		return new SoapySDRKwargs(hwinfo, Arena.ofAuto());
	}
	
	public int setFrontendMapping(SoapySDRDeviceDirection direction, String mapping) {
		try (Arena arena = Arena.ofConfined()) {
			return Device_h.SoapySDRDevice_setFrontendMapping(this.addr, direction.ordinal(), arena.allocateFrom(mapping));
		}
	}
	
	public String getFrontendMapping(SoapySDRDeviceDirection direction) {
		return NativeUtils.segmentToString(Device_h.SoapySDRDevice_getFrontendMapping(this.addr, direction.ordinal()));
	}
	
	public long getNumChannels(SoapySDRDeviceDirection direction) {
		return Device_h.SoapySDRDevice_getNumChannels(this.addr, direction.ordinal());
	}
	
	public SoapySDRKwargs getChannelInfo(SoapySDRDeviceDirection direction, long channel) {
		Arena arena = Arena.ofAuto();
		MemorySegment hwinfo = Device_h.SoapySDRDevice_getChannelInfo(arena, addr, direction.ordinal(), channel);
		return new SoapySDRKwargs(hwinfo, Arena.ofAuto());
	}
	
	public boolean getFullDuplex(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getFullDuplex(this.addr, direction.ordinal(), channel);
	}
	
	public List<StreamFormat> getStreamFormats(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_getStreamFormats(this.addr, direction.ordinal(), channel, lengthOut))
				.stream().map(str -> StreamFormat.valueOf(str)).collect(Collectors.toUnmodifiableList());
	}
	
	public NativeStreamFormat getNativeStreamFromat(SoapySDRDeviceDirection direction, long channel) {
		try (Arena arena = Arena.ofConfined()) {
			MemorySegment fullScalePtr = arena.allocate(ValueLayout.JAVA_DOUBLE);
			MemorySegment formatStrPtr = Device_h.SoapySDRDevice_getNativeStreamFormat(this.addr, direction.ordinal(), channel, fullScalePtr);
			double fullScale = fullScalePtr.get(ValueLayout.JAVA_DOUBLE, 0);
			return new NativeStreamFormat(StreamFormat.valueOf(NativeUtils.segmentToString(formatStrPtr)), fullScale);
		}
	}
	
	public record NativeStreamFormat(StreamFormat format, double fullScale) {
	}
	
	public List<SoapySDRArgInfo> getStreamArgsInfo(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getStreamArgsInfo(this.addr, direction.ordinal(), channel, lengthOut), item -> SoapySDRArgInfo.fromStruct(item), SoapySDRArgInfo_h.layout());
	}
	
	/**
	 * @see SoapySDRStream
	 * @param direction
	 * @param format
	 * @param channels
	 * @param args
	 * @return
	 */
	public SoapySDRStream setupStream(
		SoapySDRDeviceDirection direction, 
		StreamFormat format, 
		long[] channels,
		SoapySDRKwargs args
	) {
		return new SoapySDRStream(this, null, direction, format, channels, args);
	}
	
	// The rest of the Stream API (readStream, writeStream, acquireReadBuffer...) is implemented in {@link SoapySDRStream}.
	
	public List<String> listAntennas(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listAntennas(this.addr, direction.ordinal(), channel, lengthOut));
	}
	
	public int setAntenna(SoapySDRDeviceDirection direction, long channel, String name) {
		try (Arena arena = Arena.ofConfined()) {
			return Device_h.SoapySDRDevice_setAntenna(this.addr, direction.ordinal(), channel, arena.allocateFrom(name));
		}
	}
	
	public String getAntenna(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.segmentToString(Device_h.SoapySDRDevice_getAntenna(this.addr, direction.ordinal(), channel));
	}
	
	// Frontend corrections API
	
	public boolean hasDCOffsetMode(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_hasDCOffsetMode(this.addr, direction.ordinal(), channel);
	}
	
	public int setDCOffsetMode(SoapySDRDeviceDirection direction, long channel, boolean automatic) {
		return Device_h.SoapySDRDevice_setDCOffsetMode(this.addr, direction.ordinal(), channel, automatic);
	}
	
	public boolean getDCOffsetMode(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getDCOffsetMode(this.addr, direction.ordinal(), channel);
	}
	
	public boolean hasDCOffset(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_hasDCOffset(this.addr, direction.ordinal(), channel);
	}
	
	public int setDCOffset(SoapySDRDeviceDirection direction, long channel, double offsetI, double offsetQ) {
		return Device_h.SoapySDRDevice_setDCOffset(this.addr, direction.ordinal(), channel, offsetI, offsetQ);
	}
	
	public double[] getDCOffset(SoapySDRDeviceDirection direction, long channel) {
		try (Arena arena = Arena.ofConfined()) {
			MemorySegment valI = arena.allocate(ValueLayout.JAVA_DOUBLE);
			MemorySegment valQ = arena.allocate(ValueLayout.JAVA_DOUBLE);
			Device_h.SoapySDRDevice_getDCOffset(this.addr, direction.ordinal(), channel, valI, valQ);
			return new double[] {valI.get(ValueLayout.JAVA_DOUBLE, 0), valQ.get(ValueLayout.JAVA_DOUBLE, 1)};
		}
	}
	
	public boolean hasIQBalance(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_hasIQBalance(this.addr, direction.ordinal(), channel);
	}
	
	public int setIQBalance(SoapySDRDeviceDirection direction, long channel, double balanceI, double balanceQ) {
		return Device_h.SoapySDRDevice_setIQBalance(this.addr, direction.ordinal(), channel, balanceI, balanceQ);
	}
	
	public double[] getIQBalance(SoapySDRDeviceDirection direction, long channel) {
		try (Arena arena = Arena.ofConfined()) {
			MemorySegment valI = arena.allocate(ValueLayout.JAVA_DOUBLE);
			MemorySegment valQ = arena.allocate(ValueLayout.JAVA_DOUBLE);
			Device_h.SoapySDRDevice_getIQBalance(this.addr, direction.ordinal(), channel, valI, valQ);
			return new double[] {valI.get(ValueLayout.JAVA_DOUBLE, 0), valQ.get(ValueLayout.JAVA_DOUBLE, 1)};
		}
	}
	
	public boolean hasIQBalanceMode(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_hasIQBalanceMode(this.addr, direction.ordinal(), channel);
	}
	
	public int setIQBalanceMode(SoapySDRDeviceDirection direction, long channel, boolean automatic) {
		return Device_h.SoapySDRDevice_setIQBalanceMode(this.addr, direction.ordinal(), channel, automatic);
	}
	
	public boolean getIQBalanceMode(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getIQBalanceMode(this.addr, direction.ordinal(), channel);
	}
	
	public boolean hasFrequencyCorrection(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_hasFrequencyCorrection(this.addr, direction.ordinal(), channel);
	}
	
	public int setFrequencyCorrection(SoapySDRDeviceDirection direction, long channel, double value) {
		return Device_h.SoapySDRDevice_setFrequencyCorrection(this.addr, direction.ordinal(), channel, value);
	}
	
	public double getFrequencyCorrection(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getFrequencyCorrection(this.addr, direction.ordinal(), channel);
	}
	
	// Gain API
	
	public List<String> listGains(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listGains(this.addr, direction.ordinal(), channel, lengthOut));
	}
	
	public boolean hasGainMode(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_hasGainMode(this.addr, direction.ordinal(), channel);
	}
	
	public int setGainMode(SoapySDRDeviceDirection direction, long channel, boolean automatic) {
		return Device_h.SoapySDRDevice_setGainMode(this.addr, direction.ordinal(), channel, automatic);
	}
	
	public boolean getGainMode(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getGainMode(this.addr, direction.ordinal(), channel);
	}
	
	public int setGain(SoapySDRDeviceDirection direction, long channel, double value) {
		return Device_h.SoapySDRDevice_setGain(this.addr, direction.ordinal(), channel, value);
	}
	
	public int setGain(SoapySDRDeviceDirection direction, long channel, String name, double value) {
		try (Arena arena = Arena.ofConfined()) {	
			return Device_h.SoapySDRDevice_setGainElement(this.addr, direction.ordinal(), channel, arena.allocateFrom(name), value);
		}
	}
	
	public double getGain(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getGain(this.addr, direction.ordinal(), channel);
	}
	
	public double getGain(SoapySDRDeviceDirection direction, long channel, String name) {
		try (Arena arena = Arena.ofConfined()) {	
			return Device_h.SoapySDRDevice_getGainElement(this.addr, direction.ordinal(), channel, arena.allocateFrom(name));
		}
	}
	
	public SoapySDRRange getGainRange(SoapySDRDeviceDirection direction, long channel) {
		try (Arena arena = Arena.ofConfined()) {
			MemorySegment ptr = Device_h.SoapySDRDevice_getGainRange(arena, addr, direction.ordinal(), channel);
			return SoapySDRRange.fromStruct(ptr);
		}
	}
	
	public SoapySDRRange getGainRange(SoapySDRDeviceDirection direction, long channel, String name) {
		try (Arena arena = Arena.ofConfined()) {
			MemorySegment ptr = Device_h.SoapySDRDevice_getGainElementRange(arena, addr, direction.ordinal(), channel, arena.allocateFrom(name));
			return SoapySDRRange.fromStruct(ptr);
		}
	}
	
	public int setFrequency(SoapySDRDeviceDirection direction, long channel, double frequency, SoapySDRKwargs args) {
		return Device_h.SoapySDRDevice_setFrequency(this.addr, direction.ordinal(), channel, frequency, args != null ? args.getStruct() : null);
	}
	
	public int setFrequency(SoapySDRDeviceDirection direction, long channel, double frequency) {
		return setFrequency(direction, channel, frequency, null);
	}
	
	public int setFrequency(SoapySDRDeviceDirection direction, long channel, String name, double frequency, SoapySDRKwargs args) {
		try (Arena arena = Arena.ofConfined()) {
			return Device_h.SoapySDRDevice_setFrequencyComponent(this.addr, direction.ordinal(), channel, arena.allocateFrom(name), frequency, args != null ? args.getStruct() : null);
		}
	}
	
	public int setFrequency(SoapySDRDeviceDirection direction, long channel, String name, double frequency) {
		return setFrequency(direction, channel, name, frequency, null);
	}
	
	public double getFrequency(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getFrequency(this.addr, direction.ordinal(), channel);
	}
	
	public double getFrequency(SoapySDRDeviceDirection direction, long channel, String name) {
		try (Arena arena = Arena.ofConfined()) {
			return Device_h.SoapySDRDevice_getFrequencyComponent(this.addr, direction.ordinal(), channel, arena.allocateFrom(name));	
		}
	}
	
	public List<String> listFrequencies(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listFrequencies(this.addr, direction.ordinal(), channel, lengthOut));
	}
	
	public List<SoapySDRRange> getFrequencyRange(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getFrequencyRange(this.addr, direction.ordinal(), channel, lengthOut), item -> SoapySDRRange.fromStruct(item), SoapySDRRange_h.layout());
	}
	
	public List<SoapySDRRange> getFrequencyRange(SoapySDRDeviceDirection direction, long channel, String name) {
		try (Arena arena = Arena.ofConfined()) {
			return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getFrequencyRangeComponent(this.addr, direction.ordinal(), channel, arena.allocateFrom(name), lengthOut), item -> SoapySDRRange.fromStruct(item), SoapySDRRange_h.layout());
		}
	}
	
	public List<SoapySDRArgInfo> getFrequencyArgsInfo(SoapySDRDeviceDirection direction, long channel, String name) {
		return NativeUtils.invokeGetStructs(
			lengthOut -> Device_h.SoapySDRDevice_getFrequencyArgsInfo(this.addr, direction.ordinal(), channel, lengthOut),
			item -> SoapySDRArgInfo.fromStruct(item), SoapySDRArgInfo_h.layout()
		);
	}
	
	// Sample Rate API
	
	public int setSampleRate(SoapySDRDeviceDirection direction, long channel, double rate) {
		return Device_h.SoapySDRDevice_setSampleRate(this.addr, direction.ordinal(), channel, rate);
	}
	
	public double getSampleRate(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getSampleRate(this.addr, direction.ordinal(), channel);
	}
	
	public List<Double> listSampleRates(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_listSampleRates(this.addr, direction.ordinal(), channel, lengthOut), item -> item.get(ValueLayout.JAVA_DOUBLE, 0), ValueLayout.JAVA_DOUBLE);
	}
	
	public List<SoapySDRRange> getSampleRateRange(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getSampleRateRange(this.addr, direction.ordinal(), channel, lengthOut), item -> SoapySDRRange.fromStruct(item), SoapySDRRange_h.layout());
	}
	
	// Bandwidth API
	
	public int setBandwidth(SoapySDRDeviceDirection direction, long channel, double rate) {
		return Device_h.SoapySDRDevice_setBandwidth(this.addr, direction.ordinal(), channel, rate);
	}
	
	public double getBandwidth(SoapySDRDeviceDirection direction, long channel) {
		return Device_h.SoapySDRDevice_getBandwidth(this.addr, direction.ordinal(), channel);
	}
	
	public List<Double> listBandwidths(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_listBandwidths(this.addr, direction.ordinal(), channel, lengthOut), item -> item.get(ValueLayout.JAVA_DOUBLE, 0), ValueLayout.JAVA_DOUBLE);
	}
	
	public List<SoapySDRRange> getBandwidthRange(SoapySDRDeviceDirection direction, long channel) {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getBandwidthRange(this.addr, direction.ordinal(), channel, lengthOut), item -> SoapySDRRange.fromStruct(item), SoapySDRRange_h.layout());
	}
	
	// Clocking API
	
	public int setMasterClockRate(double rate) {
		return Device_h.SoapySDRDevice_setMasterClockRate(this.addr, rate);
	}
	
	public double getMasterClockRate() {
		return Device_h.SoapySDRDevice_getMasterClockRate(this.addr);
	}
	
	public List<SoapySDRRange> getMasterClockRates() {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getMasterClockRates(this.addr, lengthOut), item -> SoapySDRRange.fromStruct(item), SoapySDRRange_h.layout());
	}
	
	public int setReferenceClockRate(double rate) {
		return Device_h.SoapySDRDevice_setReferenceClockRate(this.addr, rate);
	}
	
	public double getReferenceClockRate() {
		return Device_h.SoapySDRDevice_getReferenceClockRate(this.addr);
	}
	
	public List<SoapySDRRange> getReferenceClockRates() {
		return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getReferenceClockRates(this.addr, lengthOut), item -> SoapySDRRange.fromStruct(item), SoapySDRRange_h.layout());
	}
	
	public List<String> listClockSources() {
		return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listClockSources(this.addr, lengthOut));
	}
	
	public int setClockSource(String source) {
		try (Arena arena = Arena.ofConfined()) {
			return Device_h.SoapySDRDevice_setClockSource(this.addr, arena.allocateFrom(source));
		}
	}
	
	public String getClockSource() {
		return NativeUtils.segmentToString(Device_h.SoapySDRDevice_getClockSource(this.addr));
	}
	
	public List<String> listTimeSources() {
	    return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listTimeSources(this.addr, lengthOut));
	}

	public int setTimeSource(String source) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_setTimeSource(this.addr, arena.allocateFrom(source));
	    }
	}

	public String getTimeSource() {
	    return NativeUtils.segmentToString(Device_h.SoapySDRDevice_getTimeSource(this.addr));
	}

	public boolean hasHardwareTime(String what) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_hasHardwareTime(this.addr, arena.allocateFrom(what));
	    }
	}

	public long getHardwareTime(String what) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_getHardwareTime(this.addr, arena.allocateFrom(what));
	    }
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public int setHardwareTime(long timeNs, String what) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_setHardwareTime(this.addr, timeNs, arena.allocateFrom(what));
	    }
	}

	public int setCommandTime(long timeNs, String what) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_setCommandTime(this.addr, timeNs, arena.allocateFrom(what));
	    }
	}
	
	// Sensor API

	public List<String> listSensors() {
	    return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listSensors(this.addr, lengthOut));
	}

	public SoapySDRArgInfo getSensorInfo(String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return SoapySDRArgInfo.fromStruct(Device_h.SoapySDRDevice_getSensorInfo(arena, addr, arena.allocateFrom(key)));
	    }
	}

	public String readSensor(String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return NativeUtils.segmentToString(Device_h.SoapySDRDevice_readSensor(this.addr, arena.allocateFrom(key)));
	    }
	}

	public List<String> listSensors(int direction, long channel) {
	    return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listChannelSensors(this.addr, direction, channel, lengthOut));
	}

	public SoapySDRArgInfo getSensorInfo(SoapySDRDeviceDirection direction, long channel, String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return SoapySDRArgInfo.fromStruct(Device_h.SoapySDRDevice_getChannelSensorInfo(arena, this.addr, direction.ordinal(), channel, arena.allocateFrom(key)));
	    }
	}

	public String readSensor(SoapySDRDeviceDirection direction, long channel, String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return NativeUtils.segmentToString(Device_h.SoapySDRDevice_readChannelSensor(this.addr, direction.ordinal(), channel, arena.allocateFrom(key)));
	    }
	}
	
	// Register API

	public List<String> listRegisterInterfaces() {
	    return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listRegisterInterfaces(this.addr, lengthOut));
	}

	public int writeRegister(String name, int addr, int value) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeRegister(this.addr, arena.allocateFrom(name), addr, value);
	    }
	}

	public int readRegister(String name, int addr) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_readRegister(this.addr, arena.allocateFrom(name), addr);
	    }
	}

	public int writeRegisters(String name, int addr, int[] values) {
	    try (Arena arena = Arena.ofConfined()) {
	    	MemorySegment data = arena.allocate(values.length * Integer.BYTES);
	    	for (int i = 0; i < values.length; i++) {
	    		data.setAtIndex(ValueLayout.JAVA_INT, i, values[i]);
	    	}
	    	
	        return Device_h.SoapySDRDevice_writeRegisters(this.addr, arena.allocateFrom(name), addr, data, values.length);
	    }
	}

	public int[] readRegisters(String name, int addr) {
    	try (var arena = Arena.ofConfined()) {
    		MemorySegment lengthOut = arena.allocate(ValueLayout.JAVA_LONG);
            MemorySegment resp = Device_h.SoapySDRDevice_readRegisters(this.addr, arena.allocateFrom(name), addr, lengthOut);
            
            int readCount = (int) lengthOut.get(JAVA_LONG, 0);
            if (readCount == 0) {
            	return new int[]{};
            }
            
            resp = resp.reinterpret(Integer.BYTES * readCount);
            return resp.toArray(ValueLayout.JAVA_INT);
    	}
	}
	
	// Settings API

	public List<SoapySDRArgInfo> getSettingInfo() {
	    return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getSettingInfo(this.addr, lengthOut), item -> SoapySDRArgInfo.fromStruct(item), SoapySDRArgInfo_h.layout());
	}

	public SoapySDRArgInfo getSettingInfo(String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return SoapySDRArgInfo.fromStruct(Device_h.SoapySDRDevice_getSettingInfoWithKey(arena, this.addr, arena.allocateFrom(key)));
	    }
	}

	public int writeSetting(String key, String value) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeSetting(this.addr, arena.allocateFrom(key), arena.allocateFrom(value));
	    }
	}

	public String readSetting(String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return NativeUtils.segmentToString(Device_h.SoapySDRDevice_readSetting(this.addr, arena.allocateFrom(key)));
	    }
	}

	public List<SoapySDRArgInfo> getSettingInfo(SoapySDRDeviceDirection direction, long channel) {
	    return NativeUtils.invokeGetStructs(lengthOut -> Device_h.SoapySDRDevice_getChannelSettingInfo(this.addr, direction.ordinal(), channel, lengthOut), item -> SoapySDRArgInfo.fromStruct(item), SoapySDRArgInfo_h.layout());
	}

	public SoapySDRArgInfo getSettingInfo(SoapySDRDeviceDirection direction, long channel, String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return SoapySDRArgInfo.fromStruct(Device_h.SoapySDRDevice_getChannelSettingInfoWithKey(arena, this.addr, direction.ordinal(), channel, arena.allocateFrom(key)));
	    }
	}

	public int writeSetting(SoapySDRDeviceDirection direction, long channel, String key, String value) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeChannelSetting(this.addr, direction.ordinal(), channel, arena.allocateFrom(key), arena.allocateFrom(value));
	    }
	}

	public String readSetting(SoapySDRDeviceDirection direction, long channel, String key) {
	    try (Arena arena = Arena.ofConfined()) {
	        return NativeUtils.segmentToString(Device_h.SoapySDRDevice_readChannelSetting(this.addr, direction.ordinal(), channel, arena.allocateFrom(key)));
	    }
	}
	
	// GPIO API

	public List<String> listGPIOBanks() {
	    return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listGPIOBanks(this.addr, lengthOut));
	}

	public int writeGPIO(String bank, int value) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeGPIO(this.addr, arena.allocateFrom(bank), value);
	    }
	}

	public int writeGPIO(String bank, int value, int mask) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeGPIOMasked(this.addr, arena.allocateFrom(bank), value, mask);
	    }
	}

	public int readGPIO(String bank) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_readGPIO(this.addr, arena.allocateFrom(bank));
	    }
	}

	public int writeGPIODir(String bank, int dir) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeGPIODir(this.addr, arena.allocateFrom(bank), dir);
	    }
	}

	public int writeGPIODir(String bank, int dir, int mask) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeGPIODirMasked(this.addr, arena.allocateFrom(bank), dir, mask);
	    }
	}

	public int readGPIODir(String bank) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_readGPIODir(this.addr, arena.allocateFrom(bank));
	    }
	}
	
	// I2C API

	public int writeI2C(int addr, byte[] data) {
    	MemorySegment buf = MemorySegment.ofArray(data);
        return Device_h.SoapySDRDevice_writeI2C(this.addr, addr, buf, data.length);
	}

	public byte[] readI2C(int addr) {
		try (Arena arena = Arena.ofConfined()) {
    		MemorySegment lengthOut = arena.allocate(ValueLayout.JAVA_LONG);
            MemorySegment resp = Device_h.SoapySDRDevice_readI2C(this.addr, addr, lengthOut);
            		
            int readCount = (int) lengthOut.get(JAVA_LONG, 0);
            if (readCount == 0) {
            	return new byte[]{};
            }
            
            resp = resp.reinterpret(readCount);
            return resp.toArray(ValueLayout.JAVA_BYTE);
		}
	}

	public int transactSPI(int addr, int data, int numBits) {
	    return Device_h.SoapySDRDevice_transactSPI(this.addr, addr, data, numBits);
	}

	public List<String> listUARTs() {
	    return NativeUtils.invokeGetStrArray(lengthOut -> (MemorySegment) Device_h.SoapySDRDevice_listUARTs(this.addr, lengthOut));
	}

	public int writeUART(String which, String data) {
	    try (Arena arena = Arena.ofConfined()) {
	        return Device_h.SoapySDRDevice_writeUART(this.addr, arena.allocateFrom(which), arena.allocateFrom(data));
	    }
	}

	public String readUART(String which, long timeoutUs) {
	    try (Arena arena = Arena.ofConfined()) {
	        return NativeUtils.segmentToString(Device_h.SoapySDRDevice_readUART(this.addr, arena.allocateFrom(which), timeoutUs));
	    }
	}

	public MemorySegment getNativeDeviceHandle() {
	    return Device_h.SoapySDRDevice_getNativeDeviceHandle(this.addr);
	}
	
	
}
