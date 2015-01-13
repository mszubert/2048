package put.ci.cevo.games.encodings.ntuple.factories;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.games.board.BoardPosList;
import put.ci.cevo.games.board.RectSize;
import put.ci.cevo.games.encodings.ntuple.NTuples;
import put.ci.cevo.games.encodings.ntuple.NTuplesBuilder;
import put.ci.cevo.games.encodings.ntuple.expanders.SymmetryExpander;

/**
 * Generates all square n-tuples of given side size
 */
public class NTuplesGenericFactory {

	private final double maxWeight;
	private final double minWeight;
	private final RectSize boardSize;
	private final int numValues;
	private final SymmetryExpander expander;
	private final BoardPosList[] positionsList;

	public NTuplesGenericFactory(BoardPosList[] positionsList, RectSize boardSize, int numValues,
			double minWeight, double maxWeight, SymmetryExpander expander) {
		Preconditions.checkArgument(positionsList.length > 0);
		this.positionsList = positionsList;
		this.expander = expander;
		this.boardSize = boardSize;
		this.numValues = numValues;
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
	}

	public NTuplesGenericFactory(BoardPosList positions, RectSize boardSize, int numValues,
			double minWeight, double maxWeight, SymmetryExpander expander) {
		this(new BoardPosList[] { positions }, boardSize, numValues, minWeight, maxWeight, expander);
	}

	public NTuples createRandomIndividual(RandomDataGenerator random) {
		NTuplesBuilder builder = new NTuplesBuilder(numValues, minWeight, maxWeight, expander, random, true);

		// Most of them are redundant, but it is general. Its performance could be improved, but this does not seem as a
		// bottleneck
		for (BoardPosList positions : positionsList)
			for (int r = 0; r < boardSize.rows(); ++r)
				for (int c = 0; c < boardSize.columns(); ++c) {
					BoardPosList nextPositions = positions.getAligned().getShifted(r, c);
					if (nextPositions.fitOnBoard(boardSize)) {
						builder.addTuple(nextPositions.toLocations(boardSize));
					}
				}
		return builder.buildNTuples();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}