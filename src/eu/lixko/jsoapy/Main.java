package eu.lixko.jsoapy;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.HashMap;
import java.util.Map;

import eu.lixko.jsoapy.soapy.Constants_h;
import eu.lixko.jsoapy.soapy.Device_h;
import eu.lixko.jsoapy.soapy.Logger_h;
import eu.lixko.jsoapy.soapy.Modules_h;
import eu.lixko.jsoapy.soapy.SoapySDRKwargs;
import eu.lixko.jsoapy.soapy.SoapySDRDevice;
import eu.lixko.jsoapy.soapy.SoapySDRDeviceDirection;
import eu.lixko.jsoapy.soapy.SoapySDRDeviceDirection.*;
import eu.lixko.jsoapy.util.NativeUtils;
import eu.lixko.jsoapy.soapy.SoapySDRKwargs_h;
import eu.lixko.jsoapy.soapy.SoapySDRLogLevel;
import eu.lixko.jsoapy.soapy.Types_h;
import eu.lixko.jsoapy.soapy.Version_h;

public class Main {

	public static void main(String[] args) {
		NativeUtils.loadLibrary();
		
		System.out.println(Version_h.SoapySDR_getAPIVersion());
		System.out.println(Version_h.minVersionMatches("0.8-1"));
		/*
		Logger_h.SoapySDR_log(SoapySDRLogLevel.SOAPY_SDR_NOTICE, "Helloooo");
		
		Logger_h.SoapySDR_registerLogHandler((logLevel, message) -> {
			System.out.println(logLevel + ": " + message);
		});
		
		Logger_h.SoapySDR_log(SoapySDRLogLevel.SOAPY_SDR_NOTICE, "Helloooo from the other side");
		
		HashMap<String, String> map = new HashMap<>();
		map.put("driver", "rtlsdr");
		map.put("motorku", "pidaras");
		map.put("driver", "m cyka iri");
		map.put("nigersaurus", "0");
		try (Arena arena = Arena.ofConfined()) {
			SoapySDRKwargs obj = SoapySDRKwargs.makeStruct(arena, map);
			var kwargs = obj.getStruct();
			String serialized = NativeUtils.segmentToString(Types_h.SoapySDRKwargs_toString(kwargs));
			System.out.println(serialized);
			String str = NativeUtils.segmentToString(Types_h.SoapySDRKwargs_get(kwargs, arena.allocateFrom("driver")));
			System.out.println(str);
			
			var outMap = SoapySDRKwargs.toMap(kwargs);
		   	// var outMap = Modules_h.SoapySDR_getLoaderResult(arena, "/usr/lib/SoapySDR/modules0.8/librtltcpSupport.so").toMap();
		   	for (Map.Entry<String, String> entry : outMap.entrySet()) {
		   		System.err.println("outMap: " + entry.getKey() + " : " + obj.get(entry.getKey()));
	    	}
		   	
		   	obj.close();
		   	
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		for (String path : Modules_h.SoapySDR_listModules()) {
			System.out.println(path);
		}*/
		
		try (Arena arena = Arena.ofConfined()) {
			var devArgs = SoapySDRKwargs.fromString(arena, "");
			for (var dev : SoapySDRDevice.enumerate(devArgs)) {
				System.out.println("=============================");
				for (Map.Entry<String, String> entry : dev.toMap().entrySet()) {
			   		System.out.println("outMap: " + entry.getKey() + " : " + entry.getValue());
		    	}
				// System.out.println(path);
			}
		}
		SoapySDRDevice dev = SoapySDRDevice.makeStrArgs("driver=rtlsdr");
		System.out.println(dev.listGains(SoapySDRDeviceDirection.RX, 0));
		
		/* try (Arena arena = Arena.ofConfined()) {
			var lengthOut = arena.allocate(ValueLayout.JAVA_LONG);
			 
			var firstPtr = Device_h.SoapySDRDevice_getSampleRateRange(dev.getAddress(), Constants_h.SOAPY_SDR_RX, 0, lengthOut);
			firstPtr = firstPtr.reinterpret(512);
			long length = lengthOut.get(ValueLayout.JAVA_LONG, 0);
			System.out.println(length);
			for (int i = 0; i < 6; i++) {
				System.out.println(firstPtr.get(ValueLayout.JAVA_DOUBLE, i * ValueLayout.JAVA_DOUBLE.byteSize()));
			}
		} */
		// System.exit(0);
		System.out.println(dev.getFrequencyRange(SoapySDRDeviceDirection.RX, 0));
		System.out.println(dev.getSampleRateRange(SoapySDRDeviceDirection.RX, 0));
		System.out.println(dev.getGainRange(SoapySDRDeviceDirection.RX, 0, "TUNER"));

	}

}
