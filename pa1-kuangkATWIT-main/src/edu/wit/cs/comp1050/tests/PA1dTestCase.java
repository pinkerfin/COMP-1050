package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA1d;
import junit.framework.TestCase;

public class PA1dTestCase extends TestCase {
	
	private static final String ERR_MSG = "The package cannot be shipped!";
	
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
	
	private void _test(String input, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String expected = TestSuite.stringOutput(new String[] {
			"Enter package weight: " + msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA1d.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testMethod(double weight, double expected) {
		Double result = null;
		try {
			result = PA1d.shippingCost(weight);
		} catch (ExitException e) {}
		assertEquals(expected, result, 0.01);
	}
	
	private void _testGood(String value, String result) {
		_test(value, String.format("It will cost %s to ship this package.", result));
	}
	
	private void _testBad(String value) {
		_test(value, ERR_MSG);
	}
	
	public void testBadInput() {
		_testBad("0");
		_testBad("0.00");
		_testBad("-0.1");
		_testBad("20.1");
		_testBad("23");
		_testBad("101");
	}
	
	public void testMethod() {
		_testMethod(0.10, 3.50);
		_testMethod(0.50, 3.50);
		_testMethod(0.9, 3.50);
		_testMethod(0.99, 3.50);
		_testMethod(1.00, 3.50);
		
		_testMethod(1.10, 5.50);
		_testMethod(1.50, 5.50);
		_testMethod(2.00, 5.50);
		_testMethod(2.50, 5.50);
		_testMethod(3.00, 5.50);
		
		_testMethod(3.10, 8.50);
		_testMethod(3.60, 8.50);
		_testMethod(4.00, 8.50);
		_testMethod(4.20, 8.50);
		_testMethod(5.10, 8.50);
		_testMethod(6.6, 8.50);
		_testMethod(7.00, 8.50);
		_testMethod(7.90, 8.50);
		_testMethod(8.03, 8.50);
		_testMethod(8.99, 8.50);
		_testMethod(9.19, 8.50);
		_testMethod(9.81, 8.50);
		_testMethod(10.0, 8.50);
		
		_testMethod(10.01, 10.50);
		_testMethod(11.11, 10.50);
		_testMethod(12.22, 10.50);
		_testMethod(13.33, 10.50);
		_testMethod(14.44, 10.50);
		_testMethod(15.55, 10.50);
		_testMethod(16.66, 10.50);
		_testMethod(17.77, 10.50);
		_testMethod(18.88, 10.50);
		_testMethod(19.99, 10.50);
		_testMethod(20, 10.50);
	}
	
	public void testGoodInput() {
		_testGood("0.10", "$3.50");
		_testGood("0.50", "$3.50");
		_testGood("0.9", "$3.50");
		_testGood("0.99", "$3.50");
		_testGood("1.00", "$3.50");
		
		_testGood("1.10", "$5.50");
		_testGood("1.50", "$5.50");
		_testGood("2.00", "$5.50");
		_testGood("2.50", "$5.50");
		_testGood("3.00", "$5.50");
		
		_testGood("3.10", "$8.50");
		_testGood("3.60", "$8.50");
		_testGood("4.00", "$8.50");
		_testGood("4.20", "$8.50");
		_testGood("5.10", "$8.50");
		_testGood("6.6", "$8.50");
		_testGood("7.00", "$8.50");
		_testGood("7.90", "$8.50");
		_testGood("8.03", "$8.50");
		_testGood("8.99", "$8.50");
		_testGood("9.19", "$8.50");
		_testGood("9.81", "$8.50");
		_testGood("10.0", "$8.50");
		
		_testGood("10.01", "$10.50");
		_testGood("11.11", "$10.50");
		_testGood("12.22", "$10.50");
		_testGood("13.33", "$10.50");
		_testGood("14.44", "$10.50");
		_testGood("15.55", "$10.50");
		_testGood("16.66", "$10.50");
		_testGood("17.77", "$10.50");
		_testGood("18.88", "$10.50");
		_testGood("19.99", "$10.50");
		_testGood("20", "$10.50");
	}

}
