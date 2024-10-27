package edu.wit.cs.comp1050;
import java.util.Scanner;
/**
 * Prompts the user to enter the weight of the package
 * and displays the shipping cost in the terminal
 * 
 * @author kuangk
 *
 */
public class PA1d {
	
	/**
	 * Error message to display for negative amount 
	 */
	public static final String ERR_MSG = "The package cannot be shipped!";
	
	/**
	 * Method to compute shipping cost
	 * 
	 * @param weight, assumed to be in (0, 20]
	 * @return cost to ship
	 */
	public static double shippingCost(double weight) {
		@SuppressWarnings("unused")
		double total;
		
		if(weight > 0 && weight <= 1) {
			return total = 3.50; //* weight;
		} else if (weight > 1 && weight <= 3) {
			return total = 5.50; // * weight;
		} else if (weight > 3 && weight <= 10) {
			return total = 8.50; // * weight;
		} else // if (weight > 10 && weight <= 20) {
			return total = 10.50; // * weight;
		//} else return total;
	}

	/**
	 * Prompts the user for the weight
	 * Prints the information to the terminal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	Scanner input = new Scanner(System.in);
	
	System.out.print("Enter package weight: ");
	double weight = input.nextDouble();
	
	input.close();
	
	if(weight > 20 || weight <= 0) {
		System.out.println(ERR_MSG);
		System.exit(0);
	} else {
	System.out.printf("It will cost $%.2f to ship this package.%n", shippingCost(weight));
		
		} // end of else
	} // end of main
} // end of class
