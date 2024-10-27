package edu.wit.cs.comp1050;

/**
 * 
 * write a program that will output colored 
 * shape vertices in CSV format (comma-separated value)
 * 
 * 
 * @author kuangk
 *
 */
public class PA4b {
	
	/**
	 * Error if incorrect command-line arguments are supplied
	 */
	public static final String ERR_USAGE = "Please supply correct inputs: color x1 y1 x2 y2 x3 y3";
	
	/**
	 * Number of command-line arguments
	 */
	public static final int NUM_ARGS = 7;
	
	// hint: consider adding a validateArgs() method like we had in PA3 to make main() simpler
	private static String validateArgs(String[] args, double[] p) {
		if(args.length == 7) {
			try {
				for(int i = 0; i < p.length; i++) {
					p[i] = Double.parseDouble(args[1+i]);
				}
				return args[0];
			}catch (Exception e) {
			}
		}	return null;
	}
	
	/**
	 * Produces a string representing
	 * all vertex information in CSV
	 * format:
	 * "color",x,y
	 * 
	 * For all shape vertices,
	 * including axis-aligned bounding
	 * boxes for any included triangles
	 * 
	 * @param shapes array of shapes
	 * @return string of CSV information
	 */
	public static String shapeVertices(Shape2D[] shapes) {
		//return ""; // replace with your code (use a StringBuilder for efficiency!)
		final StringBuilder sb = new StringBuilder();
		final String n1 = String.format("%n");
		boolean first = true;
		
		// https://stackoverflow.com/questions/14374707/what-does-the-colon-mean-in-java
		for(Shape2D s : shapes) {
			for(Point2D p : s.getVertices()) {
				if(!first) sb.append(n1);
				first = false;
				sb.append(pointCSV(s.getColor(), p));
			}
			
			if(s instanceof Triangle) {
				final Rectangle rect = ((Triangle) s).getAxisAlignedBoundingBox();
				for(Point2D p : rect.getVertices()) {
					if(!first) {
						sb.append(n1);
						sb.append(pointCSV(rect.getColor(), p));
					}
				}
			}
		}	return sb.toString();
	}

	private static String pointCSV(String color, Point2D point) {
		return String.format("\"%s\",%.3f,%.3f", color, point.getX(), point.getY());
	}

	/**
	 * Outputs vertex information in CSV
	 * format about the triangle supplied
	 * via command-line arguments, including
	 * its axis-aligned bounding box
	 * 
	 * @param args command-line arguments: color x1 y1 x2 y2 x3 y3
	 */
	public static void main(String[] args) {
		final double[] points = new double[NUM_ARGS-1];
		
		final String color = validateArgs(args, points);
		if(color == null) {
			System.out.println(ERR_USAGE);
			System.exit(0);
		}
		
		final Triangle tri = new Triangle(color, new Point2D(points[0], points [1]), new Point2D(points [2], points[3]), 
				new Point2D(points[4], points[5]));
		System.out.println(shapeVertices(new Shape2D[] {tri}));
	}

}
