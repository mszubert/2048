package put.ci.cevo.games.encodings.ntuple.eval;

import put.ci.cevo.games.board.Board;
import put.ci.cevo.games.encodings.ntuple.NTuple;
import put.ci.cevo.games.encodings.ntuple.NTuples;

public class DefaultNTupleEvaluator implements NTupleBoardEvaluator {

	@Override
	public double evaluate(NTuples tuples, Board board) {
		double result = 0;
		for (NTuple tuple : tuples.getAll()) {
			result += tuple.valueFor(board);
		}
		return result;
	}

}
