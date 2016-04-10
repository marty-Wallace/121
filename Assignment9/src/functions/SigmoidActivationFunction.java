package functions;

public final class SigmoidActivationFunction extends ActivationFunction {
	

	@Override
	public double calc(double value) {
		return sig(value);
	}

	public double calcPrime(double value) {
		return Math.exp(-value)/Math.pow((1 + Math.exp(-value)), 2);
	}

	private double sig(double value) {
		return 1.0/ (1.0 + Math.exp(-value));
	}
}
