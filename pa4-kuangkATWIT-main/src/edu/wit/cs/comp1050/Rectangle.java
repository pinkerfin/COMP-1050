package edu.wit.cs.comp1050;

/**
 * rectangle
 * 
 * @author kuangk
 *
 */
public class Rectangle extends Shape2D {
    // think about how you want to store the vertices of this rectangle
    // hint: an array might make sense
    
	private final Point2D ll; Point2D ur; Point2D c; // lower-left, upper-right, center
	private final Point2D[] v;
	private final double a, p;
	
	/**
	 * Constructs a rectangle given two points
	 * 
	 * @param color rectangle color
	 * @param p1 point 1
	 * @param p2 point 2
	 */
	public Rectangle(String color, Point2D p1, Point2D p2) {
		super(color, "Rectangle"); // replace with your code
		// note: you can't assume that the points p1 and p2 are given to you in any particular order
		
		ll = new Point2D(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
		ur = new Point2D(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));

		final Point2D ul = new Point2D(ll.getX(), ur.getY());
		final Point2D lr = new Point2D(ur.getX(), ll.getY());

		v = new Point2D[] {ll, ul, ur, lr};
		
		final double l = ll.distanceTo(lr);
		final double h = ll.distanceTo(ul);
		
		p = 2*l + 2*h;
		a = l*h;
		
		c = new Point2D(ll.getX() + 0.5*l, ll.getY() + 0.5*h);
	}
	
	/**
	 * Returns true if provided
	 * another rectangle whose 
	 * lower-left and upper-right
	 * points are equal to this
	 * rectangle
	 * 
	 * @param o another object
	 * @return true if the same rectangle
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Rectangle) {
			final Rectangle r = (Rectangle) o;
			return (ll.equals(r.ll) && ur.equals(ur));
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the lower-left corner
	 * 
	 * @return lower-left corner
	 */
	public Point2D getLowerLeft() {
		return ll; 
	}
	
	/**
	 * Gets the upper-right corner
	 * 
	 * @return upper-right corner
	 */
	public Point2D getUpperRight() {
		return ur; 
	}

    // hint: it may help to add a few private methods for distance between the two corners
    
	@Override
	public double getArea() {
		return a; 
	}

	@Override
	public double getPerimeter() {
		return p; 
	}

	@Override
	public Point2D getCenter() {
		return c; 
	}

	@Override
	public Point2D[] getVertices() {
		return v;
	}

}
