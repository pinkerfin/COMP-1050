package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA2b;
import junit.framework.TestCase;

public class PA2bTestCase extends TestCase {
	
	private static final String ERR_VALUES = "Number of values must be positive.";
	private static final String P_NUM = "Enter the number of values: ";
	private static final String P_VAL = "Enter the values: ";
	
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
	
	private void _test(String in, String sNum, String sValues, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String input = String.format("%s%n%s%n", sNum, sValues);
		final String expected = TestSuite.stringOutput(new String[] {
			in,
			msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA2b.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testHasConsecutive(int[] a, int k, boolean expected) {
		Boolean result = null;
		try {
			result = PA2b.hasConsecutive(a, k);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testMaxConsecutive(int[] a, int expected) {
		Integer result = null;
		try {
			result = PA2b.maxConsecutive(a);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	public void testHasConsecutive() {
		_testHasConsecutive(new int[] {}, -1, false);
		_testHasConsecutive(new int[] {}, 0, false);
		_testHasConsecutive(new int[] {}, 1, false);
		_testHasConsecutive(new int[] {}, 2, false);
		_testHasConsecutive(new int[] {}, 3, false);
		_testHasConsecutive(new int[] {}, 4, false);
		
		_testHasConsecutive(new int[] {1}, -1, false);
		_testHasConsecutive(new int[] {1}, 0, false);
		_testHasConsecutive(new int[] {1}, 1, true);
		_testHasConsecutive(new int[] {1}, 2, false);
		_testHasConsecutive(new int[] {1}, 3, false);
		_testHasConsecutive(new int[] {1}, 3, false);
		
		_testHasConsecutive(new int[] {1, 2}, -1, false);
		_testHasConsecutive(new int[] {1, 2}, 0, false);
		_testHasConsecutive(new int[] {1, 2}, 1, true);
		_testHasConsecutive(new int[] {1, 2}, 2, false);
		_testHasConsecutive(new int[] {1, 2}, 3, false);
		_testHasConsecutive(new int[] {1, 2}, 4, false);
		
		_testHasConsecutive(new int[] {2, 2}, -1, false);
		_testHasConsecutive(new int[] {2, 2}, 0, false);
		_testHasConsecutive(new int[] {2, 2}, 1, true);
		_testHasConsecutive(new int[] {2, 2}, 2, true);
		_testHasConsecutive(new int[] {2, 2}, 3, false);
		_testHasConsecutive(new int[] {2, 2}, 4, false);
		
		_testHasConsecutive(new int[] {1, 2, 1}, -1, false);
		_testHasConsecutive(new int[] {1, 2, 1}, 0, false);
		_testHasConsecutive(new int[] {1, 2, 1}, 1, true);
		_testHasConsecutive(new int[] {1, 2, 1}, 2, false);
		_testHasConsecutive(new int[] {1, 2, 1}, 3, false);
		_testHasConsecutive(new int[] {1, 2, 1}, 3, false);
		
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, -1, false);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 0, false);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 1, true);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 2, true);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 3, true);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 4, true);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 5, false);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 6, false);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 7, false);
		_testHasConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 8, false);
	}
	
	public void testMaxConsecutive() {
		_testMaxConsecutive(new int[] {}, 0);
		_testMaxConsecutive(new int[] {1}, 1);
		_testMaxConsecutive(new int[] {1, 2}, 1);
		_testMaxConsecutive(new int[] {2, 2}, 2);
		_testMaxConsecutive(new int[] {1, 2, 1}, 1);
		_testMaxConsecutive(new int[] {3, 3, 5, 5, 5, 5, 4}, 4);
		_testMaxConsecutive(new int[] {3, 4, 5, 5, 6, 5, 5, 4, 5}, 2);
	}
	
	public void _testBad(String n) {
		_test(P_NUM, n, "", ERR_VALUES);
	}
	
	public void _testGood(String n, String v, String ans) {
		_test(P_NUM+P_VAL, n, v, "The maximum length of consecutive values is " + ans + ".");
	}
	
	public void testProgram() {
		_testBad("-1");
		_testBad("0");
		
		_testGood("1", "1", "1");
		_testGood("2", "1 2", "1");
		_testGood("2", "2 2", "2");
		_testGood("3", "1 2 1", "1");
		_testGood("7", "3 3 5 5 5 5 4", "4");
		_testGood("9", "3 4 5 5 6 5 5 4 5", "2");
	}
	
}
