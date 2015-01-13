package put.ci.cevo.rl.environment;

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
}
