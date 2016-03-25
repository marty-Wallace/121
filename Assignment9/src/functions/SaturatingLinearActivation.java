package functions;

public final class SaturatingLinearActivation extends ActivationFunction{

	@Override
	public double calc(double value) {
		return satLin(value);
	}

	@Override
	public double calcInverse(double value) {
		return 1/ satLin(value);
	}

	private double satLin(double value) {
		
		if(value < -1) {
			return -1;
		}else if(value > 1) {
			return 1;
		}else{ 
			return value; 
		}
	}
	
}
