package put.ci.cevo.games.game2048;

import put.ci.cevo.rl.environment.Action;

public enum Action2048 implements Action {

	UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

	private int dirRow;
	private int dirCol;

	private Action2048(int dirRow, int dirCol) {
		this.dirRow = dirRow;
		this.dirCol = dirCol;
	}

	@Override
	public double[] getDescription() {
		return new double[] { dirRow, dirCol };
	}
}
