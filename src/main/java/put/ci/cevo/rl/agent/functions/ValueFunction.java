package put.ci.cevo.rl.agent.functions;

import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.State;

public interface ValueFunction<S extends State, A extends Action> {

	double getValue(S state, A action);
	
	void update(S state, A action, double expectedValue, double learningRate);
}
