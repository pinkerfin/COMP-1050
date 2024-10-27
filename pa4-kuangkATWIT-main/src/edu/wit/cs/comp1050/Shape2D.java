package edu.wit.cs.comp1050;

/**
 * Abstract class representing
 * a two-dimensional colored
 * shape
 * 
 * DO NOT CHANGE ANY CODE IN THIS FILE
 * 
 * @author Charlie Wiseman
 */
public abstract class Shape2D {
	
	final private String color;
	final private String shapeName;
	
	final public static double THRESH = 1.0E-5;
	
	/**
	 * Returns true if two numbers
	 * are considered close enough
	 * 
	 * @param a value 1
	 * @param b value 2
	 * @return true if a and b are considered close enough
	 */
	public static boolean closeEnough(double a, double b) {
		return Math.abs(a-b) <= THRESH;
	}
	
	/**
	 * Constructs the shape
	 * 
	 * @param color shape color
	 * @param shapeName name of the shape (e.g. Triangle, Rectangle, etc.)
	 */
	public Shape2D(String color, String shapeName) {
		this.color = color;
		this.shapeName = shapeName;
	}
	
	/**
	 * Gets the color of the shape
	 * 
	 * @return shape color
	 */
	final public String getColor() {
		return color;
	}
	
	private String vToString() {
		final StringBuilder sb = new StringBuilder();
		
		final Point2D[] v = getVertices();
		if (v.length > 0) {
			sb.append(v[0]);
			
			for (int i=1; i<v.length; i++) {
				sb.append(String.format(", %s", v[i]));
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * A friendly text representation of the shape
	 * 
	 * @return something like "Red Rectangle @ ((0.000, 0.000), (0.000, 1.000), (1.000, 1.000), (1.000, 0.000)): center=(0.000, 0.000), perimeter=4.000, area=1.000"
	 */
	@Override
	final public String toString() {
		return String.format("%s %s @ (%s): center=%s, perimeter=%.3f, area=%.3f", 
				color, shapeName, vToString(), getCenter(), getPerimeter(), getArea());
	}
	
	/**
	 * Gets the area of the shape
	 * 
	 * @return area
	 */
	public abstract double getArea();
	
	/**
	 * Gets the perimeter of the shape
	 * 
	 * @return perimeter
	 */
	public abstract double getPerimeter();
	
	/**
	 * Gets the center of the shape
	 * 
	 * @return center of the shape
	 */
	public abstract Point2D getCenter();
	
	/**
	 * Gets the vertices of the shape
	 * 
	 * @return shape vertices
	 */
	public abstract Point2D[] getVertices();

}
