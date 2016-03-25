package functions;

public final class HardLimitActivationFunction extends ActivationFunction{

	@Override
	public double calc(double value) {
		return hardLim(value);
	}

	@Override
	public double calcInverse(double value) {
		return 1-hardLim(value);
	}
	
	private double hardLim(double value) {
		if(value >= 0) {
			return 1;
		}
		return 0;
	}

}
