package put.ci.cevo.games.encodings.ntuple;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.games.board.Board;
import put.ci.cevo.util.RandomUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

public class NTuple {

	private final int numValues;
	private final int[] locations;
	private final double[] LUT;

	/**
	 * Watch out: Locations are margin-based (not 0-based)
	 */
	public NTuple(int numValues, int[] locations, double[] weights) {
		Preconditions.checkArgument(locations.length > 0);
		Preconditions.checkArgument(weights.length == computeNumWeights(numValues, locations.length));
		// I do not copy this arrays, because some of them may be shared among NTuples (symmetry expander)!
		this.numValues = numValues;
		this.locations = locations;
		this.LUT = weights;
	}

	public static NTuple newWithRandomWeights(int numValues, int[] locations, double minWeight, double maxWeight,
			RandomDataGenerator random) {
		Preconditions.checkArgument(locations.length > 0);
		double[] weights = new double[NTuple.computeNumWeights(numValues, locations.length)];

		weights = RandomUtils.randomDoubleVector(weights.length, minWeight, maxWeight, random);

		return new NTuple(numValues, locations, weights);
	}

	/**
	 * Copy constructor
	 */
	public NTuple(NTuple template) {
		this(template.numValues, template.locations.clone(), template.LUT.clone());
	}

	public double valueFor(Board board) {
		return LUT[address(board)];
	}

	public int address(Board board) {
		int address = 0;
		for (int location : locations) {
			address *= numValues;
			// Here we assume that board values are in [-1, numValues-2] range
			address += board.getValue(location); // HACKED. Above no longer true
		}
		return address;
	}

	/**
	 * @param address
	 * @return actual values e.g. [-1, 0, 1] for a given address (5). Could be static in principle
	 */
	public int[] valuesFromAddress(int address) {
		int[] val = new int[getSize()];
		for (int i = getSize() - 1; i >= 0; --i) {
			val[i] = address % numValues - 1;
			address = address / numValues;
		}
		throw new RuntimeException();// HACK
		// return val;
	}

	/**
	 * weights are not cloned, because I use this method for sharing weights among ntuples. Right, this could be done
	 * better (TODO)
	 */
	public double[] getWeights() {
		return LUT;
	}

	public int getNumWeights() {
		return LUT.length;
	}

	public int[] getLocations() {
		return locations.clone();
	}

	public int getSize() {
		return locations.length;
	}

	public int getNumValues() {
		return numValues;
	}

	static int computeNumWeights(int numValues, int numFields) {
		return (int) (Math.pow(numValues, numFields) + 0.5);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(locations).append(LUT).append(numValues).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NTuple other = (NTuple) obj;
		return new EqualsBuilder().append(locations, other.locations).append(LUT, other.LUT)
			.append(numValues, other.numValues).isEquals();
	}

	@Override
	public String toString() {
		return "loc=[" + Joiner.on(",").join(ArrayUtils.toObject(locations)) + "]";
	}

	public String toStringDetailed() {
		String s = "loc=[" + Joiner.on(",").join(ArrayUtils.toObject(locations)) + "]\n";
		for (int i = 0; i < getNumWeights(); ++i) {
			int[] values = valuesFromAddress(i);
			s += " " + Joiner.on(",").join(ArrayUtils.toObject(values)) + ":\t" + String.format("%5.1f", LUT[i]) + "\n";
		}
		return s;
	}
}
