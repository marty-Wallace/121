package Tester;

import java.util.ArrayList;
import java.util.List;

import functions.ActivationFunction;
import utility.Matrix;
import utility.MatrixMath;

public class NeuralNetwork {
    
    private int inputLayerSize;
    private int outputLayerSize;
    private int hiddenLayerSize; 
    private ActivationFunction f;
    
    Matrix W1;
    Matrix W2;
    private Matrix z2;
    private Matrix a2;
    private Matrix z3;
    
    private Matrix yHat;
    
    
    public NeuralNetwork(int inputLayerSize, int outputLayerSize, int hiddenLayerSize, ActivationFunction f) {
        this.inputLayerSize =  inputLayerSize; 
        this.outputLayerSize = outputLayerSize; 
        this.hiddenLayerSize = hiddenLayerSize; 
        this.f = f;
        this.W1 = Matrix.random(inputLayerSize, hiddenLayerSize);
        this.W2 = Matrix.random(hiddenLayerSize, outputLayerSize);
    }
    
    
    public Matrix forward(Matrix X ) {
        z2 = X.dot(W1);
        a2 = f.calc(z2);
        z3 = a2.dot(W2);
        return f.calc(z3);
        
    }
    
    public double costFunction(Matrix X, Matrix actual) {
    	
    	this.yHat = forward(X);
    	return 0.5 * MatrixMath.sumMeanSquare(yHat, actual);
    }
    
    public List<Matrix> costFunctionPrime(Matrix X, Matrix actual) { 
    	
    	this.yHat = forward(X);
    	
    	Matrix delta3 = actual.minus(yHat).negative().multiply(f.calcPrime(z3));
    	Matrix djdW2 = a2.transpose().dot(delta3);
    	
    	Matrix delta2 = delta3.dot(W2.transpose()).multiply(f.calcPrime(z2));
    	Matrix djdW1 = X.transpose().dot(delta2);
    	List<Matrix> ret = new ArrayList<Matrix>();
    	ret.add(djdW1);
    	ret.add(djdW2);
    	return ret;
    }
    
    public Matrix getW1() {
    	return W1;
    }
        
    public Matrix getW2() {
    	return W2;
    }
}