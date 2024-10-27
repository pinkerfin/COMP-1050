package edu.wit.cs.comp1050;

import java.util.Scanner;

public class LA2a {
	
	/**
	 * Number of digits in a valid value sequence
	 */
	public static final int SEQ_DIGITS = 10;
	
	/**
	 * Error for an invalid sequence
	 * (not correct number of characters
	 * or not made only of digits)
	 */
	public static final String ERR_SEQ = "Invalid sequence";
	
	/**
	 * Error for an invalid pin
	 * (not made entirely of 
	 * uppercase letters)
	 */
	public static final String ERR_PIN = "Invalid PIN";
	
	/**
	 * Converts an uppercase letter
	 * to the corresponding number on
	 * a standard phone
	 * 
	 * 1:
	 * 2: ABC
	 * 3: DEF
	 * 
	 * 4: GHI
	 * 5: JKL
	 * 6: MNO
	 * 
	 * 7: PQRS
	 * 8: TUV
	 * 9: WXYZ
	 * 
	 * *:
	 * 0: +
	 * #:
	 * 
	 * @param c assumed to be upper case letter
	 * @return corresponding phone number
	 */
	public static int letterToPhone(char c) {
		if (c == 'A' || c == 'B' || c == 'C') {
			return 2;
		}
		if (c == 'D' || c == 'E' || c == 'F') {
			return 3;
		}
		if (c == 'G' || c == 'H' || c == 'I') {
			return 4;
		}
		if (c == 'J' || c == 'K' || c == 'L') {
			return 5;
		}
		if (c == 'M' || c == 'N' || c == 'O') {
			return 6;
		}
		if (c == 'P' || c == 'Q' || c == 'R' || c == 'S') {
			return 7;
		}
		if (c == 'T' || c == 'U' || c == 'V') {
			return 8;
		}
		if (c == 'W' || c == 'X' || c == 'Y' || c == 'Z') {
			return 9;
		} 
		else if(c == '+') {
			return 0;
		}
		return 0;
	}
	
	/**
	 * Takes an input PIN and produces a response
	 * via first converting each character via phone
	 * number, then using that as an index
	 * to a value sequence
	 * 
	 * @param pin input PIN (assumed to be uppercase letters)
	 * @param values input value sequence
	 * @return response
	 */
	public static int[] getResponse(String pin, int[] values) {
		int[] response = new int[pin.length()];
		// write your code here meow
		// hint: you'll need letterToPhone() miou
		for(int i = 0; i < pin.length(); i++) { // for all the pin numbers
			response[i] = values[letterToPhone(pin.charAt(i))]; // stores it back into the int array
			
		}
		
		return response;
	}
	
	/**
	 * Returns true if the length of the
	 * input string is equal to the k
	 * parameter
	 * 
	 * @param s input string
	 * @param k length to check
	 * @return true if length of string 
	 */
	public static boolean stringIsKDigits(String s, int k) {
		return (s.length() == k);
	}
	
	/**
	 * Returns true if all the characters
	 * in the input string are '0' through '9'
	 * 
	 * @param s input string
	 * @return true if all characters in s are digits
	 */
	public static boolean allDigits(String s) {
		// hint: the Character class has a good method to test if one char is a digit
		for(int i = 0; i < s.length(); i++) {
			if(Character.isDigit(s.charAt(i)) == false) {
				return false; // meowowwww
			}
		}
		return true;
	}
	
	/**
	 * Returns true if all the characters
	 * in the input string are 'A' through 'Z'
	 * 
	 * @param s input string
	 * @return true if all characters are uppercase letters
	 */
	public static boolean allUppercaseLetters(String s) {
		// hint: the Character class has a good method to test if one char is an uppercase letter
		for(int i = 0; i < s.length(); i++) {
			if(Character.isUpperCase(s.charAt(i)) == false) {
				return false;
			}
		}
		return true; // replace with your code
	}
	
	/**
	 * Converts a string of digits to an
	 * array of integers (e.g. "12" -> {1, 2})
	 * 
	 * Assumes string is comprised of only digits!
	 * 
	 * @param s digit string
	 * @return corresponding integer array
	 */
	public static int[] digitStringToIntArray(String s) {
		// hint: the Character class has a good method to convert a char into an int
		int digitArray[] = new int[s.length()];
			for(int i = 0; i < s.length(); i++) {
			digitArray[i] = Character.getNumericValue(s.charAt(i));
		}
		
		return digitArray; // replace with your code
	}
	
	/**
	 * Returns how many times a value
	 * occurs within an array
	 * 
	 * @param value needle
	 * @param values haystack
	 * @return how many times the needle occurs in the haystack
	 */
	public static int countValues(int value, int[] values) {
		int needle = 0;
			for(int i = 0; i < values.length; i++) {
				if(values[i] == value) {
					needle++;
				}
			}
		
		return needle; // replace with your code
	}
	
	/**
	 * Returns how many ways the response could have been
	 * produced from a given value sequence
	 * 
	 * @param response output
	 * @param values value sequence
	 * @return how many PINs could have produced the same response given the value sequence
	 */
	public static int numPossible(int[] response, int[] values) {
		int amount = 1;
		for(int i = 0; i < response.length; i++) {
			amount = (amount * countValues(response[i], values));
		}
	
		return amount; // replace with your code
	}
	
	/**
	 * Inputs a value sequence and PIN,
	 * outputs challenge response
	 * 
	 * @param args command-line arguments, ignored
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		final Scanner in = new Scanner(System.in);
		
		System.out.printf("Enter value sequence: "); //User types in numbers in a sequence
		final String seq = in.nextLine();
		
		System.out.printf("Enter PIN: "); //User enters their normal PIN
		final String pin = in.nextLine();
				
		// hint: call stringIsKDigits(), allDigits(), allUppercaseLetters() to do error checking
		if(stringIsKDigits(seq, SEQ_DIGITS) == false || allDigits(seq) == false) { // if it fails the tests
			System.out.println(ERR_SEQ); // stupid sequence dumb fart poooppie peepee 
		}
		else if(allUppercaseLetters(pin) == false) { //pin is invalid
			System.out.println(ERR_PIN);
		// and be sure to use the ERR_SEQ, ERR_PIN, and SEQ_DIGITS final variables when appropriate

		} else {
			// hint: then use digitStringToIntArray() to convert the sequence into an int[]
			int values[] = digitStringToIntArray(seq);
			// hint: then call getResponse() to convert the PIN into the response using the sequence
			int response[] = getResponse(pin, values);
			
			System.out.print("Response: ");
			for (int i = 0; i < response.length; i++) {
				System.out.print(response[i]); // Gives back the converted pin
			}
				System.out.println();
		}
	}
}
