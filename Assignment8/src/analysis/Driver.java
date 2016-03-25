package analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import sort.CocktailSorter;
import sort.QuickSorter;
import sort.ShellSorter;
import sort.Sorter;

public class Driver {
	
	public static void main(String[] args) {
		
		ArrayList<Sorter> sorts = new ArrayList<Sorter>();
		
		sorts.add(new QuickSorter());
		sorts.add(new CocktailSorter());
		sorts.add(new ShellSorter());
		
		Random ran = new Random();
		
		int num = 1000000; 
		
		double tester [] = new double [num];
		ArrayList<Double> tester2 = new ArrayList<Double>();
		
		for(int i = 0; i < num; i++) {
			
			tester[i] = num -i;
			tester2.add(tester[i]);
			
		}

		
		
		
		for(Sorter s : sorts) {
			
			double [] copy = Arrays.copyOf(tester, tester.length);
			long start = System.currentTimeMillis();
			s.sort(copy);
			long end = System.currentTimeMillis();
			System.out.println(s + "time taken: " + (end-start) + "(ms)");
			
		}
		
		long start = System.currentTimeMillis();
		Arrays.sort(tester);
		long end = System.currentTimeMillis();
		System.out.println("Arrays.sort() int time taken: " + (end-start) + "(ms)");
		
		
		start = System.currentTimeMillis();
		Collections.sort(tester2);
		end = System.currentTimeMillis();
		System.out.println("Collections.sort() time taken: " + (end-start) + "(ms)");
	}

	/**
	 * Utility method for printing array to test sorting 
	 * 
	 * @param printy - array to print
	 */
	public static void printArray(double[] printy) {
		
		for(int i =0; i < printy.length; i++) {
			System.out.print(printy[i] + " " );
		}
		System.out.println();
	}
}
