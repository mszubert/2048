package put.ci.cevo.games.game2048;

import put.ci.cevo.rl.environment.Action;

public enum Action2048 implements Action {

	UP(0, 0, -1),
	RIGHT(1, 1, 0),
	DOWN(2, 0, 1),
	LEFT(3, -1, 0);

	private final int dirRow;
	private final int dirCol;
	private final int id;

	private Action2048(int id, int dirRow, int dirCol) {
		this.id = id;
		this.dirRow = dirRow;
		this.dirCol = dirCol;
	}

	@Override
	public double[] getDescription() {
		return new double[] { dirRow, dirCol };
	}

	public int id() {
		return id;
	}
}
