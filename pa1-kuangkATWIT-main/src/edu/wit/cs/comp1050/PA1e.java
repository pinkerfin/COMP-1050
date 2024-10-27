package edu.wit.cs.comp1050;
import java.util.Scanner;

/**
 * 
 * Prompts the user to enter a string and
 * displays the number of upper case letters
 * used in the terminal
 * 
 * @author kuangk
 *
 */
public class PA1e {
	
	/**
	 * Counts the number of upper case characters
	 * within the supplied string
	 * 
	 * @param s input string
	 * @return number of upper case characters
	 */
	public static int numUpperCase(String s) {
		int uppercase = 0;
		for(int i = 0; i < s.length(); i++) {
		if(Character.isUpperCase(s.charAt(i))) {
			uppercase++;
		}
	 }
		return uppercase;
	}

	/**
	 * Prompts the user for input
	 * Outputs the amount of upper case characters in string
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a string: ");
		String message = input.nextLine();
		input.close();
		
		if (numUpperCase(message) == 1) {
		System.out.printf("There is 1 uppercase character in the string.%n");
		} else if(numUpperCase(message) == 0) {
			System.out.printf("There are no uppercase characters.%n");
		} else
		System.out.printf("There are %d uppercase characters in the string.%n", (numUpperCase(message)));
		
	}

}
