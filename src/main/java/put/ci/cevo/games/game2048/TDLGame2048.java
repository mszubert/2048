package put.ci.cevo.games.game2048;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.games.encodings.ntuple.NTuples;
import put.ci.cevo.rl.agent.functions.RealFunction;
import put.ci.cevo.rl.environment.Transition;
import put.ci.cevo.util.Pair;
import put.ci.cevo.util.RandomUtils;

public class TDLGame2048 {

	public final class Game2048Outcome {
		private final int score;
		private final int maxTile;

		public Game2048Outcome(int score, int maxTile) {
			this.score = score;
			this.maxTile = maxTile;

		}

		public int score() {
			return score;
		}

		public int maxTile() {
			return maxTile;
		}
	}

	private final Game2048 game;

	public TDLGame2048() {
		game = new Game2048();
	}

	private double getBestValueAction(State2048 state, RealFunction function) {
		List<Action2048> actions = game.getPossibleActions(state);

		double bestValue = Double.NEGATIVE_INFINITY;
		for (Action2048 action : actions) {
			Transition<State2048, Action2048> transition = game.computeTransition(state, action);
			double value = transition.getReward() + function.getValue(transition.getAfterState().getFeatures());
			if (value > bestValue) {
				bestValue = value;
			}
		}

		return bestValue;
	}

	private Transition<State2048, Action2048> chooseBestTransitionAfterstate(State2048 state, RealFunction function) {
		List<Action2048> actions = game.getPossibleActions(state);
		Transition<State2048, Action2048> bestTransition = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		for (Action2048 action : actions) {
			Transition<State2048, Action2048> transition = game.computeTransition(state, action);
			double value = transition.getReward() + function.getValue(transition.getAfterState().getFeatures());
			if (value > bestValue) {
				bestTransition = transition;
				bestValue = value;
			}
		}

		return bestTransition;
	}

	public Game2048Outcome playByAfterstates(RealFunction vFunction, RandomDataGenerator random) {
		int sumRewards = 0;

		State2048 state = game.sampleInitialStateDistribution(random);
		while (!game.isTerminalState(state)) {
			Transition<State2048, Action2048> transition = chooseBestTransitionAfterstate(state, vFunction);
			sumRewards += transition.getReward();
			state = game.getNextState(transition.getAfterState(), random);
		}

		return new Game2048Outcome(sumRewards, state.getMaxTile());
	}

	public void TDAfterstateLearn(NTuples vFunction, double explorationRate, double learningRate,
			RandomDataGenerator random) {
		State2048 state = game.sampleInitialStateDistribution(random);

		while (!game.isTerminalState(state)) {
			List<Action2048> actions = game.getPossibleActions(state);

			Transition<State2048, Action2048> transition = null;
			if (random.nextUniform(0, 1) < explorationRate) {
				Action2048 randomAction = RandomUtils.pickRandom(actions, random);
				transition = game.computeTransition(state, randomAction);
			} else {
				transition = chooseBestTransitionAfterstate(state, vFunction);
			}

			State2048 nextState = game.getNextState(transition.getAfterState(), random);

			double correctActionValue = 0;
			if (!game.isTerminalState(nextState)) {
				correctActionValue += getBestValueAction(nextState, vFunction);
			}

			vFunction.update(transition.getAfterState().getFeatures(), correctActionValue, learningRate);
			state = nextState;
		}
	}
}
