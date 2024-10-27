package edu.wit.cs.comp1050;

/**
 * 
 * Class for a 2x2 set of linear equations
 * @author kuangk
 *
 */
public class LinearEquation {
	private double a, b, c, d, e, f; //, x, y;
	private boolean solveable;
	private Double x, y;
	/**
	 * Initialize the linear equation of form:
	 * ax + by = e
	 * cx + dy = f
	 * 
	 * @param a parameter a
	 * @param b parameter b
	 * @param c parameter c
	 * @param d parameter d
	 * @param e parameter e
	 * @param f parameter f
	 */
	
	public LinearEquation(double a, double b, double c, double d, double e, double f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		// 'this' is a reference to the current object (LinearEquation)
		
		final double bottom = (a * d - b * c); 
		solveable = (bottom != 0); //because you can't divide by 0
		if (solveable) { // if the denominator isn't a zero
			x = (e * d - b * f) / bottom;
			y = (a * f - e * c) / bottom;
		} else {
			x = null;
			y = null;
		}
//		if(solveable){ // can't divide by zero
//			x = ((e * d) - (b * f)) / bottom; 
//			y = ((a * f) - (e * c)) / bottom;
//			} else {
//			System.exit(0);
//		}
		
	}
	
	/**
	 * Convenience constructor to initialize
	 * the linear equation via array
	 * 
	 * THIS CONSTRUCTOR CALLS THE CONSTRUCTOR
	 * ABOVE USING THE ARRAY CONTENTS
	 * 
	 * @param p parameter array, assumed to be length 6 (a-f, in order)
	 */
	public LinearEquation(double[] p) {
		// MUST call the above constructor
		// with the contents of p
		
//		a = p[0]; b = p[1]; c = p[2]; d = p[3]; e = p[4]; f = p[5];
		
		this(p[0], p[1], p[2], p[3], p[4], p[5]);
		// this already has the parameters of a-f
	}
	
	/**
	 * Returns parameter a
	 * 
	 * @return a
	 */
	public double getA() {
		return a; 
	}
	
	/**
	 * Returns parameter b
	 * 
	 * @return b
	 */
	public double getB() {
		return b;
	}
	
	/**
	 * Returns parameter c
	 * 
	 * @return c
	 */
	public double getC() {
		return c;
	}
	
	/**
	 * Returns parameter d
	 * 
	 * @return d
	 */
	public double getD() {
		return d;
	}
	
	/**
	 * Returns parameter e
	 * 
	 * @return e
	 */
	public double getE() {
		return e; 
	}
	
	/**
	 * Returns parameter f
	 * 
	 * @return f
	 */
	public double getF() {
		return f;
	}
	
	/**
	 * Returns true if the parameterized
	 * equation is solvable (i.e. denominator
	 * ad-bc is not 0)
	 * 
	 * @return true if solvable, false otherwise
	 */
	public boolean isSolvable() {
		return solveable; 
	// already a boolean done in the LinearEquation method
	} 
	
	/**
	 * Returns solution for x if solvable,
	 * null otherwise
	 * 
	 * @return x if solvable, null otherwise
	 */
	public Double getX() {
		return x;
	}
	
	/**
	 * Returns solution for y if solvable,
	 * null otherwise
	 * 
	 * @return y if solvable, null otherwise
	 */
	public Double getY() {
		return y;
	}

}