package Tester;

import java.util.List;

import functions.ActivationFunction;
import functions.SigmoidActivationFunction;
import utility.Matrix;

public class Driver {

	    Matrix X;
	    Matrix Y;
	    ActivationFunction f;
	    NeuralNetwork NN;
	    
	    int scalar = 3; 

	    public static void main(String[] args) {

	    	new Driver();
	    	
	    	
	    } 
	    
	    public Driver() {
	    	this.f = new SigmoidActivationFunction();
	    	initTestData();
	    	NN = new NeuralNetwork(2, 1, 3, f);
	    	double cost1 = NN.costFunction(X, Y);

	    	applyTraining();
	    	applyTraining();
	    	
	    	double cost2 = NN.costFunction(X, Y);
	    	
	    	applyTraining();
	    	applyTraining();
	    	
	    	double cost3 = NN.costFunction(X, Y);
	    	
	    	System.out.println(cost1 + "\n" + cost2 + "\n" + cost3);
	    }
	    
	    public void applyTraining() {
	    	List<Matrix> listy = NN.costFunctionPrime(X, Y);
	    	Matrix djdW1 = listy.get(0);
	    	Matrix djdW2 = listy.get(1);

	    	
	    	NN.W1 = NN.W1.minus(djdW1.multiply(scalar));
	    	NN.W2 = NN.W2.minus(djdW2.multiply(scalar));
	    }
	    
	    public void initTestData() {
	        double[][] x = {{3, 5}, {5, 1}, { 10, 2 } }; 
	        double[][] y = {{75}, {82}, {93}};
	        X = new Matrix(x);
	        Y = new Matrix(y);
	        X.normalize(24); //24 is max hours 
	        Y.normalize(100);  // 100 is max testscore 
	    }


	    public static void printM(Matrix A) {
	        System.out.println(A);

	    }
	}