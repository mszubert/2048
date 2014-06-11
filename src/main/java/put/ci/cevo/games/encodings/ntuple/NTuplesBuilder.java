package put.ci.cevo.games.encodings.ntuple;

import static put.ci.cevo.util.ArrayUtils.sorted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.games.encodings.ntuple.expanders.SymmetryExpander;

import com.google.common.primitives.Ints;

/**
 * Builds NTuple with random weights. Excludes any tuple duplicates and any "subtuples"
 * 
 * @author Wojciech Jaśkowski
 */
public class NTuplesBuilder {

	private final RandomDataGenerator random;

	private final List<int[]> all = new ArrayList<int[]>();
	private final List<NTuple> main = new ArrayList<NTuple>();

	private final SymmetryExpander expander;

	private final int numValues;

	private final double minWeight;
	private final double maxWeight;

	private final boolean removeSubtuples;

	public NTuplesBuilder(int numValues, double minWeight, double maxWeight, SymmetryExpander expander,
			RandomDataGenerator random, boolean removeSubtuples) {
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		this.numValues = numValues;
		this.expander = expander;
		this.random = random;
		this.removeSubtuples = removeSubtuples;
	}

	public void addTuple(int[] locations) {
		// All contains all tuples added to the builder along with all their symmetry expansions

		// We don't want duplicates
		for (int[] tuple : all) {
			if (Arrays.equals(sorted(tuple), sorted(locations))) {
				return;
			}
		}

		if (removeSubtuples) {
			throw new RuntimeException("Not implemeneted");
			// Code inspection revealed that this will not work correctly, since we sometimes remove subtuples from all
			// (all.remove(my)), but we never remove from main!

//			for (IntArrayList my : all) {
//				if (locations.size() != my.size()) {
//					TreeSet<Integer> myset = new TreeSet<Integer>(asList(sorted(my.toArray())));
//					TreeSet<Integer> arrset = new TreeSet<Integer>(asList(sorted(locations.toArray())));
//					if (myset.containsAll(arrset)) {
//						return;
//					}
//					if (arrset.containsAll(myset)) {
//						all.remove(my);
//					}
//				}
//			}
		}

		all.addAll(NTupleUtils.createSymmetric(locations, expander));

		main.add(NTuple.newWithRandomWeights(numValues, locations, minWeight, maxWeight, random));
	}

	public NTuples buildNTuples() {
		ArrayList<NTuple> mainSorted = new ArrayList<>(main);

		// Sorting is not obligatory, but does not harm and eases some tests
		Collections.sort(mainSorted, new Comparator<NTuple>() {
			@Override
			public int compare(NTuple ntuple1, NTuple ntuple2) {
				return Ints.lexicographicalComparator().compare(ntuple1.getLocations(), ntuple2.getLocations());
			}
		});

		return new NTuples(mainSorted, expander);
	}
}