package sort;

public class CocktailSorter extends Sorter{
	
	public CocktailSorter () {
		super("Cocktail sorter: ");
	}
	
	@Override
	public double[] sort(double[] unsorted) {
		return cocktailSort(unsorted);
	}
	

	public double[] cocktailSort(double[] unsorted ) { 
		
		boolean touched = true ; // flag to check if we have swapped any elements throughout loop 
		while(touched){ // continue looping while we are swapping elements 
				
			touched = false; 
			
			// loop forwards through elements and swap any that are in the wrong order 
			for(int i = 0; i < unsorted.length-1; i++) {
				if(unsorted[i] > unsorted[i+1]){
					swap(unsorted, i, i+1);
					touched = true;
				}
			}
			
			if(!touched){ break; } // if we did not swap break from loop 
			
			
			// loop backwards through elements and swap any that are in the wrong order 
			for(int i = unsorted.length-2; i >= 0; i--) {
				if(unsorted[i] > unsorted[i+1]){
					swap(unsorted, i, i+1);
					touched = true;
				}
			}
		}
		return unsorted;
	}
}
