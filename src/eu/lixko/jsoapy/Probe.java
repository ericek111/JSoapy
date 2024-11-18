package eu.lixko.jsoapy;

import java.lang.foreign.Arena;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import eu.lixko.jsoapy.soapy.SoapySDRArgInfo;
import eu.lixko.jsoapy.soapy.SoapySDRDevice;
import eu.lixko.jsoapy.soapy.SoapySDRDevice.NativeStreamFormat;
import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.soapy.SoapySDRDeviceDirection;
import eu.lixko.jsoapy.soapy.SoapySDRKwargs;
import eu.lixko.jsoapy.soapy.SoapySDRRange;

public class Probe {
	Arena arena = Arena.ofAuto();
	
	public static void main(String[] args) {
		NativeUtils.loadLibrary();

		var obj = new Probe();
		obj.run();
	}
	
	private void run() {
		probeDevice("");
		
	}
	
	private int probeDevice(String argStr) {
		log("Probe device " + argStr);
		try {
			SoapySDRDevice device = SoapySDRDevice.makeStrArgs(argStr);
			printDev(device);
		} catch (Exception ex) {
			System.err.println("Error probing device: " + ex.getMessage());
			return 1;
		}
		
		return 0;
	}
	
	private void printDev(SoapySDRDevice dev) {
		printHeader("Device identification");
		log("driver=" + dev.getDriverKey(), 1);
		log("hardware=" + dev.getHardwareKey(), 1);
		for (Map.Entry<String, String> entry : dev.getHardwareInfo().toMap().entrySet()) {
			log(entry.getKey() + "=" + entry.getValue(), 1);
    	}
		
		printHeader("Peripheral summary");
		long numRxChans = dev.getNumChannels(SoapySDRDeviceDirection.RX);
		long numTxChans = dev.getNumChannels(SoapySDRDeviceDirection.TX);
		
		log("Channels: %d Rx, %d Tx".formatted(numRxChans, numTxChans), 1);
		log("Timestamps: " + (dev.hasHardwareTime("") ? "YES" : "NO"), 1);
		
		printList("Clock sources", dev.listClockSources());
		printList("Time sources", dev.listTimeSources());
		printList("Sensors", dev.listSensors());
		printList("Registers", dev.listRegisterInterfaces());
		List<SoapySDRArgInfo> settings = dev.getSettingInfo();
		if (!settings.isEmpty()) {
			log("Other Settings:", 1);
			for (SoapySDRArgInfo argInfo : settings) {
				printArgInfo(argInfo);				
			}
		}
		
		printList("GPIOs", dev.listGPIOBanks());
		printList("UARTs", dev.listUARTs());
		
		for (long i = 0; i < numRxChans; i++) {
			probeChannel(dev, SoapySDRDeviceDirection.RX, i);
		}
		
		for (long i = 0; i < numTxChans; i++) {
			probeChannel(dev, SoapySDRDeviceDirection.TX, i);
		}
		
	}
	
	private void probeChannel(SoapySDRDevice dev, SoapySDRDeviceDirection dir, long chan) {
		printHeader(dir.toString() + " Channel " + chan);
		
		Map<String, String> channelInfo = dev.getChannelInfo(dir, chan).toMap();
		if (!channelInfo.isEmpty()) {
			for (Map.Entry<String, String> entry : channelInfo.entrySet()) {
				log(entry.getKey() + "=" + entry.getValue(), 2);
			}
		}
		
		log("Full-duplex: " + (dev.getFullDuplex(dir, chan) ? "YES" : "NO"), 1);
		log("Supports AGC: " + (dev.hasGainMode(dir, chan) ? "YES" : "NO"), 1);
		
		printList("Stream formats", dev.getStreamFormats(dir, chan).stream().map(format -> format.name()).collect(Collectors.toList()));
		NativeStreamFormat nativeFormat = dev.getNativeStreamFromat(dir, chan);
		log("Native format: " + nativeFormat.format() + " [full-scale=" + nativeFormat.fullScale() + "]", 1);
		
		List<SoapySDRArgInfo> streamArgs = dev.getStreamArgsInfo(dir, chan);
		if (!streamArgs.isEmpty()) {
			log("Stream args:", 1);
			for (var argInfo : streamArgs) {
				printArgInfo(argInfo);				
			}
		}
		
		printList("Antennas", dev.listAntennas(dir, chan));
		
		List<String> correctionsList = new ArrayList<>();
		if (dev.hasDCOffsetMode(dir, chan)) correctionsList.add("DC removal");
		if (dev.hasDCOffset(dir, chan)) correctionsList.add("DC offset");
		if (dev.hasIQBalance(dir, chan)) correctionsList.add("IQ balance");
		printList("Corrections", correctionsList);
		
		log("Full gain range: " + dev.getGainRange(dir, chan).toString() + " dB", 1);
		List<String> gainsList = dev.listGains(dir, chan);
		for (String name : gainsList) {
			log(name + " gain range: " + dev.getGainRange(dir, chan, name).toString() + " dB", 2);
		}
		
		log("Full freq range: " + SoapySDRRange.rangeListToString(dev.getFrequencyRange(dir, chan), 1e6) + " dB", 1);
		List<String> freqsList = dev.listFrequencies(dir, chan);
		for (String name : freqsList) {
			log(name + " freq range: " + SoapySDRRange.rangeListToString(dev.getFrequencyRange(dir, chan, name), 1e6) + " MHz", 2);
		}
		
		List<SoapySDRArgInfo> freqArgs = dev.getFrequencyArgsInfo(dir, chan, "");
		if (!freqArgs.isEmpty()) {
			log("Tune args:", 1);
			for (var argInfo : freqArgs) {
				printArgInfo(argInfo);				
			}
		}
		
		log("Sample rates: " + SoapySDRRange.rangeListToString(dev.getSampleRateRange(dir, chan), 1e6) + " MSps", 1);
		
		log("Filter bandwidths: " + SoapySDRRange.rangeListToString(dev.getBandwidthRange(dir, chan), 1e6) + " MHz", 1);
		
		printList("Sensors", dev.listSensors());
		
		List<SoapySDRArgInfo> settings = dev.getSettingInfo(dir, chan);
		if (!settings.isEmpty()) {
			log("Other Settings:", 1);
			for (SoapySDRArgInfo argInfo : settings) {
				printArgInfo(argInfo);				
			}
		}
	}
	
	private void printArgInfo(SoapySDRArgInfo argInfo) {
		String name = argInfo.name.isEmpty() ? argInfo.key : argInfo.name;
		String label = " * " + name; 
		
		if (!argInfo.description.isEmpty()) {
			label += " - " + argInfo.description;
		}
		
		log(label, 2);
		
		log(" " + argInfo.toString(), 3);
		
	}
	
	private void printList(String name, List<String> list) {
		if (!list.isEmpty()) {
			log(name + ": " + String.join(", ", list), 1);
		}
	}
	

	
	private void printHeader(String title) {
		log("");
		log("----------------------------------------------------");
		log("-- " + title);
		log("----------------------------------------------------");
	}
	
	private void log(String str) {
		System.out.println(str);
	}
	
	private void log(String str, int level) {
		log("  ".repeat(level) + str);
	}
}
