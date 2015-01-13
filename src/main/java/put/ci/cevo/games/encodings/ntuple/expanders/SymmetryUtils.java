package put.ci.cevo.games.encodings.ntuple.expanders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SymmetryUtils {
	private SymmetryUtils() {
		// Static
	}

	/**
	 * Returns a list of tuples (arrays of locations) expanded with a symmetry expander.
	 * The list always starts with the given tuple.
	 *
	 * @param locations
	 *            array of margin-based locations (like in boards with margins)
	 */
	public static List<int[]> createSymmetric(int[] locations, SymmetryExpander expander) {
		int n = expander.numSymmetries(); // number of symmetric tuples
		int m = locations.length;

		int[][] tuples = new int[n][m];

		// Create n tuples location by location
		for (int j = 0; j < m; ++j) {
			int[] symmetries = expander.getSymmetries(locations[j]);
			assert symmetries.length == n;
			for (int i = 0; i < n; ++i) {
				tuples[i][j] = symmetries[i];
			}
		}

		ArrayList<int[]> unique = new ArrayList<>(Arrays.asList(tuples));

		// Remove non unique (keep the first (main) tuple intact)
		for (int i = 0; i < unique.size(); ++i) {
			for (int j = unique.size() - 1; j > i; --j) {
				if (Arrays.equals(unique.get(i), unique.get(j))) {
					unique.remove(j);
				}
			}
		}
		assert Arrays.equals(unique.get(0), locations);
		return unique;
	}
}
