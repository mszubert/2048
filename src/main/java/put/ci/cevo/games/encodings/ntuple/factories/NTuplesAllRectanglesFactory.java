package put.ci.cevo.games.encodings.ntuple.factories;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.games.board.BoardPos;
import put.ci.cevo.games.board.BoardPosList;
import put.ci.cevo.games.board.RectSize;
import put.ci.cevo.games.encodings.ntuple.NTuples;
import put.ci.cevo.games.encodings.ntuple.expanders.SymmetryExpander;

/**
 * Generates all rect-like n-tuples of given sizes (watchout: it generates rectangles CxR and RxC)
 */
public class NTuplesAllRectanglesFactory {

	private final NTuplesGenericFactory genericFactory;

	public NTuplesAllRectanglesFactory(RectSize rectSize, RectSize boardSize, int numValues, double minWeight,
			double maxWeight, SymmetryExpander expander) {
		ArrayList<BoardPos> positions1 = new ArrayList<>();
		ArrayList<BoardPos> positions2 = new ArrayList<>();
		for (int r = 0; r < rectSize.rows(); ++r) {
			for (int c = 0; c < rectSize.columns(); ++c) {
				positions1.add(new BoardPos(r, c));
				positions2.add(new BoardPos(c, r));
			}
		}
		BoardPosList[] list = new BoardPosList[] { new BoardPosList(positions1), new BoardPosList(positions2) };

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