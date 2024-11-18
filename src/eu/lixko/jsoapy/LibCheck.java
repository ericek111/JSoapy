package eu.lixko.jsoapy;

import java.lang.foreign.MemorySegment;

import eu.lixko.jsoapy.fft.FFTW;
import eu.lixko.jsoapy.fft.Volk;

public class LibCheck {
	public static void main(String[] args) {
		System.out.println(Volk.volk_32fc_32f_multiply_32fc.ADDR.address());
		MemorySegment buf = FFTW.fftwf_malloc(128);
		
		System.out.println(buf);
		FFTW.fftwf_free(buf);
	}
}
