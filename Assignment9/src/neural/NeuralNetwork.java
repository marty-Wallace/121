package neural;

import functions.ActivationFunction;

public abstract class NeuralNetwork {
	
	protected int inputCount; 
	protected int hiddenCount;
	protected int outputCount;
	protected double learnRate;
	protected double momentum;
	protected ActivationFunction f;
	
	protected NeuralNetwork(int inputCount, int hiddenCount, int outputCount, double learnRate, double momentum, ActivationFunction f) {
		this.hiddenCount = hiddenCount;
		this.outputCount = outputCount;
		this.learnRate = learnRate;
		this.momentum = momentum;
		this.f = f;
	}
		
	protected abstract double[] calculate(double[] input);
	
	protected abstract boolean finishedLearning();

	protected abstract void calculateError(double[] ideal); 
	
	protected abstract void learn(); 
	
	protected abstract void reset();
	
	
}
