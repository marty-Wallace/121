package neural;

import functions.ActivationFunction;

public class XORNetwork extends NeuralNetwork{

	protected XORNetwork(int inputCount, int hiddenCount, int outputCount, double learnRate, double momentum,
			ActivationFunction f) {
		super(inputCount, hiddenCount, outputCount, learnRate, momentum, f);
	}

	@Override
	protected double[] calculate(double[] input) {
		
		return null; 
	}

	@Override
	protected boolean finishedLearning() {
		
		return true;
	}

	@Override
	protected void calculateError(double[] ideal) {
		
	}

	@Override
	protected void learn() {
		
	}
	

	@Override
	protected void reset() {
		
	}

}
