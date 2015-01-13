package put.ci.cevo.games.encodings.ntuple.expanders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import put.ci.cevo.games.board.BoardUtils;

import com.google.common.base.Preconditions;

/**
 * Standard 8 symmetries (mirrors and rotations) for a quadratic board
 */
public class StandardSymmetryExpander implements SymmetryExpander {

	private final int boardSize;

	public StandardSymmetryExpander(int boardSize) {
		this.boardSize = boardSize;
	}

	@Override
	public int[] getSymmetries(int location) {
		Preconditions.checkArgument(BoardUtils.isValidPosition(location, boardSize()), "Location: " + location
			+ " is invalid; board size: " + boardSize());

		int M = boardSize - 1;

		int c = BoardUtils.colFromPos(location, boardSize);
		int r = BoardUtils.rowFromPos(location, boardSize);

		// @formatter:off
		int[] a = new int[] {
			flat(c, r),
			flat(M - c, r),
			flat(c, M - r),
			flat(M - c, M - r),
			flat(r, c),
			flat(M - r, c),
			flat(r, M - c),
			flat(M - r, M - c)
		};
		// @formatter:on

		assert a.length == numSymmetries();

		return a;
	}

	public int flat(int c, int r) {
		return BoardUtils.toMarginPos(boardSize, r, c);
	}

	public int boardSize() {
		return boardSize;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int numSymmetries() {
		return 8;
	}
}
