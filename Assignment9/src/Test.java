import java.util.Scanner;

import components.Neuron;
import functions.SaturatingLinearActivation;
import functions.SigmoidFunction;

public class Test {
	
	public static void main(String[]args) {
		
		System.out.println("Hello");
		
		Neuron xor = new Neuron(0.5f, new SaturatingLinearActivation());
		Neuron left = new Neuron(1.5f, new SaturatingLinearActivation());
		Neuron right = new Neuron(-0.5f, new SaturatingLinearActivation());
		left.setWeight(-0.1f);
		right.setWeight(0.1f);
		xor.connect(left, right);
		
		Scanner input = new Scanner(System.in);
		
		for(int i = 0; i < 2; i ++) {
			Neuron op = new Neuron(0.0f, new SaturatingLinearActivation());
			if(input.nextLine().equals("true")){
				op.setWeight(true);
			}else {
				op.setWeight(false);
			}
			left.connect(op);
			right.connect(op);
		}
		
		xor.fire();
		
		System.out.println("Result: " + xor.isFired());
		
	}

}
