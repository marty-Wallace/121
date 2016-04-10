package utility;

public class MatrixMath {
	
	
	public static double sumMeanSquare(Matrix A, Matrix B) {
		double[][]a = A.getArray();
		double[][]b = B.getArray();
		if(a[0].length > 1 || b[0].length > 1) {
			throw new RuntimeException("Cannot take RootMeanSquare error of Matrix with more than 1 col ");
		}
		if(a.length != b.length) {
			throw new RuntimeException("Matrices must be the same length to take the RootMeanSquare");
		}
		double totalError = 0; 
		for(int i = 0; i < a.length; i++) {
			totalError += Math.pow((a[i][0] - b[i][0]), 2);
		}
		totalError /= 2; 
		
		return totalError;
		
	}

}
