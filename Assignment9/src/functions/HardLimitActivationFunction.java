package functions;

public final class HardLimitActivationFunction extends ActivationFunction{

	@Override
	public double calc(double value) {
		return hardLim(value);
	}


	
	private double hardLim(double value) {
		if(value >= 0) {
			return 1;
		}
		return 0;
	}



	@Override
	public double calcPrime(double value) {
		// TODO Auto-generated method stub
		return 0;
	}

}
