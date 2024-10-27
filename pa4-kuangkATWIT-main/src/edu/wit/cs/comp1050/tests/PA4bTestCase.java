
package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.Assert;

import edu.wit.cs.comp1050.PA4b;
import edu.wit.cs.comp1050.Point2D;
import edu.wit.cs.comp1050.Rectangle;
import edu.wit.cs.comp1050.Shape2D;
import edu.wit.cs.comp1050.Triangle;
import junit.framework.TestCase;

public class PA4bTestCase extends TestCase {
	
	private static final String ERR_USAGE = "Please supply correct inputs: color x1 y1 x2 y2 x3 y3";
	
	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	
	private static class NoExitSecurityManager extends SecurityManager 
    {
        @Override
        public void checkPermission(Permission perm) {}
        
        @Override
        public void checkPermission(Permission perm, Object context) {}
        
        @Override
        public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
    }
	
	@Override
    protected void setUp() throws Exception 
    {
        super.setUp();
        System.setSecurityManager(new NoExitSecurityManager());
    }
	
	@Override
    protected void tearDown() throws Exception 
    {
        System.setSecurityManager(null);
        super.tearDown();
    }
	
	private void _test(String[] a, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String expected = TestSuite.stringOutput(new String[] {
			msg + "%n"
		}, new Object[] {});
		
		System.setIn(null);
		System.setOut(new PrintStream(outContent));
		try {
			PA4b.main(a);
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testPoint2D(Point2D p, double x, double y, String eS) {
		assertNotNull(p);
		
		assertEquals(x, p.getX(), Shape2D.THRESH);
		assertEquals(y, p.getY(), Shape2D.THRESH);
		assertEquals(0., p.distanceTo(p), Shape2D.THRESH);
		assertTrue(p.equals(p));
		
		assertEquals(eS, p.toString());
	}
	
	private Point2D _testPoint2D(double x, double y, String eS) {
		Point2D p = null;
		
		try {
			p = new Point2D(x, y);
		} catch (ExitException ex) {}
		
		_testPoint2D(p, x, y, eS);
		
		return p;
	}
	
	private void _testRectangle(Rectangle r, String color, double eA, double eP, String eS, Point2D[] vE, Point2D cE, Point2D llE, Point2D urE) {
		assertNotNull(r);
		assertTrue(r instanceof Shape2D);
		
		assertEquals(color, r.getColor());
		assertEquals(eS, r.toString());
		assertEquals(eA, r.getArea(), Shape2D.THRESH);
		assertEquals(eP, r.getPerimeter(), Shape2D.THRESH);
		Assert.assertArrayEquals(vE, r.getVertices());
		assertEquals(cE, r.getCenter());
		
		assertEquals(llE, r.getLowerLeft());
		assertEquals(urE, r.getUpperRight());
	}
	
	private Rectangle _testRectangle(String color, double x1, double y1, double x2, double y2, double eA, double eP, String eS, Point2D[] vE, Point2D cE, Point2D llE, Point2D urE) {
		Rectangle r1 = null;
		
		try {
			r1 = new Rectangle(color, new Point2D(x1, y1), new Point2D(x2, y2));
		} catch (ExitException ex) {}
		_testRectangle(r1, color, eA, eP, eS, vE, cE, llE, urE);
		
		Rectangle r = null; 
		try {
			r = new Rectangle(color, new Point2D(x1, y2), new Point2D(x2, y1));
		} catch (ExitException ex) {}
		_testRectangle(r, color, eA, eP, eS, vE, cE, llE, urE);
		assertEquals(r1, r);
		
		r = null; 
		try {
			r = new Rectangle(color, new Point2D(x2, y2), new Point2D(x1, y1));
		} catch (ExitException ex) {}
		_testRectangle(r, color, eA, eP, eS, vE, cE, llE, urE);
		assertEquals(r1, r);
		
		r = null; 
		try {
			r = new Rectangle(color, new Point2D(x2, y1), new Point2D(x1, y2));
		} catch (ExitException ex) {}
		_testRectangle(r, color, eA, eP, eS, vE, cE, llE, urE);
		assertEquals(r1, r);
		
		return r1;
	}
	
	private void _testTriangle(Triangle t, String color, double eA, double eP, String eS, Point2D[] eV, Point2D eC, Rectangle eAABB) {
		assertNotNull(t);
		assertTrue(t instanceof Shape2D);
		
		assertEquals(color, t.getColor());
		assertEquals(eA, t.getArea(), Shape2D.THRESH);
		assertEquals(eP, t.getPerimeter(), Shape2D.THRESH);
		assertEquals(eS, t.toString());
		Assert.assertArrayEquals(eV, t.getVertices());
		assertEquals(eC, t.getCenter());
		
		assertEquals(eAABB, t.getAxisAlignedBoundingBox());
	}
	
	private Triangle _testTriangle(String color, double x1, double y1, double x2, double y2, double x3, double y3, double eA, double eP, String eS1, String eS2, Point2D eC, Rectangle eAABB) {
		Triangle t1 = null;
		
		final Point2D p1 = new Point2D(x1, y1);
		final Point2D p2 = new Point2D(x2, y2);
		final Point2D p3 = new Point2D(x3, y3);
		
		try {
			t1 = new Triangle(color, p1, p2, p3);
		} catch (ExitException ex) {}
		_testTriangle(t1, color, eA, eP, (eS1 + " @ (" + p1 + ", " + p2 + ", " + p3 + "): " + eS2), new Point2D[] {p1, p2, p3}, eC, eAABB);
		
		Triangle t = null;
		try {
			t = new Triangle(color, p1, p3, p2);
		} catch (ExitException ex) {}
		_testTriangle(t, color, eA, eP, (eS1 + " @ (" + p1 + ", " + p3 + ", " + p2 + "): " + eS2), new Point2D[] {p1, p3, p2}, eC, eAABB);
		
		t = null;
		try {
			t = new Triangle(color, p2, p1, p3);
		} catch (ExitException ex) {}
		_testTriangle(t, color, eA, eP, (eS1 + " @ (" + p2 + ", " + p1 + ", " + p3 + "): " + eS2), new Point2D[] {p2, p1, p3}, eC, eAABB);
		
		t = null;
		try {
			t = new Triangle(color, p2, p3, p1);
		} catch (ExitException ex) {}
		_testTriangle(t, color, eA, eP, (eS1 + " @ (" + p2 + ", " + p3 + ", " + p1 + "): " + eS2), new Point2D[] {p2, p3, p1}, eC, eAABB);
		
		t = null;
		try {
			t = new Triangle(color, p3, p1, p2);
		} catch (ExitException ex) {}
		_testTriangle(t, color, eA, eP, (eS1 + " @ (" + p3 + ", " + p1 + ", " + p2 + "): " + eS2), new Point2D[] {p3, p1, p2}, eC, eAABB);
		
		t = null;
		try {
			t = new Triangle(color, p3, p2, p1);
		} catch (ExitException ex) {}
		_testTriangle(t, color, eA, eP, (eS1 + " @ (" + p3 + ", " + p2 + ", " + p1 + "): " + eS2), new Point2D[] {p3, p2, p1}, eC, eAABB);
		
		return t1;
	}
	
	private void _testCSV(Shape2D[] a, String[] csv) {
		String result = null;
		try {
			result = PA4b.shapeVertices(a); 
		} catch (ExitException ex) {}
		
		assertEquals(String.format(String.join("%n", csv)), result);
	}
	
	public void testShape2D() {
		assertEquals(1.0E-5, Shape2D.THRESH, 1.0E-10);
		
		assertTrue(Shape2D.closeEnough(0., 0.));
		assertTrue(Shape2D.closeEnough(Shape2D.THRESH, Shape2D.THRESH));
		
		assertTrue(Shape2D.closeEnough(0., Shape2D.THRESH));
		assertTrue(Shape2D.closeEnough(0., -Shape2D.THRESH));
		assertTrue(Shape2D.closeEnough(Shape2D.THRESH, 0));
		assertTrue(Shape2D.closeEnough(-Shape2D.THRESH, 0));
		
		assertTrue(Shape2D.closeEnough(0., Shape2D.THRESH/2));
		assertTrue(Shape2D.closeEnough(0., -Shape2D.THRESH/2));
		
		assertFalse(Shape2D.closeEnough(0., 2*Shape2D.THRESH));
		assertFalse(Shape2D.closeEnough(0., -2*Shape2D.THRESH));
	}
	
	private void _testPoint2D(Point2D p1, Point2D p2, Double d) {
		if (d == null) {
			assertTrue(p1.equals(p2));
			assertTrue(p2.equals(p1));
			assertEquals(0., Point2D.distance(p1, p2), Shape2D.THRESH);
			assertEquals(0., p1.distanceTo(p2), Shape2D.THRESH);
			assertEquals(0., p2.distanceTo(p1), Shape2D.THRESH);
		} else {
			assertFalse(p1.equals(p2));
			assertFalse(p2.equals(p1));
			assertEquals(d, Point2D.distance(p1, p2), Shape2D.THRESH);
			assertEquals(d, p1.distanceTo(p2), Shape2D.THRESH);
			assertEquals(d, p2.distanceTo(p1), Shape2D.THRESH);
		}
	}
	
	public void testPoint2D() {
		final Point2D origin = _testPoint2D(0., 0., "(0.000, 0.000)");
		final Point2D o1 = _testPoint2D(0, Shape2D.THRESH/10., "(0.000, 0.000)");
		final Point2D o2 = _testPoint2D(Shape2D.THRESH/10., 0, "(0.000, 0.000)");
		final Point2D o3 = _testPoint2D(Shape2D.THRESH/10., Shape2D.THRESH/10., "(0.000, 0.000)");
		
		_testPoint2D(origin, origin, null);
		_testPoint2D(origin, o1, null);
		_testPoint2D(origin, o2, null);
		_testPoint2D(origin, o3, null);
		_testPoint2D(o1, o2, null);
		_testPoint2D(o1, o3, null);
		_testPoint2D(o2, o3, null);
		
		//
		
		final Point2D p01a = _testPoint2D(0., 1., "(0.000, 1.000)");
		final Point2D p01b = _testPoint2D(0., 1., "(0.000, 1.000)");
		final Point2D p10 = _testPoint2D(1., 0., "(1.000, 0.000)");
		final Point2D p11 = _testPoint2D(1., 1., "(1.000, 1.000)");
		
		_testPoint2D(origin, p01a, 1.);
		_testPoint2D(origin, p01b, 1.);
		_testPoint2D(origin, p10, 1.);
		_testPoint2D(origin, p11, Math.sqrt(2));
		
		_testPoint2D(p01a, p01b, null);
		_testPoint2D(p01a, p10, Math.sqrt(2));
		_testPoint2D(p01a, p11, 1.);
		
		_testPoint2D(p01b, p10, Math.sqrt(2));
		_testPoint2D(p01b, p11, 1.);
		
		_testPoint2D(p10, p11, 1.);
		
		//
		
		_testPoint2D(-1., -1., "(-1.000, -1.000)");
		_testPoint2D(-1., 2., "(-1.000, 2.000)");
		_testPoint2D(0., 1., "(0.000, 1.000)");
		_testPoint2D(0., 2., "(0.000, 2.000)");
		_testPoint2D(0.5, 1., "(0.500, 1.000)");
		_testPoint2D(1., 2., "(1.000, 2.000)");
		_testPoint2D(1., 0.5, "(1.000, 0.500)");
		_testPoint2D(2., 0., "(2.000, 0.000)");
		_testPoint2D(2., 1., "(2.000, 1.000)");
		_testPoint2D(3., -1., "(3.000, -1.000)");
		_testPoint2D(3., 2., "(3.000, 2.000)");
		
		//
		
		_testPoint2D(-2., 0., "(-2.000, 0.000)");
		_testPoint2D(-2., 5., "(-2.000, 5.000)");
		_testPoint2D(-0.5, 2.5, "(-0.500, 2.500)");
		_testPoint2D(0., 9., "(0.000, 9.000)");
		_testPoint2D(1., 5., "(1.000, 5.000)");
		_testPoint2D(5., 3., "(5.000, 3.000)");
		_testPoint2D(6., 4.5, "(6.000, 4.500)");
		_testPoint2D(12., 0., "(12.000, 0.000)");
		_testPoint2D(12., 9., "(12.000, 9.000)");
		_testPoint2D(-1./3., 10./3., "(-0.333, 3.333)");
	}
	
	public void testRectangle() {
		final Point2D origin = _testPoint2D(0., 0., "(0.000, 0.000)");
		final Point2D p_1_1 = _testPoint2D(-1., -1., "(-1.000, -1.000)");
		final Point2D p_12 = _testPoint2D(-1., 2., "(-1.000, 2.000)");
		final Point2D p01 = _testPoint2D(0., 1., "(0.000, 1.000)");
		final Point2D p02 = _testPoint2D(0., 2., "(0.000, 2.000)");
		final Point2D p051 = _testPoint2D(0.5, 1., "(0.500, 1.000)");
		final Point2D p10 = _testPoint2D(1., 0., "(1.000, 0.000)");
		final Point2D p12 = _testPoint2D(1., 2., "(1.000, 2.000)");
		final Point2D p105 = _testPoint2D(1., 0.5, "(1.000, 0.500)");
		final Point2D p20 = _testPoint2D(2., 0., "(2.000, 0.000)");
		final Point2D p21 = _testPoint2D(2., 1., "(2.000, 1.000)");
		final Point2D p3_1 = _testPoint2D(3., -1., "(3.000, -1.000)");
		final Point2D p32 = _testPoint2D(3., 2., "(3.000, 2.000)");
		
		_testRectangle("Red", 0, 0, 2, 1, 2., 6., 
			"Red Rectangle @ ((0.000, 0.000), (0.000, 1.000), (2.000, 1.000), (2.000, 0.000)): center=(1.000, 0.500), perimeter=6.000, area=2.000", 
			new Point2D[] {origin, p01, p21, p20}, p105, origin, p21);
		
		_testRectangle("Blue", 0, 0, 1, 2, 2., 6., 
			"Blue Rectangle @ ((0.000, 0.000), (0.000, 2.000), (1.000, 2.000), (1.000, 0.000)): center=(0.500, 1.000), perimeter=6.000, area=2.000", 
			new Point2D[] {origin, p02, p12, p10}, p051, origin, p12);
		
		_testRectangle("Purple", -1., 2., 3., -1., 12., 14., 
			"Purple Rectangle @ ((-1.000, -1.000), (-1.000, 2.000), (3.000, 2.000), (3.000, -1.000)): center=(1.000, 0.500), perimeter=14.000, area=12.000", 
			new Point2D[] {p_1_1, p_12, p32, p3_1}, p105, p_1_1, p32);
		
		//
		
		final Point2D p_20 = _testPoint2D(-2., 0., "(-2.000, 0.000)");
		final Point2D p_25 = _testPoint2D(-2., 5., "(-2.000, 5.000)");
		final Point2D p_05205 = _testPoint2D(-0.5, 2.5, "(-0.500, 2.500)");
		final Point2D p09 = _testPoint2D(0., 9., "(0.000, 9.000)");
		final Point2D p15 = _testPoint2D(1., 5., "(1.000, 5.000)");
		_testPoint2D(5., 3., "(5.000, 3.000)");
		final Point2D p6405 = _testPoint2D(6., 4.5, "(6.000, 4.500)");
		final Point2D p120 = _testPoint2D(12., 0., "(12.000, 0.000)");
		final Point2D p129 = _testPoint2D(12., 9., "(12.000, 9.000)");
		_testPoint2D(-1./3., 10./3., "(-0.333, 3.333)");
		
		_testRectangle("White", 0, 0, 12, 9, 108., 42., 
			"White Rectangle @ ((0.000, 0.000), (0.000, 9.000), (12.000, 9.000), (12.000, 0.000)): center=(6.000, 4.500), perimeter=42.000, area=108.000", 
			new Point2D[] {origin, p09, p129, p120}, p6405, origin, p129);
		
		_testRectangle("Yellow", -2, 0, 1, 5, 15., 16., 
			"Yellow Rectangle @ ((-2.000, 0.000), (-2.000, 5.000), (1.000, 5.000), (1.000, 0.000)): center=(-0.500, 2.500), perimeter=16.000, area=15.000", 
			new Point2D[] {p_20, p_25, p15, p10}, p_05205, p_20, p15);
	}
	
	public void testTriangle() {
		final Point2D origin = _testPoint2D(0., 0., "(0.000, 0.000)");
		final Point2D p_20 = _testPoint2D(-2., 0., "(-2.000, 0.000)");
		final Point2D p_25 = _testPoint2D(-2., 5., "(-2.000, 5.000)");
		final Point2D p_05205 = _testPoint2D(-0.5, 2.5, "(-0.500, 2.500)");
		final Point2D p09 = _testPoint2D(0., 9., "(0.000, 9.000)");
		final Point2D p15 = _testPoint2D(1., 5., "(1.000, 5.000)");
		final Point2D p10 = _testPoint2D(1., 0., "(1.000, 0.000)");
		final Point2D p53 = _testPoint2D(5., 3., "(5.000, 3.000)");
		final Point2D p6405 = _testPoint2D(6., 4.5, "(6.000, 4.500)");
		final Point2D p120 = _testPoint2D(12., 0., "(12.000, 0.000)");
		final Point2D p129 = _testPoint2D(12., 9., "(12.000, 9.000)");
		final Point2D pC2 = _testPoint2D(-1./3., 10./3., "(-0.333, 3.333)");
		
		final Rectangle r1 = _testRectangle("White", 0, 0, 12, 9, 108., 42., 
			"White Rectangle @ ((0.000, 0.000), (0.000, 9.000), (12.000, 9.000), (12.000, 0.000)): center=(6.000, 4.500), perimeter=42.000, area=108.000", 
			new Point2D[] {origin, p09, p129, p120}, p6405, origin, p129);
		
		final Rectangle r2 = _testRectangle("Yellow", -2, 0, 1, 5, 15., 16., 
			"Yellow Rectangle @ ((-2.000, 0.000), (-2.000, 5.000), (1.000, 5.000), (1.000, 0.000)): center=(-0.500, 2.500), perimeter=16.000, area=15.000", 
			new Point2D[] {p_20, p_25, p15, p10}, p_05205, p_20, p15);
		
		_testTriangle("Orange", 0, 0, 3, 9, 12, 0, 54., 12.+Math.sqrt(90)+Math.sqrt(162),
			"Orange Triangle", "center=(5.000, 3.000), perimeter=34.215, area=54.000", 
			p53, r1);
		
		_testTriangle("Teal", 0, 0, -2, 5, 1, 5, 7.5, 3 + Math.sqrt(29) + Math.sqrt(26),
			"Teal Triangle", "center=(-0.333, 3.333), perimeter=13.484, area=7.500", 
			pC2, r2);
	}
	
	public void testCSV() {
		_testCSV(new Shape2D[] {}, new String[] {});
		
		_testCSV(
			new Shape2D[] {
				new Rectangle("Blue", new Point2D(0, 0), new Point2D(2, 1)),
			}, 
			new String[] {
				"\"Blue\",0.000,0.000",
				"\"Blue\",0.000,1.000",
				"\"Blue\",2.000,1.000",
				"\"Blue\",2.000,0.000",
			}
		);
		
		_testCSV(
			new Shape2D[] {
				new Triangle("Gray", new Point2D(0, 0), new Point2D(0, 1), new Point2D(1, 0)),
			}, 
			new String[] {
				"\"Gray\",0.000,0.000",
				"\"Gray\",0.000,1.000",
				"\"Gray\",1.000,0.000",
				"\"Gray\",0.000,0.000",
				"\"Gray\",0.000,1.000",
				"\"Gray\",1.000,1.000",
				"\"Gray\",1.000,0.000",
			}
		);
		
		_testCSV(
			new Shape2D[] {
				new Rectangle("Red", new Point2D(-10, -10), new Point2D(-5.5, -5.5)),
				new Rectangle("Green", new Point2D(10, 10), new Point2D(5, 5)),
				new Triangle("Blue", new Point2D(0, 0), new Point2D(0, 2), new Point2D(2, 2)),
			}, 
			new String[] {
				"\"Red\",-10.000,-10.000",
				"\"Red\",-10.000,-5.500",
				"\"Red\",-5.500,-5.500",
				"\"Red\",-5.500,-10.000",
				"\"Green\",5.000,5.000",
				"\"Green\",5.000,10.000",
				"\"Green\",10.000,10.000",
				"\"Green\",10.000,5.000",
				"\"Blue\",0.000,0.000",
				"\"Blue\",0.000,2.000",
				"\"Blue\",2.000,2.000",
				"\"Blue\",0.000,0.000",
				"\"Blue\",0.000,2.000",
				"\"Blue\",2.000,2.000",
				"\"Blue\",2.000,0.000",
			}
		);
	}
	
	private void _testProgram(String[] a, String[] msg) {
		_test(a, String.format(String.join("%n", msg)));
	}
	
	public void testProgram() {
		_test(new String[] {}, ERR_USAGE);
		_test(new String[] {"Blue"}, ERR_USAGE);
		_test(new String[] {"Blue", "0"}, ERR_USAGE);
		_test(new String[] {"Blue", "0", "0"}, ERR_USAGE);
		_test(new String[] {"Blue", "0", "0", "0"}, ERR_USAGE);
		_test(new String[] {"Blue", "0", "0", "0", "2"}, ERR_USAGE);
		_test(new String[] {"Blue", "0", "0", "0", "2", "2"}, ERR_USAGE);
		_test(new String[] {"Blue", "0", "0", "0", "2", "2", "2", "2"}, ERR_USAGE);
		
		_testProgram(
			new String[] {"Blue", "0", "0", "0", "2", "2", "2"},
			new String[] {
				"\"Blue\",0.000,0.000",
				"\"Blue\",0.000,2.000",
				"\"Blue\",2.000,2.000",
				"\"Blue\",0.000,0.000",
				"\"Blue\",0.000,2.000",
				"\"Blue\",2.000,2.000",
				"\"Blue\",2.000,0.000",
			}
		);
		
		_testProgram(
			new String[] {"Gray", "0", "0", "0", "1", "1", "0"},
			new String[] {
				"\"Gray\",0.000,0.000",
				"\"Gray\",0.000,1.000",
				"\"Gray\",1.000,0.000",
				"\"Gray\",0.000,0.000",
				"\"Gray\",0.000,1.000",
				"\"Gray\",1.000,1.000",
				"\"Gray\",1.000,0.000",
			}
		);
		
		_testProgram(
			new String[] {"DarkOrchid", "0", "0", "-2", "5", "1", "5"},
			new String[] {
				"\"DarkOrchid\",0.000,0.000",
				"\"DarkOrchid\",-2.000,5.000",
				"\"DarkOrchid\",1.000,5.000",
				"\"DarkOrchid\",-2.000,0.000",
				"\"DarkOrchid\",-2.000,5.000",
				"\"DarkOrchid\",1.000,5.000",
				"\"DarkOrchid\",1.000,0.000",
			}
		);
	}
	
}
