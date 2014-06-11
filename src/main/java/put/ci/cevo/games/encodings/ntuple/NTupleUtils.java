package put.ci.cevo.games.encodings.ntuple;

import static put.ci.cevo.util.ArrayUtils.sorted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import put.ci.cevo.games.encodings.ntuple.expanders.SymmetryExpander;

import com.carrotsearch.hppc.IntArrayList;

public class NTupleUtils {

	/**
	 * Returns a list of tuples expanded by symmetry expander. All the returned tuples share weights with the given
	 * template. The first returned tuple is equal to template (but is a new object)
	 */
	public static List<NTuple> createSymmetric(NTuple template, SymmetryExpander expander) {
		List<int[]> symmetric = createSymmetric(template.getLocations(), expander);

		List<NTuple> tuples = new ArrayList<>(symmetric.size());
		for (int[] locations : symmetric) {
			// Watch out: weights are shared among symmetric tuples
			tuples.add(new NTuple(template.getNumValues(), locations, template.getWeights()));
		}
		assert tuples.get(0).equals(template);
		return tuples;
	}

	/**
	 * Returns a list of unique tuples expanded with symmetry expander. The list always starts with the given tuple.
	 *
	 * Tuples a and b are unique if sorted(a) != sorted(b)
	 *
	 * @param tuple
	 *            array of locations
	 */
	// TODO: Make it package private
	public static List<int[]> createSymmetric(int[] tuple, SymmetryExpander expander) {
		int n = expander.numSymmetries(); // number of symmetric tuples
		int m = tuple.length;

		int[][] tuples = new int[n][m];

		// Create n tuples location by location
		for (int j = 0; j < m; ++j) {
			int[] symmetries = expander.getSymmetries(tuple[j]);
			assert symmetries.length == n;
			for (int i = 0; i < n; ++i) {
				tuples[i][j] = symmetries[i];
			}
		}

		List<int[]> unique = new ArrayList<int[]>(Arrays.asList(tuples));

		// Remove non unique (keep the first (main) tuple intact)
		for (int i = 0; i < unique.size(); ++i) {
			for (int j = unique.size() - 1; j > i; --j) {
				if (Arrays.equals(sorted(unique.get(i)), sorted(unique.get(j)))) {
					unique.remove(j);
				}
			}
		}
		assert Arrays.equals(unique.get(0), tuple);
		return unique;
	}

	/**
	 * List of tuples that contain given board position for every board position.
	 * 
	 * @param numBoardPositions
	 *            the number of margin-based board positions (all positions (also with margin position))
	 */
	public static IntArrayList[] getTuplesForPositions(NTuples tuples, int numBoardPositions) {
		IntArrayList[] tuplesForPosition = new IntArrayList[numBoardPositions];
		for (int i = 0; i < numBoardPositions; ++i) {
			tuplesForPosition[i] = new IntArrayList();
		}
		for (int idx = 0; idx < tuples.size(); ++idx) {
			NTuple tuple = tuples.getTuple(idx);
			for (int pos : tuple.getLocations()) {
				tuplesForPosition[pos].add(idx);
			}
		}
		return tuplesForPosition;
	}

}
