package eu.lixko.jsoapy.soapy;

public class Time_h {
	
	// Might as well reimplement SoapySDR/lib/TimeC.cpp in Java.

    static long llround(double x)  {
        return (long)((x < (0.0)) ? (x - (0.5)) : (x + (0.5)));
    }
    
    public static long SoapySDR_timeNsToTicks(long timeNs, double rate) {
        long ratell = (long)(rate);
        long full = (long)(timeNs/1000000000);
        long err = timeNs - (full*1000000000);
        double part = full*(rate - ratell);
        double frac = part + ((err*rate)/1000000000);
        return (full*ratell) + llround(frac);
    }
    
    public static long SoapySDR_ticksToTimeNs(long ticks, double rate) {
        long ratell = (long)(rate);
        long full = (long)(ticks/ratell);
        long err = ticks - (full*ratell);
        double part = full*(rate - ratell);
        double frac = ((err - part)*1000000000)/rate;
        return (full*1000000000) + llround(frac);
    }
}

