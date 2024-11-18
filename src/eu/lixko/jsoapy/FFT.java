package eu.lixko.jsoapy;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.FloatBuffer;
import java.util.HashMap;

import eu.lixko.jsoapy.fft.FFTW;
import eu.lixko.jsoapy.fft.Volk;

public class FFT implements AutoCloseable {
	
	public MemorySegment fft_in = null;
	public MemorySegment fft_out = null;
	public MemorySegment fft_powers = null;
	public MemorySegment fft_window = null;
	MemorySegment plan = null;
	int fftSize;
	int maxFftSize;
	HashMap<Integer, MemorySegment> plans = new HashMap<>();
	
	public FFT(int maxFftSize) {
		this.maxFftSize = maxFftSize;
		
		this.fft_in = FFTW.fftwf_malloc(this.getNativeBufferSize());
		this.fft_out = FFTW.fftwf_malloc(this.getNativeBufferSize());
		this.fft_powers = FFTW.fftwf_malloc(this.getNativeBufferSize());
		this.fft_window = FFTW.fftwf_malloc(this.getNativeBufferSize());
		
		// this.plan = FFTW.fftwf_plan_dft_1d(fftSize, fft_in, fft_out, FFTW.FFTW_FORWARD, FFTW.FFTW_ESTIMATE);
		
		this.setFftSize(maxFftSize);
		this.generateWindow();
	}
	
	public void setFftSize(int size) {
		if (size == this.fftSize && this.plan != null) {
			return;
		}
		
		this.fftSize = size;
		if (!plans.containsKey(size)) {
			this.plans.put(size, FFTW.fftwf_plan_dft_1d(fftSize, fft_in, fft_out, FFTW.FFTW_FORWARD, FFTW.FFTW_ESTIMATE));
		}
		this.plan = plans.get(size);
		this.generateWindow();
	}
	
	public void fetchPowers(FloatBuffer pwr) {
		pwr.rewind();
		for (long i = 0; i < fftSize; i++) {
			float real = this.fft_out.get(ValueLayout.JAVA_FLOAT, i * 2);
			float imag = this.fft_out.get(ValueLayout.JAVA_FLOAT, i * 2 + 1);
			float ampl = real*real + imag*imag;
			pwr.put(ampl);
		}
	}
	
	public MemorySegment getBufferIn() {
		return this.fft_in;
	}
	
	public MemorySegment getBufferOut() {
		return this.fft_out;
	}
	
	public void execute() {
		Volk.volk_32fc_32f_multiply_32fc(fft_in, fft_in, fft_window, fftSize);
		FFTW.fftwf_execute(this.plan);
		Volk.volk_32fc_s32f_power_spectrum_32f(this.fft_out, this.fft_out, (float) fftSize, (int) fftSize);
	}

	@Override
	public void close() throws Exception {
		FFTW.fftwf_destroy_plan(plan);
		FFTW.fftwf_free(fft_in);
		FFTW.fftwf_free(fft_out);
		FFTW.fftwf_free(fft_powers);
	}
	
	public long getSize() {
		return this.fftSize;
	}
	
	private long getNativeBufferSize() {
		return Float.BYTES * 2 * this.maxFftSize;
	}
	
	public void generateWindow() {
		// float coefs[] = { 0.355768f, 0.487396f, 0.144232f, 0.012604f };	// nutall
		float coefs[] = { 0.426590713672f, 0.496560619089f, 0.0768486672399f }; // blackmann
		for (int i = 0; i < fftSize; i++) {
			fft_window.setAtIndex(ValueLayout.JAVA_FLOAT, i, cosine(i, (int) fftSize, coefs));
			// fft_window.setAtIndex(ValueLayout.JAVA_FLOAT, i, (float) (0.54 - 0.46*Math.cos(2 * Math.PI * i / fftSize)));
		}
	}
	
	public float cosine(int n, int N, float[] coefs) {
		float win = 0.0f;
		float sign = 1.0f;
        for (int i = 0; i < coefs.length; i++) {
            win += sign * coefs[i] * Math.cos((float) i * 2.0 * Math.PI * n / N);
            sign = -sign;
        }
        return win;
	}
}
