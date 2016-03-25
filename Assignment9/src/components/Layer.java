package components;

import utility.Matrix;

public class Layer {
	
	private boolean hasMatrix; 
	private Matrix weights; 
	private int numNeurons; 
	private Neuron[] neurons; 
	private Layer inputLayer; 
	
	/**
	 * 
	 * @param numNeurons
	 * @param hasMatrix
	 */
	public Layer(int numNeurons) {
		this.numNeurons = numNeurons; 
		this.hasMatrix = false; 
		this.neurons = new Neuron[numNeurons];
	}
	
	/**
	 * 
	 * @param neurons
	 */
	public Layer(Neuron[] neurons) {
		this.numNeurons = neurons.length;
		this.hasMatrix = false;
		this.neurons = neurons;
	}
	
	/**
	 * 
	 * @param neurons
	 * @param m
	 */
	public Layer(Neuron[] neurons, Matrix m) {
		this.numNeurons = neurons.length;
		this.neurons = neurons;
		this.weights = m;
		this.hasMatrix = true;
	}
	
	public void setMatrix(Matrix weights) {
		if(hasMatrix) {
			throw new RuntimeException("Cannot set Matrix on a layer that already contains a matrix");
		}
		this.weights = weights;
		hasMatrix = true;
	}
	
	public boolean hasMatrix() {
		return this.hasMatrix;
	}
	
	public void addAllInputs(Neuron[] inputs) {
		for(Neuron n : neurons) {
			n.connect(inputs);
		}
	}
	
	public void connectLayer(Layer layer) {
		this.inputLayer = layer;
		Neuron[] inputs = layer.neurons;
		addAllInputs(inputs);
	}
	
}
