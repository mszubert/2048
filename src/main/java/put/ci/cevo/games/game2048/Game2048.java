package put.ci.cevo.games.game2048;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.Transition;
import put.ci.cevo.util.Pair;

public class Game2048 implements Environment<State2048, Action2048> {

	@Override
	public Transition<State2048, Action2048> computeTransition(State2048 state, Action2048 action) {
		State2048 afterState = new State2048(state);
		int reward = afterState.makeMove(action);
		return new Transition<>(state, action, afterState, reward);
	}

	@Override
	public State2048 getNextState(State2048 state, RandomDataGenerator random) {
		State2048 nextState = new State2048(state);
		nextState.addRandomTile(random);
		return nextState;
	}

	public List<Pair<Double, State2048>> getPossibleNextStates(State2048 afterState) {
		return afterState.getPossibleNextStates();
	}

	@Override
	public List<Action2048> getPossibleActions(State2048 state) {
		return state.getPossibleMoves();
	}

	@Override
	public State2048 sampleInitialStateDistribution(RandomDataGenerator random) {
		return State2048.getInitialState(random);
	}

	@Override
	public boolean isTerminalState(State2048 state) {
		return state.isTerminal();
	}

	public Pair<Integer, Integer> playGame(Player2048 player, RandomDataGenerator random) {
		int sumRewards = 0;

		State2048 state = sampleInitialStateDistribution(random);
		List<Action2048> actions = getPossibleActions(state);
		while (!actions.isEmpty()) {
			Action2048 action = player.chooseAction(state, actions);
			Transition<State2048, Action2048> transition = computeTransition(state, action);
			sumRewards += transition.getReward();
			
			state = getNextState(transition.getAfterState(), random);
			actions = getPossibleActions(state);
		}

		return new Pair<>(sumRewards, state.getMaxTile());
	}
}
