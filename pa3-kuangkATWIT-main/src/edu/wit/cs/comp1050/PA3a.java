package edu.wit.cs.comp1050;

/**
 * 
 * Program to analyze an arbitrary number of 
 * strings passed as arguments on the command line
 * 
 * @author kuangk
 *
 */
public class PA3a {
	
	/**
	 * Error to output if no command-line arguments supplied
	 */
	public static final String ERR_USAGE = "Please supply at least one argument at the command line."; // DO NOT CHANGE
	
	/**
	 * Opening quote character
	 */
	public static final char QUOTE_CHAR_OPEN = '<'; // DO NOT CHANGE
	
	/**
	 * Closing quote character
	 */
	public static final char QUOTE_CHAR_CLOSE = '>'; // DO NOT CHANGE
	
	
	/**
	 * Obtains the shortest/longest of the list
	 * 
	 * @param a input string array (assumed to be non-empty)
	 * @param shortest true if shortest, else longest
	 * @return length in a that is the shortest/longest
	 */
	private static int findStringLength(String[] a, boolean shortest) {
		int result = a[0].length(); // Initializes 
		for (int i = 1; i < a.length; i++) { // Loops every time the length of a is less than 1
			final int input = a[i].length(); // Initializes the input, which is the length of the array elements
			if ((shortest && input < result) || (!shortest && input > result)) {
	// 		if the shortest = true and the input is LESS than the result OR if it's NOT the shortest and the input is GREATER than the result
				result = input; // overwrites it if it is the shortest
			}
		}
		return result;
	}
	// Structure/idea taken from the solution. I wasn't able to pass the 
	// tests because of the find shortest/longest methods
	// You can still find the remnants of my fail code....
	
	
	/**
	 * Finds an element in a non-empty
	 * array that has minimal length.
	 * If first is true, the first such;
	 * otherwise the last.
	 * 
	 * @param a input array
	 * @param first if true, return the first minimal-length element; otherwise the last
	 * @return element in a that has minimal length
	 */
	private static String findShortest(String[] a, boolean first) {
		return (a.length==0)?null:findOfLength(a, findStringLength(a, true), first);
		// uses the findStringLength method. If it's true, then it's the shortest
		
//		String shortest = a[0];
//		if(a == null || a.length < 1) {
//			return null;
//		}
//		for(int i = 1; i < a.length; i++) {
//			if(a[i].length() < shortest.length()) {
//				shortest = a[i];
//			}
//		}
//		 return shortest;
	} 
	
	/**
	 * Finds the first of the elements of a
	 * supplied array that have the minimal
	 * length
	 * 
	 * @param a input array (assumed non-empty)
	 * @return first element in a that has minimal length
	 */
	public static String firstShortestElement(String[] a) {
		//////////////////////////////////
		// !! DO NOT CHANGE THIS METHOD !!
		//////////////////////////////////
		return findShortest(a, true);
	}
	
	/**
	 * Finds the last of the elements of a
	 * supplied array that have the minimal
	 * length
	 * 
	 * @param a input array (assumed non-empty)
	 * @return last element in a that has minimal length
	 */
	public static String lastShortestElement(String[] a) {
		//////////////////////////////////
		// !! DO NOT CHANGE THIS METHOD !!
		//////////////////////////////////
		return findShortest(a, false);
	}
	
	/**
	 * Finds an element in a non-empty
	 * array that has maximal length.
	 * If first is true, the first such;
	 * otherwise the last.
	 * 
	 * @param a input array
	 * @param first if true, return the first maximal-length element; otherwise the last
	 * @return element in a that has maximal length
	 */
	private static String findLongest(String[] a, boolean first) {
		
		return (a.length==0)?null:findOfLength(a, findStringLength(a, false), first);	
// 		uses the findStringLength method. If it's false, then it's the longest

//		int max = 0; 
//		int length = a[0].length();
//		for(int i=1; i< a.length; i++) {
//		    if(a[i].length() > length) {
//		        max = i; length = a[i].length();
//		    }
//		}
//		return a[max];
	} 
	
	/**
	 * Finds the first of the elements of a
	 * supplied array that have the maximal
	 * length
	 * 
	 * @param a input array (assumed non-empty)
	 * @return first element in a that has maximal length
	 */
	public static String firstLongestElement(String[] a) {
		//////////////////////////////////
		// !! DO NOT CHANGE THIS METHOD !!
		//////////////////////////////////
		return findLongest(a, true);
	}
	
	/**
	 * Finds the last of the elements of a
	 * supplied array that have the maximal
	 * length
	 * 
	 * @param a input array (assumed non-empty)
	 * @return last element in a that has maximal length
	 */
	public static String lastLongestElement(String[] a) {
		//////////////////////////////////
		// !! DO NOT CHANGE THIS METHOD !!
		//////////////////////////////////
		return findLongest(a, false);
	}
	
	/**
	 * Returns an element with a supplied length
	 * (if it exists, else null). If first is true,
	 * the first such element; else the last.
	 * 
	 * @param a input array
	 * @param len length of element for which to search
	 * @param first if true searches for first; else searches for last
	 * @return element with length len in a according to first
	 */
	private static String findOfLength(String[] a, int len, boolean first) {
		int start, end, inc;
		
		if(first) {
			start = 0;
			end = a.length;
			inc = 1;
		} else {
			start = a.length - 1;
			end = -1;
			inc = -1;
		}
		
		for(int i = start; i!= end; i+=inc) {
			String length = a[i];
			if(length.length() == len) {
			return length;
			}
		}
	//	for (int i = 0; i < a.length; i++) {
	//		String length = a[i];
	//		if(length.length() == len) {
	//			return length;
	//		}
	//	}
		return null;
	}
	
	/**
	 * Returns the first element of a
	 * supplied array that has the
	 * supplied length exactly, or
	 * null if not found
	 * 
	 * @param a input array
	 * @param len length of element for which to search
	 * @return first element with length len, or null if not found
	 */
	public static String firstOfLength(String[] a, int len) {
		//////////////////////////////////
		// !! DO NOT CHANGE THIS METHOD !!
		//////////////////////////////////
		return findOfLength(a, len, true);
	}
	
	/**
	 * Returns the last element of a
	 * supplied array that has the
	 * supplied length exactly, or
	 * null if not found
	 * 
	 * @param a input array
	 * @param len length of element for which to search
	 * @return last element with length len, or null if not found
	 */
	public static String lastOfLength(String[] a, int len) {
		//////////////////////////////////
		// !! DO NOT CHANGE THIS METHOD !!
		//////////////////////////////////
		return findOfLength(a, len, false);
	}
	
	/**
	 * Returns the sum of the lengths
	 * of all the elements in the
	 * supplied string array
	 * 
	 * @param a input array
	 * @return summation over the length of all strings in a
	 */
	public static int sumOfElementLengths(String[] a) {
		int sum = 0;
		for(int i = 0; i < a.length; i++) {
			sum += a[i].length();
		}
		return sum;
	}
	
	/**
	 * Given a sum and count, returns
	 * the average as an integer
	 * naturally rounded
	 * 
	 * @param sum elements summed
	 * @param count count of elements
	 * @return rounded average of sum and count
	 */
	public static int averageAsInt(int sum, int count) {
		return (int) Math.round((double) sum / count);
	}
	
	/**
	 * Returns true if the supplied array
	 * has two elements with the same contents
	 * (not necessarily same identity)
	 * 
	 * @param a array to examine
	 * @return true if a contains duplicate elements
	 */
// https://javarevisited.blogspot.com/2015/06/3-ways-to-find-duplicate-elements-in-array-java.html
	public static boolean hasDuplicates(String[] a) {
		for(int i = 0; i < a.length; i++) {
			for(int j = i + 1; j < a.length; j++) {
				if(a[i].equals(a[j])) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Utility method to quote a supplied 
	 * string using supplied open/close 
	 * characters
	 * 
	 * @param s string to quote
	 * @param cOpen character before the string
	 * @param cClose character after the string
	 * @return quoted string
	 */
	public static String flexibleQuote(String s, char cOpen, char cClose) {
		//	String thingy = String.format("%c%s%c", cOpen, s, cClose);
		//	return thingy; 
		return String.format("%c%s%c", cOpen, s, cClose);
	}
	
	/**
	 * Utility method for printing the first and last
	 * Strings from a list. Rules:
	 * - If both are null, return "none"
	 * - If either are null, return the other (quoted)
	 * - If the same string is provided twice (identity, not content); return either (quoted)
	 * - Otherwise return "first=quoted(sFirst), last=quoted(sLast)"
	 * 
	 * Characters for quoting are supplied as arguments
	 * 
	 * @param sFirst first string
	 * @param sLast last string
	 * @param cOpen open character for quoting
	 * @param cClose close character for quoting
	 * @return string according to the rules above
	 */
	public static String oneOrBothOrNone(String sFirst, String sLast, char cOpen, char cClose) {
		if(sFirst == null && sLast == null) {
			return "none";
		}
		
		else if(sFirst == null) {
			return flexibleQuote(sLast, cOpen, cClose);
		}
		//else if(sFirst == sLast || sLast == null) {
		//	return flexibleQuote(sFirst, cOpen, cClose);
		
		else if(sLast == null) {
			return flexibleQuote(sFirst, cOpen, cClose);
		}
		else if(sFirst == sLast) {
			return flexibleQuote(sFirst, cOpen, cClose);
		}
		else {
			return String.format("first=%s, last=%s", flexibleQuote(sFirst, cOpen, cClose), flexibleQuote(sLast, cOpen, cClose));
		}
	}
	
	/**
	 * Utility method to print (to the terminal) 
	 * an array of Strings given something before 
	 * the list, something after, and something 
	 * between each element
	 * 
	 * @param a elements
	 * @param sBefore what to print before the list 
	 * @param sSep what to print between each element
	 * @param sAfter what to print after the list
	 */
	public static void printWithSeparator(String[] a, String sBefore, String sSep, String sAfter) {
		System.out.printf("%s", sBefore);
		if(a.length > 0) {
			System.out.printf(a[0]);
			for(int i = 1; i < a.length; i++) {
				System.out.printf("%s%s", sSep, a[i]);
			}
		}
		System.out.printf("%s", sAfter);
	}
	
	/**
	 * Returns true if command-line arguments
	 * were supplied; false otherwise
	 * 
	 * @param args program arguments
	 * @return true if input is of non-zero length
	 */
	public static boolean validArgs(String[] args) {
		return(args.length > 0);
	}

	/**
	 * Outputs a small analysis of strings
	 * supplied as command-line arguments
	 * to the program
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		//////////////////////////////////
		// !! DO NOT CHANGE THIS METHOD !!
		//////////////////////////////////
		
		if (!validArgs(args)) {
			System.out.printf("%s%n", ERR_USAGE);
			System.exit(1);
		}
		
		//
		
		System.out.printf("Arguments (%d, %s duplication):", args.length, (hasDuplicates(args))?("has"):("no"));
		printWithSeparator(args, " ", " ", String.format("%n"));
		
		//
		
		final int lSum = sumOfElementLengths(args);
		final int lAvg = averageAsInt(lSum, args.length);
		System.out.printf("Length: total=%d, avg=%d%n", lSum, lAvg);
		
		//
		
		final String fShortest = firstShortestElement(args);
		final String lShortest = lastShortestElement(args);
		System.out.printf("Shortest (%d): %s%n", fShortest.length(), oneOrBothOrNone(fShortest, lShortest, QUOTE_CHAR_OPEN, QUOTE_CHAR_CLOSE));
		
		//
		
		final String fLongest = firstLongestElement(args);
		final String lLongest = lastLongestElement(args);
		System.out.printf("Longest (%d): %s%n", fLongest.length(), oneOrBothOrNone(fLongest, lLongest, QUOTE_CHAR_OPEN, QUOTE_CHAR_CLOSE));
		
		//
		
		final String fAverage = firstOfLength(args, lAvg);
		final String lAverage = lastOfLength(args, lAvg);
		System.out.printf("Average (%d): %s%n", lAvg, oneOrBothOrNone(fAverage, lAverage, QUOTE_CHAR_OPEN, QUOTE_CHAR_CLOSE));
	}

}
