package functions;

public final class SigmoidFunction extends ActivationFunction {
	

	@Override
	public double calc(double value) {
		return sig(value);
	}

	@Override
	public double calcInverse(double value) {
		double s = sig(value);
		return 1.0 - (s * s);
	}

	private double sig(double value) {
		return 1.0/ 1.0 + Math.exp(-value);
	}
}
