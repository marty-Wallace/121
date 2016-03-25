package components;

import java.util.ArrayList;

import functions.ActivationFunction;

public class Neuron {
	
	private ArrayList<Neuron> inputs; 
	private double weight; 
	private double threshold; 
	private boolean fired; 
	private ActivationFunction activationFunction;
	
	
	public Neuron(double threshold, ActivationFunction activationFunction) {
		this.threshold = threshold; 
		this.activationFunction = activationFunction;
		this.fired = false; 
		inputs = new ArrayList<Neuron>();
	}

	public void connect(Neuron ... neurons) {
		for(Neuron n : neurons) {
			inputs.add(n);
		}
	}
	
	public void setWeight(double newWeight) {
		weight = newWeight; 
	}
	
	public void setWeight(boolean newWeight) {
		weight = newWeight ? 1.0 : 0.0;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public double fire() {
		if(inputs.size() > 0) {
			double totalWeight = 0.0;
			for(Neuron n : inputs) {
				n.fire();
				if(n.isFired()){
					totalWeight =+ n.getWeight();
				}
			}
			fired = activationFunction.calc(totalWeight) > threshold;
			return totalWeight;
		}
		else if( weight != 0.0) {
			fired = activationFunction.calc(weight) > threshold;
			return weight;
		}
		return 0.0;
	}
	
	public boolean isFired() {
		return fired; 
	}
}
