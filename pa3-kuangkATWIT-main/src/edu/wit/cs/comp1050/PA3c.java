package edu.wit.cs.comp1050;

/**
 * A program that takes a single point via command-line arguments
 * and computes the distance from the origin
 * @author kuangk
 *
 */
public class PA3c {
	
	/**
	 * Error to display if the command-line arguments are invalid
	 */
	public static final String ERR_USAGE = "Please supply 2 numbers (x y).";
	
	
	/**
	 * Method to make sure the points can be used.
	 * Changes the main String args into doubles
	 * 
	 */
	public static double[] check(String[] args){
		try {
			if(args.length == 2) {
				double[] points = new double[2]; // initializes 2 spaces (0 and 1)
				
				for(int i = 0; i < points.length; i++) {
					points[i] = Double.valueOf(args[i]);
				}
				return points; // will return as a double rather than a string
			} 
		}
		catch(NumberFormatException e) {
		} return null;
	}
		
	
	/**
	 * Computes the distance using three methods
	 * from the origin to a point supplied via
	 * command-line arguments
	 * 
	 * @param args command-line args: x y
	 */
	public static void main(String[] args) {
//		double[] place = check(args); // sends the arg info over to check() method
//		if(place == null) {
//			System.out.println(ERR_USAGE);
//		} 
		
		double[] place = check(args);
		if (place == null) {
			System.out.println(ERR_USAGE);
			System.exit(0);
		}
	// WHATS THE DIFFERENCE BETWEEN MINE AND THE SOLUTION'S VERSION? Why does mine fail :(
	// nvm it was the System.exit

		
//		if(args.length < 2) {
//			System.out.println(ERR_USAGE);
//		} else if(args.length == 2) {
//			double[] points= new double[2];
//		}
		
		// replace the following line
		// with whatever code you see fit
		// in order to validate command-line
		// arguments and, if valid, construct
		// p via the two doubles supplied
// ===================================================
//		if(points == null) {
//			System.out.println(ERR_USAGE);
//			System.exit(0);
//		}
		
		final Point2D p = new Point2D(place[0], place[1]); // this one line is making me fail the junit
		
		//////////////////////////////////////
		// !! DO NOT CHANGE THE LINES BELOW !!
		// 
		// They assume p has been
		// properly constructed and perform
		// all necessary output for the
		// program
		//////////////////////////////////////
		final Point2D o = new Point2D();
		System.out.printf("Point 1: %s%n", o);
		System.out.printf("Point 2: %s%n", p);
		System.out.printf("Static method distance: %.3f%n", Point2D.distance(o, p));
		System.out.printf("Distance from P1: %.3f%n", o.distanceTo(p));
		System.out.printf("Distance from P2: %.3f%n", p.distanceTo(o));
	}

}
