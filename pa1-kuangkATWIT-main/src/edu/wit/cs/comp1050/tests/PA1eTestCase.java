package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA1e;
import junit.framework.TestCase;

public class PA1eTestCase extends TestCase {
	
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
	
	private void _test(String s, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String input = String.format("%s%n", s);
		final String expected = TestSuite.stringOutput(new String[] {
			"Enter a string: " + msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA1e.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testMethod(String s, int expected) {
		Integer result = null;
		try {
			result = PA1e.numUpperCase(s);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testZero(String value) {
		_test(value, "There are no uppercase characters.");
	}
	
	private void _testOne(String value) {
		_test(value, "There is 1 uppercase character in the string.");
	}
	
	private void _testMultiple(String value, String num) {
		_test(value, String.format("There are %s uppercase characters in the string.", num));
	}
	
	public void testMethod() {
		_testMethod("", 0);
		
		_testMethod("a", 0);
		_testMethod("b", 0);
		_testMethod("c", 0);
		_testMethod("1", 0);
		_testMethod("2", 0);
		_testMethod("3", 0);
		
		_testMethod("A", 1);
		_testMethod("B", 1);
		_testMethod("C", 1);
		_testMethod("D", 1);
		_testMethod("E", 1);
		_testMethod("F", 1);
		_testMethod("G", 1);
		_testMethod("H", 1);
		_testMethod("I", 1);
		_testMethod("J", 1);
		_testMethod("K", 1);
		_testMethod("L", 1);
		_testMethod("M", 1);
		_testMethod("N", 1);
		_testMethod("O", 1);
		_testMethod("P", 1);
		_testMethod("Q", 1);
		_testMethod("R", 1);
		_testMethod("S", 1);
		_testMethod("T", 1);
		_testMethod("U", 1);
		_testMethod("V", 1);
		_testMethod("W", 1);
		_testMethod("X", 1);
		_testMethod("Y", 1);
		_testMethod("Z", 1);
		
		_testMethod("AA", 2);
		_testMethod("BB", 2);
		_testMethod("CC", 2);
		_testMethod("DD", 2);
		_testMethod("EE", 2);
		_testMethod("FF", 2);
		_testMethod("GG", 2);
		_testMethod("HH", 2);
		_testMethod("II", 2);
		_testMethod("JJ", 2);
		_testMethod("KK", 2);
		_testMethod("LL", 2);
		_testMethod("MM", 2);
		_testMethod("NN", 2);
		_testMethod("OO", 2);
		_testMethod("PP", 2);
		_testMethod("QQ", 2);
		_testMethod("RR", 2);
		_testMethod("SS", 2);
		_testMethod("TT", 2);
		_testMethod("UU", 2);
		_testMethod("VV", 2);
		_testMethod("WW", 2);
		_testMethod("XX", 2);
		_testMethod("YY", 2);
		_testMethod("ZZ", 2);
		
		_testMethod("A A!", 2);
		_testMethod("B B!", 2);
		_testMethod("C C!", 2);
		_testMethod("D D!", 2);
		_testMethod("E E!", 2);
		_testMethod("F F!", 2);
		_testMethod("G G!", 2);
		_testMethod("H H!", 2);
		_testMethod("I I!", 2);
		_testMethod("J J!", 2);
		_testMethod("K K!", 2);
		_testMethod("L L!", 2);
		_testMethod("M M!", 2);
		_testMethod("N N!", 2);
		_testMethod("O O!", 2);
		_testMethod("P P!", 2);
		_testMethod("Q Q!", 2);
		_testMethod("R R!", 2);
		_testMethod("S S!", 2);
		_testMethod("T T!", 2);
		_testMethod("U U!", 2);
		_testMethod("V V!", 2);
		_testMethod("W W!", 2);
		_testMethod("X X!", 2);
		_testMethod("Y Y!", 2);
		_testMethod("Z Z!", 2);
		
		_testMethod("_aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ_", 26);
		
		_testMethod("Wentworth Institute of Technology is #1! :)", 3);
		_testMethod("Le singe est sur la branche", 1);
		_testMethod("et le chat est sur la chaise.", 0);
		_testMethod("Je suis le president de Burundi!", 2);
	}
	
	public void testGoodInput() {
		_testZero("");
		
		_testZero("a");
		_testZero("b");
		_testZero("c");
		_testZero("1");
		_testZero("2");
		_testZero("3");
		
		_testOne("A");
		_testOne("B");
		_testOne("C");
		_testOne("D");
		_testOne("E");
		_testOne("F");
		_testOne("G");
		_testOne("H");
		_testOne("I");
		_testOne("J");
		_testOne("K");
		_testOne("L");
		_testOne("M");
		_testOne("N");
		_testOne("O");
		_testOne("P");
		_testOne("Q");
		_testOne("R");
		_testOne("S");
		_testOne("T");
		_testOne("U");
		_testOne("V");
		_testOne("W");
		_testOne("X");
		_testOne("Y");
		_testOne("Z");
		
		_testMultiple("AA", "2");
		_testMultiple("BB", "2");
		_testMultiple("CC", "2");
		_testMultiple("DD", "2");
		_testMultiple("EE", "2");
		_testMultiple("FF", "2");
		_testMultiple("GG", "2");
		_testMultiple("HH", "2");
		_testMultiple("II", "2");
		_testMultiple("JJ", "2");
		_testMultiple("KK", "2");
		_testMultiple("LL", "2");
		_testMultiple("MM", "2");
		_testMultiple("NN", "2");
		_testMultiple("OO", "2");
		_testMultiple("PP", "2");
		_testMultiple("QQ", "2");
		_testMultiple("RR", "2");
		_testMultiple("SS", "2");
		_testMultiple("TT", "2");
		_testMultiple("UU", "2");
		_testMultiple("VV", "2");
		_testMultiple("WW", "2");
		_testMultiple("XX", "2");
		_testMultiple("YY", "2");
		_testMultiple("ZZ", "2");
		
		_testMultiple("A A!", "2");
		_testMultiple("B B!", "2");
		_testMultiple("C C!", "2");
		_testMultiple("D D!", "2");
		_testMultiple("E E!", "2");
		_testMultiple("F F!", "2");
		_testMultiple("G G!", "2");
		_testMultiple("H H!", "2");
		_testMultiple("I I!", "2");
		_testMultiple("J J!", "2");
		_testMultiple("K K!", "2");
		_testMultiple("L L!", "2");
		_testMultiple("M M!", "2");
		_testMultiple("N N!", "2");
		_testMultiple("O O!", "2");
		_testMultiple("P P!", "2");
		_testMultiple("Q Q!", "2");
		_testMultiple("R R!", "2");
		_testMultiple("S S!", "2");
		_testMultiple("T T!", "2");
		_testMultiple("U U!", "2");
		_testMultiple("V V!", "2");
		_testMultiple("W W!", "2");
		_testMultiple("X X!", "2");
		_testMultiple("Y Y!", "2");
		_testMultiple("Z Z!", "2");
		
		_testMultiple("_aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ_", "26");
		
		_testMultiple("Wentworth Institute of Technology is #1! :)", "3");
		_testOne("Le singe est sur la branche");
		_testZero("et le chat est sur la chaise.");
		_testMultiple("Je suis le president de Burundi!", "2");
	}

}
