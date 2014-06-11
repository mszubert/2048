package put.ci.cevo.rl.environment;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

public class Transition<S extends State, A extends Action> {

	private S state;
	private A action;
	private S afterState;

	private double reward;
	private boolean isTerminal;

	public Transition(S state, A action, S afterState, double reward, boolean isTerminal) {
		this.state = state;
		this.action = action;
		this.afterState = afterState;
		this.reward = reward;
		this.isTerminal = isTerminal;
	}
	
	public Transition(S state, A action, S afterState, double reward) {
		this(state, action, afterState, reward, false);
	}

	public S getState() {
		return state;
	}

	public A getAction() {
		return action;
	}

	public S getAfterState() {
		return afterState;
	}

	public double getReward() {
		return reward;
	}

	public boolean isTerminal() {
		return isTerminal;
	}
	
	public static <S extends State, A extends Action> Transition<S, A> getAgentTransition(Environment<S, A> env,
			S state, Agent<S, A> agent, RandomDataGenerator random) {
		A action = agent.chooseAction(state, env, random);
		return env.computeTransition(state, action);
	}

	public static <S extends State, A extends Action> Transition<S, A> getRandomTransitionFromState(
			Environment<S, A> env, S state, RandomDataGenerator random) {
		List<A> actions = env.getPossibleActions(state);
		A action = chooseRandomMove(actions, random);
		return env.computeTransition(state, action);
	}

	public static <A extends Action> A chooseRandomMove(List<A> possibleActions, RandomDataGenerator random) {
		if (possibleActions.isEmpty()) {
			return null;
		} else if (possibleActions.size() == 1) {
			return possibleActions.get(0);
		} else {
			return possibleActions.get(random.nextInt(0, possibleActions.size() - 1));
		}
	}
}
