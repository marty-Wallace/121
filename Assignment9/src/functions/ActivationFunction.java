package functions;

public abstract class ActivationFunction {
	
	protected ActivationFunction() {
		
	}
	
	public abstract double calc(double value);
	public abstract double calcInverse(double value);
	
	public static double rootMeanSquare(double[] errors){	
		return Math.sqrt(meanSquare(errors));
	}
	
	public static double meanSquare(double[] errors) {
		double total = 0; 		
		for(int i = 0; i <errors.length; i++) {
			total += errors[i] * errors[i];
		}
		total /= errors.length;
		
		return total; 
	}

}
