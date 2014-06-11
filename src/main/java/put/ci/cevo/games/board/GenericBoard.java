package put.ci.cevo.games.board;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.google.common.base.Preconditions;

//TODO: This class should be improved and refactored
public class GenericBoard implements Board {
	static final int MARGIN_SIZE = BoardUtils.MARGIN_WIDTH;

	public final int height;
	public final int width;

	int extWidth() {
		return width + 2 * MARGIN_SIZE;
	}

	int extHeight() {
		return height + 2 * MARGIN_SIZE;
	}

	int bufferSize() {
		return extHeight() * extWidth();
	}

	int[] dirs() {
		return new int[] { -extWidth() - 1, -extWidth(), -extWidth() + 1, -1, +1, extWidth() - 1, extWidth(),
			extWidth() + 1 };
	}

	final int WALL = -2;

	final int[] buffer;

	/** Creates an empty-filled rectangular board with height and width */
	private static int[][] emptyBoard(int height, int width) {
		int[][] board = new int[height][width];
		Arrays.fill(board, Board.EMPTY);
		for (int r = 0; r < board.length; r++) {
			Arrays.fill(board[r], Board.EMPTY);
		}
		return board;
	}

	public GenericBoard(int height, int width) {
		this(emptyBoard(height, width));
	}

	public GenericBoard(int[][] board) {
		this.height = board.length;
		this.width = board[0].length;

		buffer = new int[bufferSize()];
		initMargins();
		for (int r = 0; r < board.length; r++) {
			Preconditions.checkArgument(board[r].length == width);
			for (int c = 0; c < board[r].length; c++) {
				setValue(r, c, board[r][c]);
			}
		}
	}

	public GenericBoard(int height, int width, int[] buffer) {
		this.height = height;
		this.width = width;
		this.buffer = buffer.clone();
	}

	private void initMargins() {
		for (int col = 0; col < extWidth(); ++col) {
			setValueInternal(0, col, WALL);
			setValueInternal(extHeight() - 1, col, WALL);
		}
		for (int row = 0; row < extHeight(); ++row) {
			setValueInternal(row, 0, WALL);
			setValueInternal(row, extWidth() - 1, WALL);
		}
	}

	private void setValueInternal(int row, int col, int color) {
		buffer[toPosInternal(row, col)] = color;
	}

	private int toPosInternal(int row, int col) {
		return row * extWidth() + col;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setValue(int row, int col, int color) {
		buffer[toPos(row, col)] = color;
	}

	@Override
	public void setValue(int pos, int color) {
		buffer[pos] = color;
	}

	@Override
	public int getValue(int row, int col) {
		return buffer[toPos(row, col)];
	}

	@Override
	public int getValue(int pos) {
		return buffer[pos];
	}

	@Override
	public void invert() {
		throw new RuntimeException("Not impelmented. SHould not be any invert in this interface");
	}

	@Override
	public GenericBoard createAfterState(int row, int col, int player) {
		GenericBoard clonedBoard = clone();
		clonedBoard.makeMove(toPos(row, col), player);
		return clonedBoard;
	}

	public void makeMove(int move, int player) {
		buffer[move] = player;
	}

	public boolean isEmpty(int row, int col) {
		return isEmpty(toPos(row, col));
	}

	boolean isEmpty(int pos) {
		return buffer[pos] == EMPTY;
	}

	boolean isInBoard(int pos) {
		return buffer[pos] != WALL;
	}

	boolean isWall(int pos) {
		return buffer[pos] == WALL;
	}

	/**
	 * Watch out: this position is margin-based (not 0-based)
	 */
	public int toPos(int row, int col) {
		return (row + 1) * extWidth() + (col + 1);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GenericBoard other = (GenericBoard) obj;
		return new EqualsBuilder().append(this.width, other.width).append(this.height, other.height)
			.append(this.buffer, other.buffer).isEquals();
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(buffer);
	}

	@Override
	public GenericBoard clone() {
		return new GenericBoard(this.height, this.width, this.buffer);
	}

	@Override
	public String toString() {
		return BoardUtils.toString(this);
	}

}
