package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA1c;
import junit.framework.TestCase;

public class PA1cTestCase extends TestCase {
	
	private static final String ERR_MSG = "Dollar amount must be non-negative!";
	
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
			"Enter total amount: $" + msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA1c.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testGood(String value, String[] result) {
		_test(value, String.format(
				"You have %s %s, %s %s, %s %s, and %s %s.", 
				result[0], result[1], result[2], result[3], 
				result[4], result[5], result[6], result[7]
		));
	}
	
	private void _testBad(String value) {
		_test(value, ERR_MSG);
	}
	
	public void testBadInput() {
		_testBad("-1");
		_testBad("-1.0");
		_testBad("-1.00");
		_testBad("-170.14");
	}
	
	public void testGoodInput() {
		_testGood("0", new String[] {"0", "quarters", "0", "dimes", "0", "nickels", "0", "pennies"});
		_testGood("0.0", new String[] {"0", "quarters", "0", "dimes", "0", "nickels", "0", "pennies"});
		_testGood("0.00", new String[] {"0", "quarters", "0", "dimes", "0", "nickels", "0", "pennies"});
		
		_testGood("0.01", new String[] {"0", "quarters", "0", "dimes", "0", "nickels", "1", "penny"});
		_testGood("0.02", new String[] {"0", "quarters", "0", "dimes", "0", "nickels", "2", "pennies"});
		_testGood("0.03", new String[] {"0", "quarters", "0", "dimes", "0", "nickels", "3", "pennies"});
		_testGood("0.04", new String[] {"0", "quarters", "0", "dimes", "0", "nickels", "4", "pennies"});
		
		_testGood("0.05", new String[] {"0", "quarters", "0", "dimes", "1", "nickel", "0", "pennies"});
		_testGood("0.06", new String[] {"0", "quarters", "0", "dimes", "1", "nickel", "1", "penny"});
		_testGood("0.07", new String[] {"0", "quarters", "0", "dimes", "1", "nickel", "2", "pennies"});
		_testGood("0.08", new String[] {"0", "quarters", "0", "dimes", "1", "nickel", "3", "pennies"});
		_testGood("0.09", new String[] {"0", "quarters", "0", "dimes", "1", "nickel", "4", "pennies"});
		
		_testGood("0.10", new String[] {"0", "quarters", "1", "dime", "0", "nickels", "0", "pennies"});
		_testGood("0.11", new String[] {"0", "quarters", "1", "dime", "0", "nickels", "1", "penny"});
		_testGood("0.12", new String[] {"0", "quarters", "1", "dime", "0", "nickels", "2", "pennies"});
		_testGood("0.15", new String[] {"0", "quarters", "1", "dime", "1", "nickel", "0", "pennies"});
		_testGood("0.16", new String[] {"0", "quarters", "1", "dime", "1", "nickel", "1", "penny"});
		_testGood("0.17", new String[] {"0", "quarters", "1", "dime", "1", "nickel", "2", "pennies"});
		_testGood("0.20", new String[] {"0", "quarters", "2", "dimes", "0", "nickels", "0", "pennies"});
		_testGood("0.21", new String[] {"0", "quarters", "2", "dimes", "0", "nickels", "1", "penny"});
		_testGood("0.22", new String[] {"0", "quarters", "2", "dimes", "0", "nickels", "2", "pennies"});
		_testGood("0.23", new String[] {"0", "quarters", "2", "dimes", "0", "nickels", "3", "pennies"});
		_testGood("0.24", new String[] {"0", "quarters", "2", "dimes", "0", "nickels", "4", "pennies"});
		
		_testGood("0.25", new String[] {"1", "quarter", "0", "dimes", "0", "nickels", "0", "pennies"});
		_testGood("0.26", new String[] {"1", "quarter", "0", "dimes", "0", "nickels", "1", "penny"});
		_testGood("0.27", new String[] {"1", "quarter", "0", "dimes", "0", "nickels", "2", "pennies"});
		_testGood("0.30", new String[] {"1", "quarter", "0", "dimes", "1", "nickel", "0", "pennies"});
		_testGood("0.31", new String[] {"1", "quarter", "0", "dimes", "1", "nickel", "1", "penny"});
		_testGood("0.32", new String[] {"1", "quarter", "0", "dimes", "1", "nickel", "2", "pennies"});
		_testGood("0.35", new String[] {"1", "quarter", "1", "dime", "0", "nickels", "0", "pennies"});
		_testGood("0.36", new String[] {"1", "quarter", "1", "dime", "0", "nickels", "1", "penny"});
		_testGood("0.37", new String[] {"1", "quarter", "1", "dime", "0", "nickels", "2", "pennies"});
		_testGood("0.39", new String[] {"1", "quarter", "1", "dime", "0", "nickels", "4", "pennies"});
		_testGood("0.40", new String[] {"1", "quarter", "1", "dime", "1", "nickel", "0", "pennies"});
		_testGood("0.41", new String[] {"1", "quarter", "1", "dime", "1", "nickel", "1", "penny"});
		_testGood("0.42", new String[] {"1", "quarter", "1", "dime", "1", "nickel", "2", "pennies"});
		_testGood("0.45", new String[] {"1", "quarter", "2", "dimes", "0", "nickels", "0", "pennies"});
		_testGood("0.46", new String[] {"1", "quarter", "2", "dimes", "0", "nickels", "1", "penny"});
		_testGood("0.47", new String[] {"1", "quarter", "2", "dimes", "0", "nickels", "2", "pennies"});
		_testGood("0.49", new String[] {"1", "quarter", "2", "dimes", "0", "nickels", "4", "pennies"});
		_testGood("0.50", new String[] {"2", "quarters", "0", "dimes", "0", "nickels", "0", "pennies"});
		_testGood("0.51", new String[] {"2", "quarters", "0", "dimes", "0", "nickels", "1", "penny"});
		_testGood("0.52", new String[] {"2", "quarters", "0", "dimes", "0", "nickels", "2", "pennies"});
		_testGood("0.55", new String[] {"2", "quarters", "0", "dimes", "1", "nickel", "0", "pennies"});
		_testGood("0.56", new String[] {"2", "quarters", "0", "dimes", "1", "nickel", "1", "penny"});
		_testGood("0.57", new String[] {"2", "quarters", "0", "dimes", "1", "nickel", "2", "pennies"});
		_testGood("0.60", new String[] {"2", "quarters", "1", "dime", "0", "nickels", "0", "pennies"});
		_testGood("0.61", new String[] {"2", "quarters", "1", "dime", "0", "nickels", "1", "penny"});
		_testGood("0.62", new String[] {"2", "quarters", "1", "dime", "0", "nickels", "2", "pennies"});
		_testGood("0.64", new String[] {"2", "quarters", "1", "dime", "0", "nickels", "4", "pennies"});
		_testGood("0.65", new String[] {"2", "quarters", "1", "dime", "1", "nickel", "0", "pennies"});
		_testGood("0.66", new String[] {"2", "quarters", "1", "dime", "1", "nickel", "1", "penny"});
		_testGood("0.67", new String[] {"2", "quarters", "1", "dime", "1", "nickel", "2", "pennies"});
		_testGood("0.70", new String[] {"2", "quarters", "2", "dimes", "0", "nickels", "0", "pennies"});
		_testGood("0.71", new String[] {"2", "quarters", "2", "dimes", "0", "nickels", "1", "penny"});
		_testGood("0.72", new String[] {"2", "quarters", "2", "dimes", "0", "nickels", "2", "pennies"});
		_testGood("0.74", new String[] {"2", "quarters", "2", "dimes", "0", "nickels", "4", "pennies"});
		
		_testGood("1.94", new String[] {"7", "quarters", "1", "dime", "1", "nickel", "4", "pennies"});
		_testGood("101.94", new String[] {"407", "quarters", "1", "dime", "1", "nickel", "4", "pennies"});
		_testGood("1.99", new String[] {"7", "quarters", "2", "dimes", "0", "nickels", "4", "pennies"});
		_testGood("507.99", new String[] {"2031", "quarters", "2", "dimes", "0", "nickels", "4", "pennies"});
	}

}
