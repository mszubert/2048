package put.ci.cevo.games.game2048;

import java.io.Serializable;
import java.util.Arrays;

import com.google.common.base.Preconditions;
import put.ci.cevo.games.board.Board;
import put.ci.cevo.games.board.BoardUtils;

public class Game2048Board implements Board, Serializable {

	public static final int SIZE = State2048.SIZE;
	static final int MARGIN_WIDTH = BoardUtils.MARGIN_WIDTH;
	static final int WIDTH = SIZE + 2 * MARGIN_WIDTH;
	static final int BUFFER_SIZE = WIDTH * WIDTH;
	static final int WALL = -2;

	final int[] buffer;

	private Game2048Board(int[] buffer) {
		Preconditions.checkArgument(buffer.length == BUFFER_SIZE);
		this.buffer = buffer.clone();
	}

	public Game2048Board(double[] input) {
		Preconditions.checkArgument(input.length == SIZE * SIZE);
		this.buffer = new int[BUFFER_SIZE];
		initMargins();
		for (int r = 0; r < SIZE; ++r) {
			for (int c = 0; c < SIZE; c++) {
				setValue(r, c, inputToBoardValue(input, r, c));
			}
		}
	}

	private static int inputToBoardValue(double[] input, int r, int c) {
		return (int) (input[r * SIZE + c] + 0.5);
	}

	private void initMargins() {
		for (int i = 0; i < WIDTH; ++i) {
			setValueInternal(0, i, WALL);
			setValueInternal(WIDTH - 1, i, WALL);
			setValueInternal(i, 0, WALL);
			setValueInternal(i, WIDTH - 1, WALL);
		}
	}

	private static int toPosInternal(int row, int col) {
		return row * WIDTH + col;
	}

	public int getSize() {
		return SIZE;
	}

	@Override
	public void setValue(int row, int col, int color) {
		buffer[toPos(row, col)] = color;
	}

	private void setValueInternal(int row, int col, int color) {
		buffer[toPosInternal(row, col)] = color;
	}

	@Override
	public int getValue(int row, int col) {
		return buffer[toPos(row, col)];
	}

	@Override
	public int getValue(int pos) {
		return buffer[pos];
	}

	/**
	 * Watch out: this position is margin-based (not 0-based)
	 */
	public static int toPos(int row, int col) {
		return (row + 1) * WIDTH + (col + 1);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("  ");
		for (int i = 0; i < getSize(); i++) {
			builder.append((char) ('A' + i));
		}
		builder.append("\n");

		for (int r = 0; r < getSize(); r++) {
			builder.append(r + 1 + " ");
			for (int c = 0; c < getSize(); c++) {
				builder.append(getValue(r, c));
			}
			builder.append("\n");
		}
		return builder.toString();
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
		Game2048Board other = (Game2048Board) obj;
		return Arrays.equals(buffer, other.buffer);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(buffer);
	}

	@Override
	public Game2048Board clone() {
		return new Game2048Board(this.buffer);
	}

	@Override
	public int getWidth() {
		return getSize();
	}

	@Override
	public int getHeight() {
		return getSize();
	}

}
