package put.ci.cevo.games.encodings.ntuple;

import static put.ci.cevo.util.RandomUtils.nextInt;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.games.board.Board;
import put.ci.cevo.games.board.BoardUtils;
import put.ci.cevo.util.RandomFactory;

import com.carrotsearch.hppc.IntOpenHashSet;

public class NTupleRandomFactory implements RandomFactory<NTuple> {

	private final int numValues;
	private final int tupleSize;

	private final double minWeight;
	private final double maxWeight;

	private final int boardWidth;
	private final int boardHeight;

	public NTupleRandomFactory(int numValues, int tupleSize, int boardSize, double minWeight, double maxWeight) {
		this(numValues, tupleSize, boardSize, boardSize, minWeight, maxWeight);
	}

	public NTupleRandomFactory(int numValues, int tupleSize, int boardWidth, int boardHeight, double minWeight,
			double maxWeight) {
		this.numValues = numValues;
		this.tupleSize = tupleSize;
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
	}

	@Override
	public NTuple create(RandomDataGenerator random) {
		IntOpenHashSet positionSet = new IntOpenHashSet();
		int row = nextInt(0, boardHeight - 1, random);
		int col = nextInt(0, boardWidth - 1, random);
		positionSet.add(BoardUtils.toPos(boardWidth, row, col));
		for (int j = 0; j < tupleSize - 1; j++) {
			int dir = random.nextInt(0, Board.DIRS.length - 1);
			row += Board.DIRS[dir][0];
			col += Board.DIRS[dir][1];

			row = (row + boardHeight) % boardHeight; // TODO: This makes no sense, because it harms locality.
			col = (col + boardWidth) % boardHeight; // It is better to bounce the seed

			positionSet.add(BoardUtils.toPos(boardWidth, row, col)); // This way, duplicates are ignored (but,
																	 // how many
			// of
			// them are ignored? TODO)
			// TODO: We assume that this is OthelloBoard and positions are margin-based. This should be resolved somehow
		}
		return NTuple.newWithRandomWeights(numValues, positionSet.toArray(), minWeight, maxWeight, random);
	}
}
