package put.ci.cevo.rl.environment;

import org.apache.commons.math3.random.RandomDataGenerator;

public interface Agent<S extends State, A extends Action> {

	void observeTransition(Transition<S, A> transition);

	A chooseAction(S state, Environment<S, A> environment, RandomDataGenerator random);
}
