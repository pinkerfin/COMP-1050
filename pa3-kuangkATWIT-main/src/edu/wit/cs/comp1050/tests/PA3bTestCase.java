
package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.Assert;

import edu.wit.cs.comp1050.LinearEquation;
import edu.wit.cs.comp1050.PA3b;
import junit.framework.TestCase;

public class PA3bTestCase extends TestCase {
	
	private static final double THRESH = 0.00001;
	
	private static final String ERR_USAGE = "Please supply 6 numbers (a-f).";
	private static final String ERR_NOSLTN = "The equation has no solution.";
	
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
			PA3b.main(a);
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testValidArgs(String[] a, double[] expected) {
		double[] result = null;
		try {
			result = PA3b.validateArgs(a);
		} catch (ExitException e) {}
		Assert.assertArrayEquals(expected, result, THRESH);
	}
	
	private void _testLinearEquation(LinearEquation leq, double a, double b, double c, double d, double e, double f, boolean eS, Double eX, Double eY) {
		assertNotNull(leq);
		
		assertEquals(a, leq.getA(), THRESH);
		assertEquals(b, leq.getB(), THRESH);
		assertEquals(c, leq.getC(), THRESH);
		assertEquals(d, leq.getD(), THRESH);
		assertEquals(e, leq.getE(), THRESH);
		assertEquals(f, leq.getF(), THRESH);
		
		assertEquals(eS, leq.isSolvable());
		
		if (eX == null) {
			assertNull(leq.getX());
		} else {
			assertEquals(eX, leq.getX(), THRESH);
		}
		
		if (eY == null) {
			assertNull(leq.getY());
		} else {
			assertEquals(eY, leq.getY(), THRESH);
		}
	}
	
	private void _testLinearEquation(double a, double b, double c, double d, double e, double f, boolean eS, Double eX, Double eY) {
		LinearEquation leq;
		
		leq = null;
		try {
			leq = new LinearEquation(a, b, c, d, e, f);
		} catch (ExitException ex) {}
		_testLinearEquation(leq, a, b, c, d, e, f, eS, eX, eY);
		
		leq = null;
		try {
			leq = new LinearEquation(new double[] {a, b, c, d, e, f});
		} catch (ExitException ex) {}
		_testLinearEquation(leq, a, b, c, d, e, f, eS, eX, eY);
	}
	
	public void testLinearEquation() {
		_testLinearEquation(9.0, 4.0, 3.0, -5.0, -6.0, -21.0, true, -2., 3.);
		_testLinearEquation(1.0, 2.0, 2.0, 4.0, 4.0, 5.0, false, null, null);
	}
	
	public void testValidArgs() {
		_testValidArgs(new String[] {}, null);
		
		_testValidArgs(new String[] {"9.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", "3.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", "3.0", "-5.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", "3.0", "-5.0", "-6.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", "3.0", "-5.0", "-6.0", "-21.0"}, new double[] {9., 4., 3., -5., -6., -21.});
		
		_testValidArgs(new String[] {"a", "4.0", "3.0", "-5.0", "-6.0", "-21.0"}, null);
		_testValidArgs(new String[] {"9.0", "B", "3.0", "-5.0", "-6.0", "-21.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", ":(", "-5.0", "-6.0", "-21.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", "3.0", "!", "-6.0", "-21.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", "3.0", "-5.0", " ", "-21.0"}, null);
		_testValidArgs(new String[] {"9.0", "4.0", "3.0", "-5.0", "-6.0", "_?weird!_"}, null);
		
		_testValidArgs(new String[] {"1.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "2.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "2.0", "4.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "2.0", "4.0", "4.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "2.0", "4.0", "4.0", "5.0"}, new double[] {1., 2., 2., 4., 4., 5.});
		
		_testValidArgs(new String[] {"Hi", "2.0", "2.0", "4.0", "4.0", "5.0"}, null);
		_testValidArgs(new String[] {"1.0", "there!", "2.0", "4.0", "4.0", "5.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "How", "4.0", "4.0", "5.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "2.0", "are", "4.0", "5.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "2.0", "4.0", "you", "5.0"}, null);
		_testValidArgs(new String[] {"1.0", "2.0", "2.0", "4.0", "4.0", "doing?"}, null);
	}
	
	public void testProgram() {
		_test(new String[] {}, ERR_USAGE);
		
		_test(new String[] {"9.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0", "3.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0", "3.0", "-5.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0", "3.0", "-5.0", "-6.0"}, ERR_USAGE);
		
		_test(new String[] {"a", "4.0", "3.0", "-5.0", "-6.0", "-21.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "B", "3.0", "-5.0", "-6.0", "-21.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0", ":(", "-5.0", "-6.0", "-21.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0", "3.0", "!", "-6.0", "-21.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0", "3.0", "-5.0", " ", "-21.0"}, ERR_USAGE);
		_test(new String[] {"9.0", "4.0", "3.0", "-5.0", "-6.0", "_?weird!_"}, ERR_USAGE);
		
		_test(new String[] {"1.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0", "2.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0", "2.0", "4.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0", "2.0", "4.0", "4.0"}, ERR_USAGE);
		
		_test(new String[] {"Hi", "2.0", "2.0", "4.0", "4.0", "5.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "there!", "2.0", "4.0", "4.0", "5.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0", "How", "4.0", "4.0", "5.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0", "2.0", "are", "4.0", "5.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0", "2.0", "4.0", "you", "5.0"}, ERR_USAGE);
		_test(new String[] {"1.0", "2.0", "2.0", "4.0", "4.0", "doing?"}, ERR_USAGE);
		
		_test(new String[] {"9.0", "4.0", "3.0", "-5.0", "-6.0", "-21.0"}, "Solution: x=-2.000, y=3.000");
		_test(new String[] {"1.0", "2.0", "2.0", "4.0", "4.0", "5.0"}, ERR_NOSLTN);
	}
	
}
