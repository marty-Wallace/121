package sort;

public abstract class Sorter {
	
	protected String name; 
	
	protected Sorter(String name) {
		this.name = name; 
	}
	
	public abstract double[] sort (double[] unsorted);
	
	@Override
	public String toString() {
		return this.name;
	}
	
	protected void swap(double[] ar, int i, int j) {
		double temp = ar[i];
		ar[i] = ar[j];
		ar[j] = temp;
	}
}
