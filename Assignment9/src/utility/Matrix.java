package utility;

public class Matrix {
	
	private final int ROW; 
	private final int COL; 

	private final double[][] matrix;

	public Matrix(int ROW, int COL) {
		this.ROW = ROW;
		this.COL = COL;
		matrix = new double[ROW][COL];
	}

	private Matrix(double[][] matrix) {
		this.ROW = matrix.length;
		this.COL = matrix[0].length;
		this.matrix = new double[ROW][COL];
		for(int i = 0; i < ROW; i++) {
			for(int j= 0; j < COL; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
	}
	
	private Matrix(Matrix m) { 
		this(m.matrix);
	} 
	
	public static Matrix identity(int N) {
		Matrix identity = new Matrix(N, N);
		for(int i = 0; i < N; i++) {
			identity.matrix[i][i] = 1;
		}
		return identity;
	}

	public Matrix transpose() {
		Matrix ret = new Matrix(COL, ROW); 
		for(int i = 0; i < ROW; i ++) {
			for(int j = 0; j < COL; j++) {
				ret.matrix[j][i] = this.matrix[i][j];
			}
		}
		return ret;
	}
	
	/**
	 * Swap two rows i and j
	 * @param i - row one
	 * @param j - row two 
	 */
	public void swap(int i, int j) {
		double[] temp = matrix[i];
		matrix[i] = matrix[j];
		matrix[j] = temp;
	}
	
	public static void validate(Matrix A, Matrix B) {
		if(A.ROW != B.ROW || A.COL != B.COL) {
			throw new RuntimeException("Illegal matrix dimensions.");
		}
	}
	
	public Matrix plus(Matrix B) {
		validate(this, B);
		Matrix A = this; 
		Matrix C = new Matrix(B.ROW, B.COL);
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				C.matrix[i][j] = A.matrix[i][j] + B.matrix[i][j];
			}
		}
		return C;
	}
	
	public Matrix minus(Matrix B) {
		validate(this, B); 
		Matrix A = this; 
		Matrix C = new Matrix(ROW, COL);
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				C.matrix[i][j] = A.matrix[i][j] - B.matrix[i][j];
			}
		}
		return C; 
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Matrix)) {
			return false;
		}
		Matrix M = (Matrix) o;
		validate(this, M);
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				if(this.matrix[i][j] != M.matrix[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	public Matrix multiply(Matrix B) {
		Matrix A = this;
		Matrix C = new Matrix(A.ROW, B.COL);
		if(A.ROW != B.COL|| A.COL != B.ROW) {
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		for(int i = 0; i < C.ROW; i++) {
			for(int j = 0; j < C.COL; j++) {
				for(int k = 0; k < A.COL; k++) {
					C.matrix[i][j] += (A.matrix[i][k] * B.matrix[k][j]);
				}
			}
		}
		return C;
	}
	
	public Matrix multiply(double d) {
		Matrix ret = new Matrix(this.matrix); 
		for(int i = 0; i < ROW; i ++) {
			for(int j = 0; j < COL; j++){
				ret.matrix[i][j] *= d;
			}
		}
		return ret;
	}
	
    public void show() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                System.out.printf("%9.4f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
    
    public static Matrix random(int ROW, int COL) {
    	Matrix ret = new Matrix(ROW, COL);
    	for(int i = 0; i < ROW; i++) {
    		for(int j = 0 ; j < COL; j++) {
    			ret.matrix[i][j] = Math.random();
    		}
    	}
    	return ret;
    }
    
}
