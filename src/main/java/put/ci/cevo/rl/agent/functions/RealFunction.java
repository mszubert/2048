package put.ci.cevo.rl.agent.functions;

public interface RealFunction {
	
	double getValue(double[] input);
	
	void update(double[] input, double expectedValue, double learningRate);
}
