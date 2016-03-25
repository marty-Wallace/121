package sort;

/**
 * Quicksorter class 
 * 
 * @author Martin Wallace 
 *
 */
public class QuickSorter extends Sorter {
	
	public QuickSorter() {
		super("Quicksort sorter: ");
	}

	
	@Override
	public double[] sort(double[] unsorted) {
		return quicksort(unsorted, 0, unsorted.length-1);
	}
	
	/**
	 * Quicksort a double array 
	 * @param unsorted - the array to sort 
	 * @return - sorted array
	 */
	private double[] quicksort(double[] unsorted, int low, int high) {
		
		int left = low; 
		int right = high;
		
		double pivot = unsorted[low+(high-low)/2]; // set pivot to the middle element 
		
		while(left <= right) { // loop while left index is less or equal to right index 
			
			while(unsorted[left] < pivot) { // increment left index until we find something on the left greater than the pivot 
				left ++; 
			}
			
			while(unsorted[right] > pivot) { //increment right index until we find something on the right less than the pivot 
				right -- ; 
			}
			
			if(left <= right ) {  // if we haven't crossed over out pivot then, swap the elements 
				swap(unsorted, left, right);
				left ++;
				right --;
			}
			
		}
		
		if(low < right) { // if right <= low then we are done sorting this side 
			quicksort(unsorted, low, right);
		}
		
		if( left < high) { // if left <= high index we are done sorting this side
			quicksort(unsorted, left, high);
		}
		
		return unsorted;
	}

}
