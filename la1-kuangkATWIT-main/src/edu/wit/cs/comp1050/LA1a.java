package edu.wit.cs.comp1050;
import java.util.Scanner;

public class LA1a {
	
	/**
	 * Returns an array with counts for each
	 * letter from a-z (case-insensitive),
	 * ignoring all other characters:
	 * 
	 * [0]: number of a/A's
	 * [1]: number of b/B's
	 * ...
	 * [25]: number of z/Z's
	 * 
	 * @param word input word
	 * @return count of case-insensitive letters
	 */
	public static int[] countLetters(String word) {
		int[] counts = new int[26];

		
		for (char c : word.toCharArray()) {
			c = Character.toLowerCase(c);
			if (c >= 'a' && c <= 'z') {
				counts[c-'a']++;
			}
		}
		
		return counts; // returns the number of each char
	}
	
	/**
	 * Compares two arrays and
	 * returns true if they have
	 * the same contents
	 * 
	 * @param c1 array 1
	 * @param c2 array 2
	 * @return true if c1 and c2 have the same contents
	 */
	public static boolean sameCounts(int[] c1, int[] c2) {
		
		if (c1.length != c2.length) { //if word1 and word2 are the same length
		return false;
		}
		
		for(int i = 0; i < c1.length; i++) { //i = the char, checks for same char
			if(c1[i] != c2[i]) { // if there is at least one missing letter
				return false;
			}
		}
		
		return true; //passes length and char amount check
	}

	/**
	 * Inputs two phrases and outputs
	 * if they are anagrams (ignoring
	 * case and any non-letter
	 * characters)
	 * 
	 * @param args command-line arguments, ignored
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		
		System.out.print("Enter phrase 1: ");
		String word1 = input.nextLine();
		
		System.out.print("Enter phrase 2: ");
		String word2 = input.nextLine();
		
		input.close();
		
		// count the letters
		int[] count1 = countLetters(word1);
		int[] count2 = countLetters(word2);
		
		boolean result = sameCounts(count1, count2); // throws this into sameCounts()
		
		if (result == true) {
			System.out.println("These phrases are anagrams.");
		} else {
			System.out.println("These phrases are not anagrams.");
			
		}
	}
}
