package functions;

public final class TahnFunction extends ActivationFunction{

	@Override
	public double calc(double value) {
		return tahn(value);
	}

	@Override
	public double calcInverse(double value) {
		double t = tahn(value);
		return 1.0 - (t * t);
	}

	private double tahn(double value) {
		double a = Math.exp(value);
		double b = Math.exp(-value);
		return (a-b)/(a+b);
	}

}
