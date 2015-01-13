package put.ci.cevo.games.board;

public interface Board {

	public int getWidth();
	public int getHeight();

	/**
	 * @param row
	 *            0-based index
	 * @param col
	 *            0-based index
	 */
	public int getValue(int row, int col);
	public void setValue(int row, int col, int color);

	/**
	 * @param pos
	 *            pos is not 0-based. It is currently implementation dependent
	 */
	public int getValue(int pos);

	public Board clone();
}
