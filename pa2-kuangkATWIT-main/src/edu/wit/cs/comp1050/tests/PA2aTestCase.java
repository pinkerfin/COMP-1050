package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA2a;
import junit.framework.TestCase;

public class PA2aTestCase extends TestCase {
	
	private static final String ERR_NON_NUMERIC = "Non-numeric input";
	private static final String ERR_SHORT = "Input is too short";
	private static final String ERR_LONG = "Input is too long";
	private static final String ERR_BAD_PREFIX = "Invalid prefix";
	
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
			"Enter a credit card number: ",
			"Status: " + msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA2a.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testIsOnlyNumbers(String s, boolean expected) {
		Boolean result = null;
		try {
			result = PA2a.isOnlyNumbers(s);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testDigitCharToInt(char c, int expected) {
		Integer result = null;
		try {
			result = PA2a.digitCharToInt(c);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testDigitCharToInt(char c1, char c2, int expected) {
		Integer result = null;
		try {
			result = PA2a.digitCharToInt(c1, c2);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testInArray(int n, int[] h, boolean expected) {
		Boolean result = null;
		try {
			result = PA2a.inArray(n, h);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testValidPrefix(String s, boolean expected) {
		Boolean result = null;
		try {
			result = PA2a.validPrefix(s);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testNumDigits(int num, int expected) {
		Integer result = null;
		try {
			result = PA2a.numDigits(num);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testGetDigitInPlace(int num, int place, int expected) {
		Integer result = null;
		try {
			result = PA2a.getDigitInPlace(num, place);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testReduceToDigit(int num, int expected) {
		Integer result = null;
		try {
			result = PA2a.reduceToDigit(num);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testSumOfDoubleEvens(String s, int expected) {
		Integer result = null;
		try {
			result = PA2a.sumOfDoubleEvens(s);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testSumOfOdds(String s, int expected) {
		Integer result = null;
		try {
			result = PA2a.sumOfOdds(s);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testLuhnCheck(String s, boolean expected) {
		Boolean result = null;
		try {
			result = PA2a.luhnCheck(s);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	public void testIsOnlyNumbers() {
		_testIsOnlyNumbers("", true);
		
		_testIsOnlyNumbers("0", true);
		_testIsOnlyNumbers("1", true);
		_testIsOnlyNumbers("2", true);
		_testIsOnlyNumbers("3", true);
		_testIsOnlyNumbers("4", true);
		_testIsOnlyNumbers("5", true);
		_testIsOnlyNumbers("6", true);
		_testIsOnlyNumbers("7", true);
		_testIsOnlyNumbers("8", true);
		_testIsOnlyNumbers("9", true);
		
		_testIsOnlyNumbers("01", true);
		_testIsOnlyNumbers("12", true);
		_testIsOnlyNumbers("23", true);
		_testIsOnlyNumbers("34", true);
		_testIsOnlyNumbers("45", true);
		_testIsOnlyNumbers("56", true);
		_testIsOnlyNumbers("67", true);
		_testIsOnlyNumbers("78", true);
		_testIsOnlyNumbers("89", true);
		_testIsOnlyNumbers("90", true);
		
		_testIsOnlyNumbers(" ", false);
		_testIsOnlyNumbers("      ", false);
		_testIsOnlyNumbers("-", false);
		_testIsOnlyNumbers(".", false);
		
		_testIsOnlyNumbers("a", false);
		_testIsOnlyNumbers("b", false);
		_testIsOnlyNumbers("c", false);
		_testIsOnlyNumbers("d", false);
		_testIsOnlyNumbers("e", false);
		_testIsOnlyNumbers("f", false);
		_testIsOnlyNumbers("g", false);
		_testIsOnlyNumbers("h", false);
		_testIsOnlyNumbers("i", false);
		_testIsOnlyNumbers("j", false);
		_testIsOnlyNumbers("k", false);
		_testIsOnlyNumbers("l", false);
		_testIsOnlyNumbers("m", false);
		_testIsOnlyNumbers("n", false);
		_testIsOnlyNumbers("o", false);
		_testIsOnlyNumbers("p", false);
		_testIsOnlyNumbers("q", false);
		_testIsOnlyNumbers("r", false);
		_testIsOnlyNumbers("s", false);
		_testIsOnlyNumbers("t", false);
		_testIsOnlyNumbers("u", false);
		_testIsOnlyNumbers("v", false);
		_testIsOnlyNumbers("w", false);
		_testIsOnlyNumbers("x", false);
		_testIsOnlyNumbers("y", false);
		_testIsOnlyNumbers("z", false);
		_testIsOnlyNumbers("A", false);
		_testIsOnlyNumbers("B", false);
		_testIsOnlyNumbers("C", false);
		_testIsOnlyNumbers("D", false);
		_testIsOnlyNumbers("E", false);
		_testIsOnlyNumbers("F", false);
		_testIsOnlyNumbers("G", false);
		_testIsOnlyNumbers("H", false);
		_testIsOnlyNumbers("I", false);
		_testIsOnlyNumbers("J", false);
		_testIsOnlyNumbers("K", false);
		_testIsOnlyNumbers("L", false);
		_testIsOnlyNumbers("M", false);
		_testIsOnlyNumbers("N", false);
		_testIsOnlyNumbers("O", false);
		_testIsOnlyNumbers("P", false);
		_testIsOnlyNumbers("Q", false);
		_testIsOnlyNumbers("R", false);
		_testIsOnlyNumbers("S", false);
		_testIsOnlyNumbers("T", false);
		_testIsOnlyNumbers("U", false);
		_testIsOnlyNumbers("V", false);
		_testIsOnlyNumbers("W", false);
		_testIsOnlyNumbers("X", false);
		_testIsOnlyNumbers("Y", false);
		_testIsOnlyNumbers("Z", false);
		
		_testIsOnlyNumbers("5117275325077359", true);
		_testIsOnlyNumbers("4388576018402626", true);
		
		_testIsOnlyNumbers("5117 2753 2507 7359", false);
		_testIsOnlyNumbers("4388 5760 1840 2626", false);
		
		_testIsOnlyNumbers("5117-2753-2507-7359", false);
		_testIsOnlyNumbers("4388-5760-1840-2626", false);
	}
	
	public void testDigitToChar() {
		_testDigitCharToInt('0', 0);
		_testDigitCharToInt('1', 1);
		_testDigitCharToInt('2', 2);
		_testDigitCharToInt('3', 3);
		_testDigitCharToInt('4', 4);
		_testDigitCharToInt('5', 5);
		_testDigitCharToInt('6', 6);
		_testDigitCharToInt('7', 7);
		_testDigitCharToInt('8', 8);
		_testDigitCharToInt('9', 9);
		
		_testDigitCharToInt('0', '0', 0);
		_testDigitCharToInt('0', '1', 1);
		_testDigitCharToInt('0', '2', 2);
		_testDigitCharToInt('0', '3', 3);
		_testDigitCharToInt('0', '4', 4);
		_testDigitCharToInt('0', '5', 5);
		_testDigitCharToInt('0', '6', 6);
		_testDigitCharToInt('0', '7', 7);
		_testDigitCharToInt('0', '8', 8);
		_testDigitCharToInt('0', '9', 9);
		
		_testDigitCharToInt('1', '0', 10);
		_testDigitCharToInt('1', '1', 11);
		
		_testDigitCharToInt('2', '0', 20);
		_testDigitCharToInt('2', '1', 21);
		_testDigitCharToInt('2', '2', 22);
		
		_testDigitCharToInt('3', '0', 30);
		_testDigitCharToInt('3', '1', 31);
		_testDigitCharToInt('3', '2', 32);
		_testDigitCharToInt('3', '3', 33);
		
		_testDigitCharToInt('4', '0', 40);
		_testDigitCharToInt('4', '1', 41);
		_testDigitCharToInt('4', '2', 42);
		_testDigitCharToInt('4', '3', 43);
		_testDigitCharToInt('4', '4', 44);
		
		_testDigitCharToInt('5', '0', 50);
		_testDigitCharToInt('5', '1', 51);
		_testDigitCharToInt('5', '2', 52);
		_testDigitCharToInt('5', '3', 53);
		_testDigitCharToInt('5', '4', 54);
		_testDigitCharToInt('5', '5', 55);
		
		_testDigitCharToInt('6', '0', 60);
		_testDigitCharToInt('6', '1', 61);
		_testDigitCharToInt('6', '2', 62);
		_testDigitCharToInt('6', '3', 63);
		_testDigitCharToInt('6', '4', 64);
		_testDigitCharToInt('6', '5', 65);
		_testDigitCharToInt('6', '6', 66);
		
		_testDigitCharToInt('7', '0', 70);
		_testDigitCharToInt('7', '1', 71);
		_testDigitCharToInt('7', '2', 72);
		_testDigitCharToInt('7', '3', 73);
		_testDigitCharToInt('7', '4', 74);
		_testDigitCharToInt('7', '5', 75);
		_testDigitCharToInt('7', '6', 76);
		_testDigitCharToInt('7', '7', 77);
		
		_testDigitCharToInt('8', '0', 80);
		_testDigitCharToInt('8', '1', 81);
		_testDigitCharToInt('8', '2', 82);
		_testDigitCharToInt('8', '3', 83);
		_testDigitCharToInt('8', '4', 84);
		_testDigitCharToInt('8', '5', 85);
		_testDigitCharToInt('8', '6', 86);
		_testDigitCharToInt('8', '7', 87);
		_testDigitCharToInt('8', '8', 88);
		
		_testDigitCharToInt('9', '0', 90);
		_testDigitCharToInt('9', '1', 91);
		_testDigitCharToInt('9', '2', 92);
		_testDigitCharToInt('9', '3', 93);
		_testDigitCharToInt('9', '4', 94);
		_testDigitCharToInt('9', '5', 95);
		_testDigitCharToInt('9', '6', 96);
		_testDigitCharToInt('9', '7', 97);
		_testDigitCharToInt('9', '8', 98);
		_testDigitCharToInt('9', '9', 99);
	}
	
	public void testInArray() {
		_testInArray(0, new int[] {}, false);
		_testInArray(100, new int[] {}, false);
		_testInArray(555, new int[] {}, false);
		
		_testInArray(0, new int[] {0}, true);
		_testInArray(100, new int[] {100}, true);
		_testInArray(555, new int[] {555}, true);
		
		_testInArray(0, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(1, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(2, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(3, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(4, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(5, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(6, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(7, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(8, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		_testInArray(9, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, true);
		
		_testInArray(100, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false);
		_testInArray(555, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false);
		_testInArray(-100, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false);
		_testInArray(-555, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false);
	}
	
	public void testValidPrefix() {
		_testValidPrefix("", false);
		_testValidPrefix("0", false);
		_testValidPrefix("1", false);
		_testValidPrefix("2", false);
		_testValidPrefix("3", false);
		_testValidPrefix("7", false);
		_testValidPrefix("8", false);
		_testValidPrefix("9", false);
		
		_testValidPrefix("4", true);
		_testValidPrefix("5", true);
		_testValidPrefix("6", true);
		
		_testValidPrefix("00", false);
		_testValidPrefix("01", false);
		_testValidPrefix("02", false);
		_testValidPrefix("03", false);
		_testValidPrefix("04", false);
		_testValidPrefix("05", false);
		_testValidPrefix("06", false);
		_testValidPrefix("07", false);
		_testValidPrefix("08", false);
		_testValidPrefix("09", false);
		
		_testValidPrefix("10", false);
		_testValidPrefix("11", false);
		_testValidPrefix("12", false);
		_testValidPrefix("13", false);
		_testValidPrefix("14", false);
		_testValidPrefix("15", false);
		_testValidPrefix("16", false);
		_testValidPrefix("17", false);
		_testValidPrefix("18", false);
		_testValidPrefix("19", false);
		
		_testValidPrefix("20", false);
		_testValidPrefix("21", false);
		_testValidPrefix("22", false);
		_testValidPrefix("23", false);
		_testValidPrefix("24", false);
		_testValidPrefix("25", false);
		_testValidPrefix("26", false);
		_testValidPrefix("27", false);
		_testValidPrefix("28", false);
		_testValidPrefix("29", false);
		
		_testValidPrefix("30", false);
		_testValidPrefix("31", false);
		_testValidPrefix("32", false);
		_testValidPrefix("33", false);
		_testValidPrefix("34", false);
		_testValidPrefix("35", false);
		_testValidPrefix("36", false);
		_testValidPrefix("37", true);
		_testValidPrefix("38", false);
		_testValidPrefix("39", false);
		
		_testValidPrefix("40", true);
		_testValidPrefix("41", true);
		_testValidPrefix("42", true);
		_testValidPrefix("43", true);
		_testValidPrefix("44", true);
		_testValidPrefix("45", true);
		_testValidPrefix("46", true);
		_testValidPrefix("47", true);
		_testValidPrefix("48", true);
		_testValidPrefix("49", true);
		
		_testValidPrefix("50", true);
		_testValidPrefix("51", true);
		_testValidPrefix("52", true);
		_testValidPrefix("53", true);
		_testValidPrefix("54", true);
		_testValidPrefix("55", true);
		_testValidPrefix("56", true);
		_testValidPrefix("57", true);
		_testValidPrefix("58", true);
		_testValidPrefix("59", true);
		
		_testValidPrefix("60", true);
		_testValidPrefix("61", true);
		_testValidPrefix("62", true);
		_testValidPrefix("63", true);
		_testValidPrefix("64", true);
		_testValidPrefix("65", true);
		_testValidPrefix("66", true);
		_testValidPrefix("67", true);
		_testValidPrefix("68", true);
		_testValidPrefix("69", true);
		
		_testValidPrefix("70", false);
		_testValidPrefix("71", false);
		_testValidPrefix("72", false);
		_testValidPrefix("73", false);
		_testValidPrefix("74", false);
		_testValidPrefix("75", false);
		_testValidPrefix("76", false);
		_testValidPrefix("77", false);
		_testValidPrefix("78", false);
		_testValidPrefix("79", false);
		
		_testValidPrefix("80", false);
		_testValidPrefix("81", false);
		_testValidPrefix("82", false);
		_testValidPrefix("83", false);
		_testValidPrefix("84", false);
		_testValidPrefix("85", false);
		_testValidPrefix("86", false);
		_testValidPrefix("87", false);
		_testValidPrefix("88", false);
		_testValidPrefix("89", false);
		
		_testValidPrefix("90", false);
		_testValidPrefix("91", false);
		_testValidPrefix("92", false);
		_testValidPrefix("93", false);
		_testValidPrefix("94", false);
		_testValidPrefix("95", false);
		_testValidPrefix("96", false);
		_testValidPrefix("97", false);
		_testValidPrefix("98", false);
		_testValidPrefix("99", false);
		
		_testValidPrefix("0117275325077359", false);
		_testValidPrefix("1117275325077359", false);
		_testValidPrefix("2117275325077359", false);
		_testValidPrefix("3117275325077359", false);
		_testValidPrefix("3717275325077359", true);
		_testValidPrefix("4117275325077359", true);
		_testValidPrefix("5117275325077359", true);
		_testValidPrefix("6117275325077359", true);
		_testValidPrefix("7117275325077359", false);
		_testValidPrefix("8117275325077359", false);
		_testValidPrefix("9117275325077359", false);
		
		_testValidPrefix("0388576018402626", false);
		_testValidPrefix("1388576018402626", false);
		_testValidPrefix("2388576018402626", false);
		_testValidPrefix("3388576018402626", false);
		_testValidPrefix("3788576018402626", true);
		_testValidPrefix("4388576018402626", true);
		_testValidPrefix("5388576018402626", true);
		_testValidPrefix("6388576018402626", true);
		_testValidPrefix("7388576018402626", false);
		_testValidPrefix("8388576018402626", false);
		_testValidPrefix("9388576018402626", false);
		
		_testValidPrefix("078282246310005", false);
		_testValidPrefix("178282246310005", false);
		_testValidPrefix("278282246310005", false);
		_testValidPrefix("378282246310005", true);
		_testValidPrefix("478282246310005", true);
		_testValidPrefix("578282246310005", true);
		_testValidPrefix("678282246310005", true);
		_testValidPrefix("778282246310005", false);
		_testValidPrefix("878282246310005", false);
		_testValidPrefix("978282246310005", false);
	}
	
	public void testNumDigits() {
		_testNumDigits(0, 1);
		_testNumDigits(1, 1);
		_testNumDigits(9, 1);
		
		_testNumDigits(10, 2);
		_testNumDigits(11, 2);
		_testNumDigits(19, 2);
		_testNumDigits(50, 2);
		_testNumDigits(99, 2);
		
		_testNumDigits(100, 3);
		_testNumDigits(101, 3);
		_testNumDigits(109, 3);
		_testNumDigits(110, 3);
		_testNumDigits(199, 3);
		_testNumDigits(200, 3);
		_testNumDigits(999, 3);
		
		_testNumDigits(1000, 4);
		_testNumDigits(1001, 4);
	}
	
	public void testGetDigitInPlace() {
		_testGetDigitInPlace(1234, 0, 4);
		_testGetDigitInPlace(1234, 1, 3);
		_testGetDigitInPlace(1234, 2, 2);
		_testGetDigitInPlace(1234, 3, 1);
		
		_testGetDigitInPlace(567, 0, 7);
		_testGetDigitInPlace(567, 1, 6);
		_testGetDigitInPlace(567, 2, 5);
		
		_testGetDigitInPlace(98, 0, 8);
		_testGetDigitInPlace(98, 1, 9);
		_testGetDigitInPlace(10, 0, 0);
		_testGetDigitInPlace(10, 1, 1);
		
		_testGetDigitInPlace(0, 0, 0);
		_testGetDigitInPlace(1, 0, 1);
		_testGetDigitInPlace(2, 0, 2);
		_testGetDigitInPlace(9, 0, 9);
	}
	
	public void testReduceToDigit() {
		_testReduceToDigit(0, 0);
		_testReduceToDigit(1, 1);
		_testReduceToDigit(2, 2);
		_testReduceToDigit(3, 3);
		_testReduceToDigit(4, 4);
		_testReduceToDigit(9, 9);
		
		_testReduceToDigit(10, 1);
		_testReduceToDigit(11, 2);
		_testReduceToDigit(12, 3);
		_testReduceToDigit(13, 4);
		_testReduceToDigit(14, 5);
		_testReduceToDigit(15, 6);
		_testReduceToDigit(16, 7);
		_testReduceToDigit(17, 8);
		_testReduceToDigit(18, 9);
		
		_testReduceToDigit(19, 1);
		_testReduceToDigit(20, 2);
		_testReduceToDigit(28, 1);
		_testReduceToDigit(29, 2);
		_testReduceToDigit(30, 3);
		_testReduceToDigit(99, 9);
		_testReduceToDigit(100, 1);
		_testReduceToDigit(199, 1);
		_testReduceToDigit(950, 5);
		_testReduceToDigit(999, 9);
		_testReduceToDigit(5555, 2);
		_testReduceToDigit(5678, 8);
		_testReduceToDigit(9999, 9);
		_testReduceToDigit(45678, 3);
		_testReduceToDigit(99998, 8);
	}
	
	public void testSumOfDoubleEvens() {
		_testSumOfDoubleEvens("", 0);
		
		_testSumOfDoubleEvens("0", 0);
		_testSumOfDoubleEvens("1", 0);
		_testSumOfDoubleEvens("2", 0);
		_testSumOfDoubleEvens("9", 0);
		
		_testSumOfDoubleEvens("00", 0);
		_testSumOfDoubleEvens("11", 2);
		_testSumOfDoubleEvens("22", 4);
		_testSumOfDoubleEvens("99", 9);
		_testSumOfDoubleEvens("03", 0);
		_testSumOfDoubleEvens("52", 1);
		_testSumOfDoubleEvens("27", 4);
		_testSumOfDoubleEvens("98", 9);
		_testSumOfDoubleEvens("30", 6);
		_testSumOfDoubleEvens("25", 4);
		_testSumOfDoubleEvens("72", 5);
		_testSumOfDoubleEvens("89", 7);
		
		_testSumOfDoubleEvens("4388576018402626", 37);
		_testSumOfDoubleEvens("5117275325077359", 18);
		_testSumOfDoubleEvens("378282246310005", 27);
		_testSumOfDoubleEvens("371449635398431", 47);
		_testSumOfDoubleEvens("378734493671000", 32);
		_testSumOfDoubleEvens("6011111111111117", 17);
		_testSumOfDoubleEvens("5555555555554444", 22);
		_testSumOfDoubleEvens("5105105105105100", 7);
		_testSumOfDoubleEvens("4012888888881881", 47);
		_testSumOfDoubleEvens("4222222222222", 24);
	}
	
	public void testSumOfOdds() {
		_testSumOfOdds("", 0);
		
		_testSumOfOdds("0", 0);
		_testSumOfOdds("1", 1);
		_testSumOfOdds("2", 2);
		_testSumOfOdds("9", 9);
		
		_testSumOfOdds("00", 0);
		_testSumOfOdds("11", 1);
		_testSumOfOdds("22", 2);
		_testSumOfOdds("99", 9);
		_testSumOfOdds("03", 3);
		_testSumOfOdds("52", 2);
		_testSumOfOdds("27", 7);
		_testSumOfOdds("98", 8);
		_testSumOfOdds("30", 0);
		_testSumOfOdds("25", 5);
		_testSumOfOdds("72", 2);
		_testSumOfOdds("89", 9);
		
		_testSumOfOdds("4388576018402626", 38);
		_testSumOfOdds("5117275325077359", 42);
		_testSumOfOdds("378282246310005", 33);
		_testSumOfOdds("371449635398431", 33);
		_testSumOfOdds("378734493671000", 28);
		_testSumOfOdds("6011111111111117", 13);
		_testSumOfOdds("5555555555554444", 38);
		_testSumOfOdds("5105105105105100", 13);
		_testSumOfOdds("4012888888881881", 43);
		_testSumOfOdds("4222222222222", 16);
	}
	
	public void testLuhnCheck() {
		_testLuhnCheck("4388576018402626", false);
		
		_testLuhnCheck("5117275325077359", true);
		_testLuhnCheck("5117275325077358", false);
		_testLuhnCheck("5117275325077350", false);
		
		_testLuhnCheck("378282246310005", true);
		_testLuhnCheck("378282246310015", false);
		_testLuhnCheck("378282246310095", false);
		
		_testLuhnCheck("371449635398431", true);
		_testLuhnCheck("371449635398531", false);
		_testLuhnCheck("371449635398331", false);
		
		_testLuhnCheck("378734493671000", true);
		_testLuhnCheck("378734493672000", false);
		_testLuhnCheck("378734493670000", false);
		
		_testLuhnCheck("6011111111111117", true);
		_testLuhnCheck("6011111111121117", false);
		_testLuhnCheck("6011111111101117", false);
		
		_testLuhnCheck("5555555555554444", true);
		_testLuhnCheck("5555555555654444", false);
		_testLuhnCheck("5555555555454444", false);
		
		_testLuhnCheck("5105105105105100", true);
		_testLuhnCheck("5105105106105100", false);
		_testLuhnCheck("5105105104105100", false);
		
		_testLuhnCheck("4012888888881881", true);
		_testLuhnCheck("4012888898881881", false);
		_testLuhnCheck("4012888878881881", false);
		
		_testLuhnCheck("4222222222222", true);
		_testLuhnCheck("4222322222222", false);
		_testLuhnCheck("4222122222222", false);
	}
	
	public void testBadInput() {
		_test("42", ERR_SHORT);
		_test("59", ERR_SHORT);
		_test("67", ERR_SHORT);
		_test("3707", ERR_SHORT);
		_test("420000000000", ERR_SHORT);
		_test("401000000000", ERR_SHORT);
		
		_test("42222222222220000", ERR_LONG);
		_test("401000000000000000", ERR_LONG);
		_test("4222222222222000000", ERR_LONG);
		_test("40100000000000000000", ERR_LONG);
		_test("422222222222200000000", ERR_LONG);
		
		_test("4010-0000-0000", ERR_NON_NUMERIC);
		_test("4010 0000 0000", ERR_NON_NUMERIC);
		
		_test("30569309025904", ERR_BAD_PREFIX);
		_test("38520000023237", ERR_BAD_PREFIX);
		_test("3530111333300000", ERR_BAD_PREFIX);
		_test("3566002020360505", ERR_BAD_PREFIX);
	}
	
	private void _testGoodInput(String s, String status) {
		_test(s, "card is " + status);
	}
	
	public void testGoodInput() {
		_testGoodInput("4388576018402626", "invalid");
		
		_testGoodInput("5117275325077359", "valid");
		_testGoodInput("5117275325077358", "invalid");
		_testGoodInput("5117275325077350", "invalid");
		
		_testGoodInput("378282246310005", "valid");
		_testGoodInput("378282246310015", "invalid");
		_testGoodInput("378282246310095", "invalid");
		
		_testGoodInput("371449635398431", "valid");
		_testGoodInput("371449635398531", "invalid");
		_testGoodInput("371449635398331", "invalid");
		
		_testGoodInput("378734493671000", "valid");
		_testGoodInput("378734493672000", "invalid");
		_testGoodInput("378734493670000", "invalid");
		
		_testGoodInput("6011111111111117", "valid");
		_testGoodInput("6011111111121117", "invalid");
		_testGoodInput("6011111111101117", "invalid");
		
		_testGoodInput("5555555555554444", "valid");
		_testGoodInput("5555555555654444", "invalid");
		_testGoodInput("5555555555454444", "invalid");
		
		_testGoodInput("5105105105105100", "valid");
		_testGoodInput("5105105106105100", "invalid");
		_testGoodInput("5105105104105100", "invalid");
		
		_testGoodInput("4012888888881881", "valid");
		_testGoodInput("4012888898881881", "invalid");
		_testGoodInput("4012888878881881", "invalid");
		
		_testGoodInput("4222222222222", "valid");
		_testGoodInput("4222322222222", "invalid");
		_testGoodInput("4222122222222", "invalid");
	}
	
}
