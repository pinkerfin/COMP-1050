package edu.wit.cs.comp1050;

/**
 * 
 * detect points that represent the same location,
 * within a distance threshold.
 * 
 * @author kuangk
 *
 */
public class Point2D {
	final private double x, y;
	
	
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
	 * Get the x coordinate
	 * 
	 * @return x coordinate
	 */
	public double getX() {
		return x; 
	}
	
	/**
	 * Get the y coordinate
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return y; 
	}
	
	/**
	 * Gets a String representation
	 * of the coordinate in the form
	 * "(x, y)" (each with three decimal
	 * places of precision)
	 * 
	 * @return "(x, y)"
	 */
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x, y); 
	}
	
	/**
	 * Returns true if provided another
	 * point that is at the same (x,y)
	 * location (that is, the distance
	 * is "close enough" according to
	 * Shape2D)
	 * 
	 * @param o another object
	 * @return true if the other object is a point and the same location (within threshold)
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Point2D) {
			final Point2D p = (Point2D) o;
			return Shape2D.closeEnough(0.00, distanceTo(p));
		} else {
			return false;
		}
	}
	
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
		final double rise = (p1.y - p2.y);
		final double run = (p1.x - p2.x);
		
		return Math.sqrt(run*run + rise*rise);
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
		return distance(this, p); // MUST call distance above with this point
	}

}
