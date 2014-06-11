package put.ci.cevo.games.encodings.ntuple.expanders;

public interface SymmetryExpander {

	/**
	 * Returns an array of locations expanded by the inherent symmetry of the board. The expanded locations can (and
	 * should sometimes, e.g. for the middle locations in Othello). Have to always return the same number of symmetries.
	 *
	 * @return the first element has to be the original location.
	 **/
	public int[] getSymmetries(int location);

	/**
	 * Number of symmetric expansions for the given symmetry expander. >0
	 */
	public int numSymmetries();
}
