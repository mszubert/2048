package put.ci.cevo.games.encodings.ntuple.factories;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.games.board.BoardPos;
import put.ci.cevo.games.board.BoardPosList;
import put.ci.cevo.games.board.RectSize;
import put.ci.cevo.games.encodings.ntuple.NTuples;
import put.ci.cevo.games.encodings.ntuple.expanders.SymmetryExpander;

public class NTuplesAllStraightFactory {

	private final NTuplesGenericFactory genericFactory;

	/** All 1-D horizontal and vertical tuples */
	public NTuplesAllStraightFactory(int tupleLength, RectSize boardSize, int numValues, double minWeight,
			double maxWeight, SymmetryExpander expander) {
		BoardPos[][] positions = new BoardPos[4][tupleLength];
		for (int i = 0; i < tupleLength; ++i) {
			positions[0][i] = new BoardPos(i, 0);
			positions[1][i] = new BoardPos(0, i);
		}
		BoardPosList[] list = new BoardPosList[] { new BoardPosList(positions[0]), new BoardPosList(positions[1]) };
		genericFactory = new NTuplesGenericFactory(list, boardSize, numValues, minWeight, maxWeight, expander);
	}

	public NTuples createRandomIndividual(RandomDataGenerator random) {
		return genericFactory.createRandomIndividual(random);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
