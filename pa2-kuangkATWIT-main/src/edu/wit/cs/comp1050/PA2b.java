package edu.wit.cs.comp1050;
import java.util.Scanner;
/**
 * 
 * You  are  to  write  a  program  that  inputs  an  array  of  
 * integers  and  then  outputs  the  length  of  the  longest 
 * sequence of repeating values.
 *
 */
public class PA2b {
	
	/**
	 * Error to supply if input is not positive
	 */
	public static final String ERR_VALUES = "Number of values must be positive.";
	
	/**
	 * Returns true if the supplied array has a
	 * sequence of k consecutive values
	 * 
	 * @param values input array
	 * @param k sequence length for which to search
	 * @return true if values has a consecutive sequence of at least k
	 */
	
	// https://www.geeksforgeeks.org/check-if-array-elements-are-consecutive/
	
	
	public static boolean hasConsecutive(int[] values, int k) {
	
	// if k is less/equal to 0 OR value = 0 OR k > values length
	if(k <= 0 || values.length == 0 || k > values.length) {
		return false;
	}
		// counter variables
		int min = 1;
		int max = 0;
		
		// for loop for the array
		// initialized at 1 because we need to start at 2
		for(int i = 1; i < values.length; i++) {
			// if they're equal, then increase the minimum
			if(values[i] == values[i - 1]) {
				min++;
			} else if(min > max){
				 { 
				// if the minimum is greater than the max
				// it will save into max
					max = min;
				}
				// reloops and resets
					min = 1;
			}
		}
		
		if( k <= min || max >= k) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the length of the longest
	 * consecutive sequence in the supplied
	 * array
	 * 
	 * @param values input array
	 * @return length of the longest consecutive value sequence in values
	 */
	public static int maxConsecutive(int[] values) {
		// Hint: hasConsecutive could
		// be very useful here
		// ?
		int min = 1;
		int max = 0;
		
		if(values.length == 0) {
			return 0;
		}
		// ===================================
		
		for (int i = 1; i < values.length; i++) {
	    	if (values[i] == values[i-1]) {
	    		min++;
	        } else {
	            if (min > max) {
	                max = min;
	            }
	            min = 1;
	        }
	    }
	    if (min > max) {
	        return min;
	    } else {
	        return max;
	    }
	}

	/**
	 * Inputs an array of numbers
	 * and outputs the longest consecutive
	 * sequence of values
	 * 
	 * @param args command-line arguments, ignored
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		
		
		System.out.print("Enter the number of values: ");
		int limit = input.nextInt() ;
			if(limit <= 0) {
				System.out.println(ERR_VALUES);
				System.exit(0);
			}
		
		System.out.print("Enter the values: ");
		int values[] = new int[limit];
		//String values = input.nextLine();
		
		for(int i = 0; i < values.length; i++) {
			values[i] = input.nextInt();
		}
		input.close();
// https://www.quora.com/How-do-I-declare-an-array-of-type-long-int-in-Java-using-a-scanner
		
		System.out.printf("The maximum length of consecutive values is %d.%n", maxConsecutive(values));


		// Hint: useful methods and constants here
		// - maxConsecutive
		// - ERR_VALUES
	}

}
// https://www.youtube.com/watch?v=Yn8KfYOOEuc