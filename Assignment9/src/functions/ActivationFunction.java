package functions;

import utility.Matrix;

public abstract class ActivationFunction {
	
	protected ActivationFunction() {
		
	}
	
	public abstract double calc(double value);
	public abstract double calcPrime(double value);
	
	public Matrix calcPrime(Matrix m) {
		double[][]d = m.getArray();
		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < d[0].length; j++ ){
				d[i][j] = calcPrime(d[i][j]);
			}
		}
		return new Matrix(d);
	}
	public Matrix calc(Matrix m) {
		double[][] d = m.getArray();
		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < d[0].length; j++) {
				d[i][j] = calc(d[i][j]);
			}
		}
		return new Matrix(d);
	}

}
