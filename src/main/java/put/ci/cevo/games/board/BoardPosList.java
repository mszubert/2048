package put.ci.cevo.games.board;

import java.util.Collection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** Represents a list of position on a rectangular board. */
public final class BoardPosList {
	private final BoardPos[] positions;

	public BoardPosList(BoardPos[] positions) {
		this.positions = positions;
	}

	public BoardPosList(Collection<BoardPos> positions) {
		this(positions.toArray(new BoardPos[positions.size()]));
	}

	/** All positions are shifted to the left and up, so that some of them are in row 0 and some of them are on column 0 */
	public BoardPosList getAligned() {
		BoardPos minPos = getMinCorner();
		return getShifted(-minPos.row(), -minPos.column());
	}

	/** All positions are shifted to the right and down by shift. Shifts may be negative. */
	public BoardPosList getShifted(int shiftRow, int shiftColumn) {
		BoardPos[] shifted = new BoardPos[positions.length];
		for (int i = 0; i < positions.length; ++i) {
			shifted[i] = positions[i].add(new BoardPos(shiftRow, shiftColumn));
		}
		return new BoardPosList(shifted);
	}

	/** Minimal corner = minimal row and minimal column */
	private BoardPos getMinCorner() {
		int minRow = positions[0].row();
		int minColumn = positions[0].column();
		for (BoardPos pos : positions) {
			minRow = Math.min(minRow, pos.row());
			minColumn = Math.min(minColumn, pos.column());
		}
		return new BoardPos(minRow, minColumn);
	}

	/** Locations are margin-based. */
	public int[] toLocations(RectSize boardSize) {
		int[] locations = new int[positions.length];
		for (int i = 0; i < positions.length; ++i) {
			locations[i] = BoardUtils.toMarginPos(boardSize, positions[i]);
		}
		return locations;
	}

	/** Whether all positions fit on the board */
	public boolean fitOnBoard(RectSize boardSize) {
		for (BoardPos pos : positions) {
			if (!boardSize.contains(pos)) {
				return false;
			}
		}
		return true;
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
		BoardPosList other = (BoardPosList) obj;
		return new EqualsBuilder().append(this.positions, other.positions).build();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(positions).build();
	}

	public int size() {
		return positions.length;
	}
}