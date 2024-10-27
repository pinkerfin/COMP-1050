package edu.wit.cs.comp1050;

/**
 * 
 * Takes the parameters (a-f) via command-line arguments, validates the 
 * input, and outputs either an error (due to invalid inputs or a 
 * non-solvable system) or the solution (with three decimal places of precision)
 * 
 * @author kuangk
 *
 */
public class PA3b {
	
	/**
	 * Error to display if the command-line arguments are invalid
	 */
	public static final String ERR_USAGE = "Please supply 6 numbers (a-f).";
	
	/**
	 * Error to display if the equation has no solution
	 */
	public static final String ERR_NOSLTN = "The equation has no solution.";
	
	/**
	 * Number of required parameters (a-f)
	 */
	public static final int NUM_PARAMS = 6;
	
	/**
	 * Validates command-line arguments and returns
	 * parameters if valid
	 * 
	 * @param args command-line arguments
	 * @return if valid an array of parameters, else null
	 */
	public static double[] validateArgs(String[] args) {
// 	added try and catch blocks. without it; kept failing tests
		try {
			if (args.length == 6) {
				double[] params = new double[6];
				for (int i=0; i<params.length; i++) {
					params[i] = Double.valueOf(args[i]);
				} return params;
			} } catch (NumberFormatException e) {
		} return null; }
	/**
	 * Uses command-line arguments to create 
	 * an instance of the linear equation,
	 * and reports the outcome
	 * 
	 * @param args command-line arguments, interpreted as equation parameters
	 */
	public static void main(String[] args) {
		double[] param = validateArgs(args);
		//LinearEquation Linear = new LinearEquation(param);
		
		if(param == null) {
		//	System.out.println(ERR_USAGE);
			System.out.printf("%s%n", ERR_USAGE);
			System.exit(0);
		}
		
		// i really just spent hours trying to figure out what was wrong and it was because
		// this line was in the wrong place
		LinearEquation Linear = new LinearEquation(param); // calls the class
		
		if(Linear.isSolvable()) {
			System.out.printf("Solution: x=%.3f, y=%.3f%n", Linear.getX(), Linear.getY());
		} else {
			System.out.printf("%s%n", ERR_NOSLTN);
		}
		
	} // end of main
}