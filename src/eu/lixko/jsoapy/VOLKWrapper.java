package eu.lixko.jsoapy;

import java.lang.foreign.Arena;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.FloatBuffer;

import eu.lixko.jsoapy.fft.Volk;

public class VOLKWrapper {
    private static final MemoryLayout floatArrayLayout = MemoryLayout.sequenceLayout(1, ValueLayout.JAVA_FLOAT);
    
    public static void volk32fcS32fPowerSpectrum32f(FloatBuffer result, FloatBuffer input, int numPoints, float scale) throws Throwable {
        try (Arena session = Arena.ofConfined()) {
            MemorySegment resultSegment = session.allocate(floatArrayLayout, numPoints * 4);
            MemorySegment inputSegment = session.allocate(floatArrayLayout, numPoints * 4);

            // Copy data to the native memory segments
            for (int i = 0; i < numPoints; i++) {
                resultSegment.setAtIndex(ValueLayout.JAVA_FLOAT, i, result.get(i));
                inputSegment.setAtIndex(ValueLayout.JAVA_FLOAT, i, input.get(i));
            }

            // Call the native method
            Volk.volk_32fc_s32f_power_spectrum_32f.HANDLE.invoke(resultSegment, inputSegment, scale, numPoints);

            // Copy the result back to the Java buffer
            for (int i = 0; i < numPoints; i++) {
                result.put(i, resultSegment.getAtIndex(ValueLayout.JAVA_FLOAT, i));
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        int numPoints = 1024; // Example size
        float scale = 1.0f; // Example scale

        // Create example buffers
        FloatBuffer input = FloatBuffer.allocate(numPoints * 2); // Complex input (interleaved real and imaginary)
        FloatBuffer result = FloatBuffer.allocate(numPoints);

        // Fill the input with example data
        for (int i = 0; i < numPoints * 2; i++) {
            input.put(i, (float) Math.random());
        }
        
        try (Arena arena = Arena.ofConfined()) {
        	MemorySegment ints = arena.allocate(4);
        	ints.set(ValueLayout.JAVA_BYTE, 0, (byte) 0x01);
        	ints.set(ValueLayout.JAVA_BYTE, 1, (byte) 0x23);
        	ints.set(ValueLayout.JAVA_BYTE, 2, (byte) 0x45);
        	ints.set(ValueLayout.JAVA_BYTE, 3, (byte) 0x67);
        	System.out.println(ints.get(ValueLayout.JAVA_BYTE, 0) + ", " + ints.get(ValueLayout.JAVA_BYTE, 1) + ", " + ints.get(ValueLayout.JAVA_BYTE, 2) + ", " + ints.get(ValueLayout.JAVA_BYTE, 3));
        	Volk.volk_16u_byteswap(ints, 2);
        	System.out.println(ints.get(ValueLayout.JAVA_BYTE, 0) + ", " + ints.get(ValueLayout.JAVA_BYTE, 1) + ", " + ints.get(ValueLayout.JAVA_BYTE, 2) + ", " + ints.get(ValueLayout.JAVA_BYTE, 3));
        	System.exit(0);
        }

        // Call the VOLK function
        volk32fcS32fPowerSpectrum32f(result, input, numPoints, scale);

        // Print the result
        for (int i = 0; i < numPoints; i++) {
            System.out.println("Result[" + i + "] = " + result.get(i));
        }
    }
}
