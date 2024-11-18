package eu.lixko.jsoapy;
import jdk.incubator.vector.Vector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorOperators.Unary;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class VectorTest {
	public static void main(String[] args) {
		
		final VectorSpecies<Float> species = FloatVector.SPECIES_PREFERRED;
		System.out.println(species);
		
		float[] inArr = new float[128];
		float[] outArr = new float[inArr.length];
		
		for (int i = 0; i < inArr.length; i++) {
			inArr[i] = (float) -i;
			// System.out.println("# " + i + " : " + inArr[i]);
		}
		
		
		for (int i = 0; i < inArr.length; i += species.length() * 1) {
			float max = FloatVector.fromArray(species, inArr, i).reduceLanes(VectorOperators.MAX);
		}
		
		var mask = species.indexInRange(3, 8);
		System.out.println(mask);
		var vi = FloatVector.fromArray(species, inArr, 8);
		System.out.println("vi: " + vi);
		var vr = FloatVector.fromArray(species, inArr, 0, mask);
		System.out.println("vr: " + vr);
		
		System.out.println("vi.max(vr): " + vi.max(vr));
		System.out.println("vi.lanewise(VectorOperators.MAX, vr, mask): " + vi.lanewise(VectorOperators.MAX, vr, mask));
		;
		System.out.println("vr.max(vi): " + vr.max(vi));
		System.out.println("vr.lanewise(VectorOperators.MAX, vi, mask): " + vr.lanewise(VectorOperators.MAX, vi, mask));
		// .reduceLanes(VectorOperators.MAX)
		
		
		
	}
}
