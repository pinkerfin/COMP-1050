package edu.wit.cs.comp1050;

/** 
 * Class to store a coordinate pair (x,y) and 
 * to compute Euclidean/L2 distances between
 * points in two-dimensional space
 * 
 * @author kuangk
 *
 */
public class Point2D {
	final double x, y;
	/**
	 * Constructor to initialize coordinates
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Initializes to (0., 0.)
	 */
	public Point2D() {
		// replace with your code
		// MUST be a single call
		// to the constructor above
		this(0., 0.);
	}
	

	/**
	 * Get the x coordinate
	 * 
	 * @return x coordinate
	 */
	public double getX() {
	//	return 0.; // replace with your code
		return x;
	}
	
	/**
	 * Get the y coordinate
	 * 
	 * @return y coordinate
	 */
	public double getY() {
	//	return 0.; // replace with your code
		return y;
	}
	
	/**
	 * Gets a String representation
	 * of the coordinate in the form
	 * "(x, y)" (each with three decimal
	 * places of precision)
	 * 
	 * #return "(x, y)"
	 */
	public String toString() {
	//	return String.format("(x%.3f, y%.3f%)%n", x, y);
		return String.format("(%.3f, %.3f)", x, y);

	}// https://www.javatpoint.com/java-string-format
	
	/**
	 * Method to compute the Euclidean/L2
	 * distance between two points in 2D
	 * space
	 * 
	 * @param p1 point 1
	 * @param p2 point 2
	 * @return straightline distance between p1 and p2
	 */
	public static double distance(Point2D p1, Point2D p2) {
		// d(p,q)=sqrt( (x1 - y1)^2 + (x2 - y2)^2 )
		double xD = (p1.x - p2.x);
		double yD = (p1.y - p2.y);
		
	//	double d = (xD*xD) + (yD * yD);
		
		return Math.sqrt((xD*xD) + (yD * yD));
	//	return Math.sqrt(d);
	}
	
	/**
	 * Method to compute the Euclidean
	 * distance between this point
	 * and a supplied point
	 * 
	 * @param p input point
	 * @return straightline distance between this point and p
	 */
	public double distanceTo(Point2D p) {
		return distance(this, p);
	}

}
