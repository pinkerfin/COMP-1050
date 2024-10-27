package edu.wit.cs.comp1050;
import java.util.Scanner;

/**
 * When it runs it will ask the user for a amount
 * and will give back the amount of coins needed in the terminal
 * 
 * @author kuangk
 *
 */
public class PA1c {
	
	/**
	 * Error message to display for negative amount 
	 */
	public static final String ERR_MSG = "Dollar amount must be non-negative!";
	
	/**
	 * Method to convert a double to
	 * an integer
	 * 
	 * @param num number to convert
	 * @return converted value
	 */
	public static int convertToInt(double num) {
		return (int) Math.round(num);
	}

	/**
	 * Prompts the user for a dollar amount
	 * Outputs the fewest coins needed
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter total amount: $");
		double amount = input.nextDouble();
		if (amount < 0) {
			System.out.println(ERR_MSG);
			System.exit(0);
		}
		
		// calculations
		int quarters, dimes, nickels, pennies;
		
		amount *=100;
		int total = convertToInt(amount);
		
		quarters = total / 25;
		total%=25; //shorthand (same as total = total % 25)
		
		dimes = total / 10;
		total%=10;
		
		nickels = total / 5;
		total%=5;
		
		pennies = total / 1;

	if (quarters == 1) {
		System.out.printf("You have %d quarter,", convertToInt(quarters));
	} else {
		System.out.printf("You have %d quarters,", convertToInt(quarters));
	}	
		
	if (dimes == 1) {
		System.out.printf(" %d dime,", convertToInt(dimes));
	}else {
		System.out.printf(" %d dimes,", convertToInt(dimes));
	}
	
	if(nickels == 1) {
		System.out.printf(" %d nickel,", convertToInt(nickels));
	} else {
		System.out.printf(" %d nickels,", convertToInt(nickels));

	}
	
	if (pennies == 1) {
		System.out.printf(" and %d penny.%n", convertToInt(pennies));
	} else {
		System.out.printf(" and %d pennies.%n", convertToInt(pennies));
	}		
		
	} // end of main

} // goodness gracious this took me so long because i am unable to do simple math
