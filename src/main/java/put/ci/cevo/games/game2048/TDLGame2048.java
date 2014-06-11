package put.ci.cevo.games.game2048;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.games.encodings.ntuple.NTuples;
import put.ci.cevo.rl.agent.functions.RealFunction;
import put.ci.cevo.rl.environment.Transition;
import put.ci.cevo.util.Pair;
import put.ci.cevo.util.RandomUtils;

public class TDLGame2048 {
	
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

	private Transition<State2048, Action2048> chooseBestTransition(State2048 state, RealFunction function) {
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
	
	private Transition<State2048, Action2048> chooseBestTransitionExpectiMax(State2048 state, RealFunction function) {
		List<Action2048> actions = game.getPossibleActions(state);
		Transition<State2048, Action2048> bestTransition = null;
		double bestValue = Double.NEGATIVE_INFINITY;
		
		for (Action2048 action : actions) {
			Transition<State2048, Action2048> transition = game.computeTransition(state, action);
			
			double value = transition.getReward();
			List<Pair<Double, State2048>> nextStates = game.getPossibleNextStates(transition.getAfterState());
			for (Pair<Double, State2048> nextState : nextStates) {
				value += (nextState.first() * function.getValue(nextState.second().getFeatures()));
			}
			
			if (value > bestValue) {
				bestTransition = transition;
				bestValue = value;
			}
		}

		return bestTransition;
	}
	
	public Pair<Integer, Integer> playByExpectimax(RealFunction vFunction, RandomDataGenerator random) {
		int sumRewards = 0;
		
		State2048 state = game.sampleInitialStateDistribution(random);
		while (!game.isTerminalState(state)) {
			Transition<State2048, Action2048> transition = chooseBestTransitionExpectiMax(state, vFunction);
			sumRewards += transition.getReward();
			state = game.getNextState(transition.getAfterState(), random);
		}

		return new Pair<>(sumRewards, state.getMaxTile());
	}
	
	
	private int numMoves = 0;
	public int getNumMoves() {
		return numMoves;
	}
	
	public Pair<Integer, Integer> playByAfterstates(RealFunction vFunction, RandomDataGenerator random) {
		int sumRewards = 0;
		numMoves = 0;
		
		State2048 state = game.sampleInitialStateDistribution(random);
		while (!game.isTerminalState(state)) {
			numMoves++;
			Transition<State2048, Action2048> transition = chooseBestTransition(state, vFunction);
			sumRewards += transition.getReward();
			state = game.getNextState(transition.getAfterState(), random);
		}

		return new Pair<>(sumRewards, state.getMaxTile());
	}
	
	public void TDAfterstateLearn(NTuples vFunction, double explorationRate, double learningRate, RandomDataGenerator random) {
		Game2048 game = new Game2048();
		State2048 state = game.sampleInitialStateDistribution(random);
		
		while (!game.isTerminalState(state)) {
			List<Action2048> actions = game.getPossibleActions(state);

			Transition<State2048, Action2048> transition = null;
			if (random.nextUniform(0, 1) < explorationRate) {
				Action2048 randomAction = RandomUtils.pickRandom(actions, random);
				transition = game.computeTransition(state, randomAction);
			} else {
				transition = chooseBestTransition(state, vFunction);
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

	public void TDExpectimaxLearn(NTuples vFunction, double explorationRate, double learningRate, RandomDataGenerator random) {
		Game2048 game = new Game2048();
		State2048 state = game.sampleInitialStateDistribution(random);
		
		while (!game.isTerminalState(state)) {
			List<Action2048> actions = game.getPossibleActions(state);

			Transition<State2048, Action2048> transition = null;
			if (random.nextUniform(0, 1) < explorationRate) {
				Action2048 randomAction = RandomUtils.pickRandom(actions, random);
				transition = game.computeTransition(state, randomAction);
			} else {
				transition = chooseBestTransitionExpectiMax(state, vFunction);
			}

			State2048 nextState = game.getNextState(transition.getAfterState(), random);
			
			double correctActionValue = transition.getReward();
			if (!game.isTerminalState(nextState)) {
				correctActionValue += vFunction.getValue(nextState.getFeatures());
			}
			
			vFunction.update(state.getFeatures(), correctActionValue, learningRate);
			state = nextState;
		}
	}
	
	public void TDLambdaLearn(NTuples vFunction, double explorationRate, double learningRate, double lambda, RandomDataGenerator random) {
		Game2048 game = new Game2048();
		State2048 state = game.sampleInitialStateDistribution(random);

//		int numTuples = vFunction.getAll().size();
//		Int2DoubleLinkedOpenHashMap[] eTraces = new Int2DoubleLinkedOpenHashMap[numTuples];
//		for (int i = 0; i < numTuples; i++) {
//			eTraces[i] = new Int2DoubleLinkedOpenHashMap();
//		}

		while (!game.isTerminalState(state)) {
			List<Action2048> actions = game.getPossibleActions(state);

			Action2048 chosenAction = null;
			if (random.nextUniform(0, 1) < explorationRate) {
				chosenAction = RandomUtils.pickRandom(actions, random);
			} else {
				double bestValue = Double.NEGATIVE_INFINITY;
				for (Action2048 action : actions) {
					Transition<State2048, Action2048> transition = game.computeTransition(state, action);
					double value = vFunction.getValue(transition.getAfterState().getFeatures());
					if (value > bestValue) { // resolve ties randomly?
						chosenAction = action;
						bestValue = value;
					}
				}
			}

			Transition<State2048, Action2048> transition = game.computeTransition(state, chosenAction);
			State2048 nextState = game.getNextState(transition.getAfterState(), random);

			double correctActionValue = transition.getReward();
			if (!game.isTerminalState(nextState)) {
				double value = vFunction.getValue(nextState.getFeatures());
				correctActionValue += value;
			}

			// double val = vFunction.getValue(state.getFeatures());
			// double error = correctActionValue - val;

			vFunction.update(state.getFeatures(), correctActionValue, learningRate);

//			for (int i = 0; i < numTuples; i++) {
//				Int2DoubleLinkedOpenHashMap map = eTraces[i];
//				for (int weight : map.keySet()) {
//					map.put(weight, map.get(weight) * lambda);
//				}
//			}
//
//			Game2048Board board = new Game2048Board(state.getFeatures());
//			for (int i = 0; i < numTuples; i++) {
//				NTuple tuple = vFunction.getTuple(i);
//				int weightIndex = tuple.address(board);
//
//				Int2DoubleLinkedOpenHashMap map = eTraces[i];
//				if (map.containsKey(weightIndex)) {
//					map.addTo(weightIndex, 1.0);
//				} else {
//					map.put(weightIndex, 1.0);
//				}
//
//				double[] weights = tuple.getWeights();
//				for (int weight : map.keySet()) {
//					weights[weight] += error * learningRate * map.get(weight);
//				}
//			}

			state = nextState;
		}
	}
}
