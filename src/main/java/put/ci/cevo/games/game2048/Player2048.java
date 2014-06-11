package put.ci.cevo.games.game2048;

import java.util.List;

public interface Player2048 {

	public Action2048 chooseAction(State2048 state, List<Action2048> actions);
}
