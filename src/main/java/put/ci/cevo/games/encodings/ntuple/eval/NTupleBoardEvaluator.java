package put.ci.cevo.games.encodings.ntuple.eval;

import put.ci.cevo.games.board.Board;
import put.ci.cevo.games.encodings.ntuple.NTuples;

public interface NTupleBoardEvaluator {

	public double evaluate(NTuples tuples, Board board);

}
