package put.ci.cevo.games.board;

public interface Board {

	public static final int WHITE = -1;
	public static final int BLACK = 1;
	public static final int EMPTY = 0;

	// is this really necessary here?
	public static final int[][] DIRS = { new int[] { -1, -1 }, new int[] { -1, 0 }, new int[] { -1, 1 },
		new int[] { 0, -1 }, new int[] { 0, 1 }, new int[] { 1, -1 }, new int[] { 1, 0 }, new int[] { 1, 1 } };

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
	public void setValue(int pos, int color);

	/** Inverts each BLACK to WHITE and vice versa */
	public void invert();

	// TODO: Currently Board has the logic of a game. I consider making Board a simple class that has no game logic.
	// This way game logic could be grouped in particular game class. To do this, I need to remove createAfterState from
	// here (this method stops me from doing that)
	public Board createAfterState(int row, int col, int player);

	public Board clone();

}
