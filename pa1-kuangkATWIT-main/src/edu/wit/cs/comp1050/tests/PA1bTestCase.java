package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA1b;
import junit.framework.TestCase;

public class PA1bTestCase extends TestCase {
	
	private static final String ERR_MSG = "All coin counts must be non-negative!";
	
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
	
	private void _test(String[] values, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String input = String.join(" ", values);
		final String expected = TestSuite.stringOutput(new String[] {
			"Enter number of quarters: " +
			"Enter number of dimes: " +
			"Enter number of nickels: " +
			"Enter number of pennies: " +
			msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA1b.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testGood(String[] values, String result) {
		_test(values, String.format("You have %s in coins.", result));
	}
	
	private void _testBad(String[] values) {
		_test(values, ERR_MSG);
	}
	
	public void testBadInput() {
		_testBad(new String[] {"1", "2", "3", "-1"});
		_testBad(new String[] {"1", "2", "-1", "4"});
		_testBad(new String[] {"1", "-1", "3", "4"});
		_testBad(new String[] {"-1", "2", "3", "4"});
		
		_testBad(new String[] {"-3", "-17", "0", "-40"});
	}
	
	public void testGoodInput() {
		_testGood(new String[] {"0", "0", "0", "0"}, "$0.00");
		
		_testGood(new String[] {"1", "0", "0", "0"}, "$0.25");
		_testGood(new String[] {"0", "1", "0", "0"}, "$0.10");
		_testGood(new String[] {"0", "0", "1", "0"}, "$0.05");
		_testGood(new String[] {"0", "0", "0", "1"}, "$0.01");
		
		_testGood(new String[] {"1", "0", "0", "4"}, "$0.29");
		_testGood(new String[] {"1", "0", "3", "4"}, "$0.44");
		_testGood(new String[] {"1", "2", "3", "4"}, "$0.64");
		
		_testGood(new String[] {"101", "202", "303", "4"}, "$60.64");
	}

}
