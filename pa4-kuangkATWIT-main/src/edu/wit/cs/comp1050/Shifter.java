package edu.wit.cs.comp1050;

import java.util.ArrayList;

/**
 * 
 * This class is constructed with the encrypted string, 
 * and then has methods to both shift by an arbitrary amount and 
 * find substrings across all shifts.
 * 
 * @author kuangk
 *
 */

public class Shifter {

	// think about what class variables you will need here
	
	/**
	 * Number of letters in the English alphabet
	 */
	public static final int NUM_LETTERS = ('z' - 'a') + 1;
	
	private final String[] shift = new String[NUM_LETTERS];

	/**
	 * Initializes the shifter
	 * 
	 * @param s encrypted string
	 */
	public Shifter(String s) {
		final StringBuilder sb = new StringBuilder(s);
		
		shift[0] = s;
		for(int i = 1; i < NUM_LETTERS; i++) {
		shift[i] = shift(s, sb, i);
		}
	}
	
	private String shift(String s, StringBuilder sb, int i) {
		for (int j=0; j<s.length(); j++) {
			final char c = s.charAt(j);
			final boolean isLowercase = (c>='a' && c<='z');
			final boolean isUppercase = (c>='A' && c<='Z');
			
			final char r;
			if (isLowercase || isUppercase) {
				final char base = isLowercase?'a':'A';
				
				r = (char) (base + ((NUM_LETTERS + (c - base) + i) % NUM_LETTERS));
			} else {
				r = c;
			}
			sb.setCharAt(j, r);
		}
		return sb.toString();
	}

	
	
	
	/**
	 * Returns the result of shifting
	 * by a supplied amount
	 * 
	 * @param n number of places to shift 
	 * @return shifted string
	 */
	public String shift(int n) {
	    // hint: Math.floorMod() can be very helpful here
//		int x = (NUM_LETTERS + n) % NUM_LETTERS;
//		return shift[x];
		return shift[shiftMod(n)];
		
	}
	
	private int shiftMod(int n) {
		// TODO Auto-generated method stub
		return (NUM_LETTERS + n) % NUM_LETTERS;
	}

	/**
	 * Finds all shifts that contain
	 * the supplied substring
	 * 
	 * @param sub substring to find
	 * @return array of shifts (0-25) that contain the substring (in order)
	 */
	public int[] findShift(String sub) {
		// hint: use shift() and an ArrayList of Integers
		// note that you'll have to convert the ArrayList back into an int[] to return it
	
		ArrayList<Integer> n = new ArrayList<>();
		for(int i = 0; i < NUM_LETTERS; i++) {
			if(shift(i).indexOf(sub) != -1) {
				n.add(i);
			}
		}
		
		int[] ans = new int[n.size()];
		for(int i = 0; i < n.size(); i++) {
			ans[i] = n.get(i);
		}
		
		return ans;
		
		
	}

}
