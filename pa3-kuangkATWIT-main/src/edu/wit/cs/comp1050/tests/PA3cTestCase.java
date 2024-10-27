
package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA3c;
import edu.wit.cs.comp1050.Point2D;
import junit.framework.TestCase;

public class PA3cTestCase extends TestCase {
	
	private static final double THRESH = 0.00001;
	
	private static final String ERR_USAGE = "Please supply 2 numbers (x y).";
	
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
			PA3c.main(a);
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testPoint2D(Point2D p, double x, double y, String eS) {
		assertNotNull(p);
		
		assertEquals(x, p.getX(), THRESH);
		assertEquals(y, p.getY(), THRESH);
		assertEquals(0., p.distanceTo(p), THRESH);
		
		assertEquals(eS, p.toString());
	}
	
	private void _testPoint2D(Point2D p1, Point2D p2, double eD) {
		assertEquals(eD, Point2D.distance(p1, p2), THRESH);
		assertEquals(eD, p1.distanceTo(p2), THRESH);
		assertEquals(eD, p2.distanceTo(p1), THRESH);
	}
	
	private void _testPoint2D(double x1, double y1, String eS1, double x2, double y2, String eS2, double eD) {
		final Point2D p1 = new Point2D(x1, y1);
		_testPoint2D(p1, x1, y1, eS1);
		
		final Point2D p2 = new Point2D(x2, y2);
		_testPoint2D(p2, x2, y2, eS2);
		
		_testPoint2D(p1, p2, eD);
	}
	
	private void _testPoint2D(double x, double y, String eS, double eD) {
		final Point2D o = new Point2D();
		_testPoint2D(o, 0., 0., "(0.000, 0.000)");
		
		final Point2D p = new Point2D(x, y);
		_testPoint2D(p, x, y, eS);
		
		_testPoint2D(o, p, eD);
	}
	
	public void testPoint2D() {
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 0.);
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 0.0, 0.0, "(0.000, 0.000)", 0.);
		
		_testPoint2D(1.0, 0.0, "(1.000, 0.000)", 1.);
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 1.0, 0.0, "(1.000, 0.000)", 1.);
		
		_testPoint2D(0.0, 1.0, "(0.000, 1.000)", 1.);
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 0.0, 1.0, "(0.000, 1.000)", 1.);
		
		_testPoint2D(-1.0, 0.0, "(-1.000, 0.000)", 1.);
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", -1.0, 0.0, "(-1.000, 0.000)", 1.);
		
		_testPoint2D(0.0, -1.0, "(0.000, -1.000)", 1.);
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 0.0, -1.0, "(0.000, -1.000)", 1.);
		
		_testPoint2D(1.0, 1.0, "(1.000, 1.000)", Math.sqrt(2));
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 1.0, 1.0, "(1.000, 1.000)", Math.sqrt(2));
		
		_testPoint2D(-1.0, 1.0, "(-1.000, 1.000)", Math.sqrt(2));
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", -1.0, 1.0, "(-1.000, 1.000)", Math.sqrt(2));
		
		_testPoint2D(1.0, -1.0, "(1.000, -1.000)", Math.sqrt(2));
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 1.0, -1.0, "(1.000, -1.000)", Math.sqrt(2));
		
		_testPoint2D(-1.0, -1.0, "(-1.000, -1.000)", Math.sqrt(2));
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", -1.0, -1.0, "(-1.000, -1.000)", Math.sqrt(2));
		
		_testPoint2D(10.0, 30.5, "(10.000, 30.500)", 32.09750769140807);
		_testPoint2D(0.0, 0.0, "(0.000, 0.000)", 10.0, 30.5, "(10.000, 30.500)", 32.09750769140807);
		
		_testPoint2D(-1.0, -1.0, "(-1.000, -1.000)", 1.0, 1.0, "(1.000, 1.000)", 2*Math.sqrt(2));
	}
	
	public void testProgram() {
		_test(new String[] {}, ERR_USAGE);
		
		_test(new String[] {"1.0"}, ERR_USAGE);
		_test(new String[] {"a", "2.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "???"}, ERR_USAGE);
		
		_test(new String[] {"1", "1.0"}, TestSuite.terminalOutput(new String[] {
			"Point 1: (0.000, 0.000)",
			"Point 2: (1.000, 1.000)",
			"Static method distance: 1.414",
			"Distance from P1: 1.414",
			"Distance from P2: 1.414"
		}));
		
		_test(new String[] {"-1.00", "-1."}, TestSuite.terminalOutput(new String[] {
			"Point 1: (0.000, 0.000)",
			"Point 2: (-1.000, -1.000)",
			"Static method distance: 1.414",
			"Distance from P1: 1.414",
			"Distance from P2: 1.414"
		}));
		
		_test(new String[] {"10", "30.5"}, TestSuite.terminalOutput(new String[] {
			"Point 1: (0.000, 0.000)",
			"Point 2: (10.000, 30.500)",
			"Static method distance: 32.098",
			"Distance from P1: 32.098",
			"Distance from P2: 32.098",
		}));
	}
	
}
