package put.ci.cevo.rl.environment;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

public interface Environment<S extends State, A extends Action> {

	Transition<S, A> computeTransition(S state, A action);

	S getNextState(S state, RandomDataGenerator random);

	List<A> getPossibleActions(S state);

	S sampleInitialStateDistribution(RandomDataGenerator random);

	boolean isTerminalState(S state);
}
