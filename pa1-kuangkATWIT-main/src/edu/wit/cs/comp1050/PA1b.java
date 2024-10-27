package edu.wit.cs.comp1050;
import java.util.Scanner;
/**
 *  When it runs it will prompt the user for the amount in coins
 * and give back the total amount in total in the terminal
 * 
 * @author kuangk
 *
 */

public class PA1b {
	
	/**
	 * Error message to display for any non-negative counts 
	 */
	public static final String ERR_MSG = "All coin counts must be non-negative!";

	/**
	 * Prompts the user for a count of quarters, dimes, nickels, and pennies
	 * Outputs the total amount in dollars and cents
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int quarters, dimes, nickels, pennies; // initializes the variables
		double total;
		
		// prompts the user
		System.out.print("Enter number of quarters: ");
		quarters = input.nextInt();

		System.out.print("Enter number of dimes: ");
		dimes = input.nextInt();
		
		System.out.print("Enter number of nickels: ");
		nickels = input.nextInt();
		
		System.out.print("Enter number of pennies: ");
		pennies = input.nextInt();
		input.close();
		
		total = (0.25 * quarters) + (0.10 * dimes) + (0.05 * nickels) + (0.01 * pennies);
		if (quarters < 0) {
			System.out.println(ERR_MSG);}
		else if (dimes < 0) {
			System.out.println(ERR_MSG);}
		else if (nickels < 0) {
			System.out.println(ERR_MSG);}
		else if (pennies < 0) {
			System.out.println(ERR_MSG);}
		else
		System.out.printf("You have $%.2f in coins.%n", total);
		
	}

}
