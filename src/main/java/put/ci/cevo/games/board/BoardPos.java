package put.ci.cevo.games.board;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/** Represents a position on a rectangular board. Position is 0-based */
public final class BoardPos {
	private final int row;
	private final int column;

	public int row() {
		return row;
	}

	public int column() {
		return column;
	}

	/**
	 * row or column can be negative
	 */
	public BoardPos(int row, int column) {
		this.row = row;
		this.column = column;
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
		BoardPos other = (BoardPos) obj;
		return (this.row == other.row && this.column == other.column);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(row).append(column).build();
	}

	public BoardPos add(BoardPos pos) {
		return new BoardPos(row + pos.row, column + pos.column);
	}
}
