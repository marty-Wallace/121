package functions;

public final class SaturatingLinearActivation extends ActivationFunction{

	@Override
	public double calc(double value) {
		return satLin(value);
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


	@Override
	public double calcPrime(double value) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
