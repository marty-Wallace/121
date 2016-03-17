package sort;


/**
 * Shellsorter class 
 * 
 * @author Martin Wallace 
 */
public class ShellSorter extends Sorter {

	public ShellSorter() {
		super("Shell sorter: ");
	}

	
	@Override
	public double[] sort(double[] unsorted) {
		return shellSort(unsorted);
	}

	
	private double[] shellSort(double[] unsorted) {
		int insideIndex, outsideIndex;
		double temp;
		
		int gap = 1; 
		//set size of gap relative to unsorted length
		while(gap <= unsorted.length/3) { gap = gap*3 + 1; } 
	
		while(gap > 0) { // continue sorting with smaller gap until gap == 0
			
			// middle loop: starting at gap
			for(outsideIndex = gap; outsideIndex < unsorted.length; outsideIndex ++) {
				temp = unsorted[outsideIndex]; // set temp to use for comparison and swap 
				insideIndex = outsideIndex; // start at the outside index 
				
				// inside loop: loop over items starting at inside index and decreasing by gap until we find one less than our temp 
				// we shift everything inside our array by the gap until we find the one less
				while( insideIndex > gap -1 && unsorted[insideIndex -gap] >= temp) {
					unsorted[insideIndex] = unsorted[insideIndex -gap];
					insideIndex -= gap;
				}
				// here we set the temp to be the last index-gap before the number less than it 
				unsorted[insideIndex] = temp;
			}
			
			// after we exit outside loop we decrease gap by -1 then by /3 
			gap = (gap-1)/3;
		}
		return unsorted; 
	}
}
