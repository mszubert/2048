package put.ci.cevo.games.board;

public final class BoardUtils {

	public static final int MARGIN_WIDTH = 1;
	public static final int TOTAL_MARGIN = 2 * MARGIN_WIDTH;

	private BoardUtils() {
		// A static class
	}

	/**
	 * Returned position is margin-based (not 0-based)
	 */
	public static int toMarginPos(RectSize boardSize, BoardPos pos) {
		return (pos.row() + 1) * (boardSize.width() + TOTAL_MARGIN) + (pos.column() + 1);
	}

	public static int toMarginPos(int boardWidth, int row, int col) {
		return toMarginPos(new RectSize(boardWidth), new BoardPos(row, col));
	}

	/**
	 * Checks whether position is a valid margin-based board position (inside board)
	 *
	 * @param pos
	 *            margin-based position
	 * @param boardSize
	 *            board size without margins (e.g., 8 for Othello)
	 *
	 *            Assuming square board
	 */
	public static boolean isValidPosition(int pos, int boardSize) {
		return isValidPosition(pos, boardSize, boardSize);
	}

	public static boolean isValidPosition(int pos, int rows, int cols) {
		int row = rowFromPos(pos, cols);
		int col = colFromPos(pos, cols);
		return 0 <= row && row < rows && 0 <= col && col < cols;
	}

	/**
	 * @param pos
	 *            is margin-based position
	 * @return 0-based row
	 */
	public static int rowFromPos(int pos, int boardWidth) {
		return pos / (boardWidth + TOTAL_MARGIN) - 1;
	}

	/**
	 * @param pos
	 *            is margin-based position
	 * @return 0-based column (i.e. the first column is 0, the last is boardSize-1)
	 */
	public static int colFromPos(int pos, int boardWidth) {
		return pos % (boardWidth + TOTAL_MARGIN) - 1;
	}
}
