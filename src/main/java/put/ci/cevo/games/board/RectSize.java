package put.ci.cevo.games.board;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** Represents size of a rectangle (such as 2D board) */
public final class RectSize {
	private final int columns;
	private final int rows;

	public int width() {
		return columns;
	}

	public int height() {
		return rows;
	}

	public int columns() {
		return columns;
	}

	public int rows() {
		return rows;
	}

	public RectSize(int size) {
		this(size, size);
	}

	public RectSize(int rows, int columns) {
		Preconditions.checkArgument(0 < columns);
		Preconditions.checkArgument(0 < rows);
		this.columns = columns;
		this.rows = rows;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
		RectSize other = (RectSize) obj;
		return (this.columns == other.columns && this.rows == other.rows);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(columns).append(rows).build();
	}

	/** Whether position is a proper position on a board of given size */
	public boolean contains(BoardPos position) {
		return 0 <= position.row() && position.row() < rows() && 0 <= position.column()
			&& position.column() < columns();
	}
}