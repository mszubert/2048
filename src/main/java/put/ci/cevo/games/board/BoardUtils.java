package put.ci.cevo.games.board;

import com.google.common.base.Preconditions;

public final class BoardUtils {
	// TODO: Most of the methods in this class could operate on BoardSize class
	// Some methods could be members of Board
	// TODO: Made cohesive names: cols/rows vs. boardWidth/boardHeight

	public static final int MARGIN_WIDTH = 1;
	public static final int TOTAL_MARGIN = 2 * MARGIN_WIDTH;

	private BoardUtils() {
		// A static class
	}

	public static double[] getValues(Board board) {
		int rows = board.getHeight();
		int cols = board.getWidth();
		double[] values = new double[rows * cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				values[r * cols + c] = board.getValue(r, c);
			}
		}
		return values;
	}

	// TODO: Remove it from here. It is game dependent!
	public static int countDepth(Board board) {
		int count = 0;
		int rows = board.getHeight();
		int cols = board.getWidth();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (board.getValue(r, c) != Board.EMPTY) {
					count += 1;
				}
			}
		}
		return count - 4;
	}

	public static int countPieces(Board board, int color) {
		int count = 0;
		int rows = board.getHeight();
		int cols = board.getWidth();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (board.getValue(r, c) == color) {
					count += 1;
				}
			}
		}
		return count;
	}

	/**
	 * Returned position is margin-based (not 0-based)
	 *
	 * row and col are 0-based
	 */
	public static int toPos(int boardWidth, int row, int col) {
		return (row + 1) * (boardWidth + TOTAL_MARGIN) + (col + 1);
	}

	/**
	 * Converts margin-based position to 0-based position (like in Othello League). Square board assumed
	 **/
	public static int marginPosToPos(int pos, int boardSize) {
		return marginPosToPos(pos, boardSize, boardSize);
	}

	/**
	 * Converts margin-based position to 0-based position (like in Othello League)
	 **/
	public static int marginPosToPos(int pos, int rows, int cols) {
		Preconditions.checkArgument(isValidPosition(pos, rows, cols));
		int row = rowFromPos(pos, cols);
		int col = colFromPos(pos, cols);
		return row * cols + col;
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

	public static char pieceToChar(int piece) {
		if (piece == Board.EMPTY) {
			return '-';
		}
		if (piece == Board.BLACK) {
			return 'b';
		}
		if (piece == Board.WHITE) {
			return 'w';
		}

		assert (false);
		return '?';
	}

	public static String posToString(int pos, Board board) {
		int row = BoardUtils.rowFromPos(pos, board.getWidth());
		int col = BoardUtils.colFromPos(pos, board.getWidth());
		return (char) ('A' + row) + "" + (col + 1);
	}

	public static String toString(Board board) {
		StringBuilder builder = new StringBuilder();
		builder.append("  ");
		for (int i = 0; i < board.getWidth(); i++) {
			builder.append((char) ('A' + i));
		}
		builder.append("\n");

		for (int r = 0; r < board.getHeight(); r++) {
			builder.append(r + 1 + " ");
			for (int c = 0; c < board.getWidth(); c++) {
				builder.append(BoardUtils.pieceToChar(board.getValue(r, c)));
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
