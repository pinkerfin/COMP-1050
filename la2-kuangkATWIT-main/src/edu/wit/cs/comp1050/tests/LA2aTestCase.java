package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.Assert;

import edu.wit.cs.comp1050.LA2a;
import junit.framework.TestCase;

public class LA2aTestCase extends TestCase {
	
	private static final String ERR_SEQ = "Invalid sequence";
	private static final String ERR_PIN = "Invalid PIN";

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
	
	private void _test(String s1, String s2, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String input = String.format("%s%n%s%n", s1, s2);
		final String expected = TestSuite.stringOutput(new String[] {
			"Enter value sequence: ",
			"Enter PIN: ",
			msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			LA2a.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testStringIsKDigits(String s, int k, boolean expected) {
		Boolean result = null;
		try {
			result = LA2a.stringIsKDigits(s, k);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testAllDigits(String s, boolean expected) {
		Boolean result = null;
		try {
			result = LA2a.allDigits(s);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testAllUppercaseLetters(String s, boolean expected) {
		Boolean result = null;
		try {
			result = LA2a.allUppercaseLetters(s);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testDigitStringToIntArray(String s, int[] expected) {
		int[] result = null;
		try {
			result = LA2a.digitStringToIntArray(s);
		} catch (ExitException e) {}
		Assert.assertArrayEquals(expected, result);
	}
	
	private void _testLetterToPhone(char c, int expected) {
		Integer result = null;
		try {
			result = LA2a.letterToPhone(c);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testGetResponse(String s, int[] values, int[] expected) {
		int[] result = null;
		try {
			result = LA2a.getResponse(s, values);
		} catch (ExitException e) {}
		Assert.assertArrayEquals(expected, result);
	}
	
	private void _testCountValues(int needle, int[] haystack, int expected) {
		Integer result = null;
		try {
			result = LA2a.countValues(needle, haystack);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testNumPossible(int[] needles, int[] haystack, int expected) {
		Integer result = null;
		try {
			result = LA2a.numPossible(needles, haystack);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	public void testStringIsKDigits() {
		_testStringIsKDigits("", -1, false);
		_testStringIsKDigits("", 0, true);
		_testStringIsKDigits("", 1, false);
		_testStringIsKDigits("", 2, false);
		_testStringIsKDigits("", 3, false);
		_testStringIsKDigits("", 10, false);
		_testStringIsKDigits("", 11, false);
		
		_testStringIsKDigits("a", -1, false);
		_testStringIsKDigits("b", 0, false);
		_testStringIsKDigits("c", 1, true);
		_testStringIsKDigits("d", 2, false);
		_testStringIsKDigits("e", 3, false);
		_testStringIsKDigits("f", 10, false);
		_testStringIsKDigits("f", 11, false);
		
		_testStringIsKDigits("af", -1, false);
		_testStringIsKDigits("be", 0, false);
		_testStringIsKDigits("cd", 1, false);
		_testStringIsKDigits("dc", 2, true);
		_testStringIsKDigits("eb", 3, false);
		_testStringIsKDigits("fa", 10, false);
		_testStringIsKDigits("fa", 11, false);
		
		_testStringIsKDigits("af1", -1, false);
		_testStringIsKDigits("be2", 0, false);
		_testStringIsKDigits("cd3", 1, false);
		_testStringIsKDigits("dc4", 2, false);
		_testStringIsKDigits("eb5", 3, true);
		_testStringIsKDigits("fa6", 10, false);
		_testStringIsKDigits("fa6", 11, false);
		
		_testStringIsKDigits("af1_012345", -1, false);
		_testStringIsKDigits("be2_012345", 0, false);
		_testStringIsKDigits("cd3_012345", 1, false);
		_testStringIsKDigits("dc4_012345", 2, false);
		_testStringIsKDigits("eb5_012345", 3, false);
		_testStringIsKDigits("fa6_012345", 10, true);
		_testStringIsKDigits("fa6_012345", 11, false);
	}
	
	public void testAllDigits() {
		_testAllDigits("", true);
		_testAllDigits("0", true);
		_testAllDigits("1", true);
		_testAllDigits("2", true);
		_testAllDigits("3", true);
		_testAllDigits("4", true);
		_testAllDigits("5", true);
		_testAllDigits("6", true);
		_testAllDigits("7", true);
		_testAllDigits("8", true);
		_testAllDigits("9", true);
		
		_testAllDigits("12", true);
		_testAllDigits("123", true);
		_testAllDigits("1234", true);
		_testAllDigits("12345", true);
		_testAllDigits("123456", true);
		_testAllDigits("1234567", true);
		_testAllDigits("12345678", true);
		_testAllDigits("123456789", true);
		_testAllDigits("1234567890", true);
		_testAllDigits("11123456789011", true);
		
		_testAllDigits(" ", false);
		_testAllDigits("a", false);
		_testAllDigits("b", false);
		_testAllDigits("c", false);
		_testAllDigits("y", false);
		_testAllDigits("z", false);
		_testAllDigits("A", false);
		_testAllDigits("B", false);
		_testAllDigits("C", false);
		_testAllDigits("Y", false);
		_testAllDigits("Z", false);
		_testAllDigits(",", false);
		_testAllDigits(".", false);
		
		_testAllDigits("!", false);
		_testAllDigits("@", false);
		_testAllDigits("#", false);
		_testAllDigits("$", false);
		_testAllDigits("%", false);
		_testAllDigits("^", false);
		_testAllDigits("&", false);
		_testAllDigits("*", false);
		_testAllDigits("(", false);
		_testAllDigits(")", false);
		_testAllDigits("-", false);
		_testAllDigits("_", false);
		_testAllDigits("+", false);
		_testAllDigits("=", false);
		
		_testAllDigits("!@##@%%$^ %**()_+", false);
		_testAllDigits("isudfiask hXXYASFDh!@$%$", false);
		
		_testAllDigits("1 2", false);
		_testAllDigits("1.23", false);
		_testAllDigits("12,34", false);
		_testAllDigits("123(45)", false);
		_testAllDigits("?123456?", false);
		_testAllDigits("1234567!", false);
		_testAllDigits("123^45678", false);
		_testAllDigits("$1,234,567.89", false);
		_testAllDigits("@12.34.56.78.90", false);
		_testAllDigits(" _11123456789011_ ", false);
	}
	
	public void testAllUppercaseLetters() {
		_testAllUppercaseLetters("", true);
		_testAllUppercaseLetters("A", true);
		_testAllUppercaseLetters("B", true);
		_testAllUppercaseLetters("C", true);
		_testAllUppercaseLetters("D", true);
		_testAllUppercaseLetters("E", true);
		_testAllUppercaseLetters("F", true);
		_testAllUppercaseLetters("G", true);
		_testAllUppercaseLetters("H", true);
		_testAllUppercaseLetters("I", true);
		_testAllUppercaseLetters("J", true);
		_testAllUppercaseLetters("K", true);
		_testAllUppercaseLetters("L", true);
		_testAllUppercaseLetters("M", true);
		_testAllUppercaseLetters("N", true);
		_testAllUppercaseLetters("O", true);
		_testAllUppercaseLetters("P", true);
		_testAllUppercaseLetters("Q", true);
		_testAllUppercaseLetters("R", true);
		_testAllUppercaseLetters("S", true);
		_testAllUppercaseLetters("T", true);
		_testAllUppercaseLetters("U", true);
		_testAllUppercaseLetters("V", true);
		_testAllUppercaseLetters("W", true);
		_testAllUppercaseLetters("X", true);
		_testAllUppercaseLetters("Y", true);
		_testAllUppercaseLetters("Z", true);
		
		_testAllUppercaseLetters("AB", true);
		_testAllUppercaseLetters("ABC", true);
		_testAllUppercaseLetters("ABCD", true);
		_testAllUppercaseLetters("ABCDE", true);
		_testAllUppercaseLetters("ABCDEF", true);
		_testAllUppercaseLetters("ABCDEFG", true);
		_testAllUppercaseLetters("ZYABXWCDVUEFTSGHRQIJPOKLNM", true);
		
		_testAllUppercaseLetters(" ", false);
		_testAllUppercaseLetters("a", false);
		_testAllUppercaseLetters("b", false);
		_testAllUppercaseLetters("c", false);
		_testAllUppercaseLetters("y", false);
		_testAllUppercaseLetters("z", false);
		_testAllUppercaseLetters(",", false);
		_testAllUppercaseLetters(".", false);
		
		_testAllUppercaseLetters("!", false);
		_testAllUppercaseLetters("@", false);
		_testAllUppercaseLetters("#", false);
		_testAllUppercaseLetters("$", false);
		_testAllUppercaseLetters("%", false);
		_testAllUppercaseLetters("^", false);
		_testAllUppercaseLetters("&", false);
		_testAllUppercaseLetters("*", false);
		_testAllUppercaseLetters("(", false);
		_testAllUppercaseLetters(")", false);
		_testAllUppercaseLetters("-", false);
		_testAllUppercaseLetters("_", false);
		_testAllUppercaseLetters("+", false);
		_testAllUppercaseLetters("=", false);
		
		_testAllUppercaseLetters("!@##@%%$^ %**()_+", false);
		_testAllUppercaseLetters("isudfiask hXXYASFDh!@$%$", false);
		
		_testAllUppercaseLetters("1 2", false);
		_testAllUppercaseLetters("1.23", false);
		_testAllUppercaseLetters("12,34", false);
		_testAllUppercaseLetters("123(45)", false);
		_testAllUppercaseLetters("?123456?", false);
		_testAllUppercaseLetters("1234567!", false);
		_testAllUppercaseLetters("123^45678", false);
		_testAllUppercaseLetters("$1,234,567.89", false);
		_testAllUppercaseLetters("@12.34.56.78.90", false);
		_testAllUppercaseLetters(" _11123456789011_ ", false);
		
		_testAllUppercaseLetters("A1B", false);
		_testAllUppercaseLetters("AB C?", false);
		_testAllUppercaseLetters("ABCD!", false);
		_testAllUppercaseLetters("AB, CDE", false);
		_testAllUppercaseLetters("_ABCDEF_", false);
		_testAllUppercaseLetters("==ABCD-EFG==", false);
		_testAllUppercaseLetters("ZYasdf A123BXWCDVU234xcvEFTSGHRQIJPOKLNM", false);
	}
	
	public void testDigitStringToIntArray() {
		_testDigitStringToIntArray("", new int[] {});
		_testDigitStringToIntArray("0", new int[] {0});
		_testDigitStringToIntArray("1", new int[] {1});
		_testDigitStringToIntArray("2", new int[] {2});
		_testDigitStringToIntArray("3", new int[] {3});
		_testDigitStringToIntArray("4", new int[] {4});
		_testDigitStringToIntArray("5", new int[] {5});
		_testDigitStringToIntArray("6", new int[] {6});
		_testDigitStringToIntArray("7", new int[] {7});
		_testDigitStringToIntArray("8", new int[] {8});
		_testDigitStringToIntArray("9", new int[] {9});
		
		_testDigitStringToIntArray("00", new int[] {0, 0});
		_testDigitStringToIntArray("12", new int[] {1, 2});
		_testDigitStringToIntArray("212", new int[] {2, 1, 2});
		_testDigitStringToIntArray("321", new int[] {3, 2, 1});
		
		_testDigitStringToIntArray("3231132213", new int[] {3,2,3,1,1,3,2,2,1,3});
	}
	
	public void testLetterToPhone() {
		_testLetterToPhone('A', 2);
		_testLetterToPhone('B', 2);
		_testLetterToPhone('C', 2);
		
		_testLetterToPhone('D', 3);
		_testLetterToPhone('E', 3);
		_testLetterToPhone('F', 3);
		
		_testLetterToPhone('G', 4);
		_testLetterToPhone('H', 4);
		_testLetterToPhone('I', 4);
		
		_testLetterToPhone('J', 5);
		_testLetterToPhone('K', 5);
		_testLetterToPhone('L', 5);
		
		_testLetterToPhone('M', 6);
		_testLetterToPhone('N', 6);
		_testLetterToPhone('O', 6);
		
		_testLetterToPhone('P', 7);
		_testLetterToPhone('Q', 7);
		_testLetterToPhone('R', 7);
		_testLetterToPhone('S', 7);
		
		_testLetterToPhone('T', 8);
		_testLetterToPhone('U', 8);
		_testLetterToPhone('V', 8);
		
		_testLetterToPhone('W', 9);
		_testLetterToPhone('X', 9);
		_testLetterToPhone('Y', 9);
		_testLetterToPhone('Z', 9);
	}
	
	public void testGetResponse() {
		_testGetResponse("", new int[] {}, new int[] {});
		_testGetResponse("", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {});
		_testGetResponse("", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {});
		
		_testGetResponse("A", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {2});
		_testGetResponse("B", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {2});
		_testGetResponse("C", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {2});
		_testGetResponse("D", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {3});
		_testGetResponse("E", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {3});
		_testGetResponse("F", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {3});
		_testGetResponse("G", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {4});
		_testGetResponse("H", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {4});
		_testGetResponse("I", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {4});
		_testGetResponse("J", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {5});
		_testGetResponse("K", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {5});
		_testGetResponse("L", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {5});
		_testGetResponse("M", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {6});
		_testGetResponse("N", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {6});
		_testGetResponse("O", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {6});
		_testGetResponse("P", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {7});
		_testGetResponse("Q", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {7});
		_testGetResponse("R", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {7});
		_testGetResponse("S", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {7});
		_testGetResponse("T", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {8});
		_testGetResponse("U", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {8});
		_testGetResponse("V", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {8});
		_testGetResponse("W", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {9});
		_testGetResponse("X", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {9});
		_testGetResponse("Y", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {9});
		_testGetResponse("Z", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {9});
		
		_testGetResponse("A", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("B", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("C", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("D", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("E", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("F", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("G", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("H", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("I", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("J", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("K", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("L", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("M", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {2});
		_testGetResponse("N", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {2});
		_testGetResponse("O", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {2});
		_testGetResponse("P", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {2});
		_testGetResponse("Q", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {2});
		_testGetResponse("R", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {2});
		_testGetResponse("S", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {2});
		_testGetResponse("T", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("U", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("V", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1});
		_testGetResponse("W", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("X", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("Y", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		_testGetResponse("Z", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3});
		
		_testGetResponse("HELLO", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {4, 3, 5, 5, 6});
		_testGetResponse("HELLO", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {1, 1, 3, 3, 2});
		
		_testGetResponse("BAD", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {2, 2, 3});
		_testGetResponse("ABE", new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[] {2, 2, 3});
		
		_testGetResponse("BAD", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3, 3, 1});
		_testGetResponse("ABE", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3, 3, 1});
		_testGetResponse("BAG", new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, new int[] {3, 3, 1});
	}
	
	private void _testGoodProgram(String seq, String pin, String r) {
		_test(seq, pin, "Response: " + r);
	}
	
	public void testProgram() {
		_test("", "HELLO", ERR_SEQ);
		_test("0", "HELLO", ERR_SEQ);
		_test("01", "HELLO", ERR_SEQ);
		_test("012", "HELLO", ERR_SEQ);
		_test("0123", "HELLO", ERR_SEQ);
		_test("12345", "HELLO", ERR_SEQ);
		_test("234567", "HELLO", ERR_SEQ);
		_test("3456789", "HELLO", ERR_SEQ);
		_test("01234567", "HELLO", ERR_SEQ);
		_test("876543210", "HELLO", ERR_SEQ);
		
		_test(" 012345678", "HELLO", ERR_SEQ);
		_test("0.12345678", "HELLO", ERR_SEQ);
		_test("01,2345678", "HELLO", ERR_SEQ);
		_test("012<345678", "HELLO", ERR_SEQ);
		_test("0123?45678", "HELLO", ERR_SEQ);
		_test("01234!5678", "HELLO", ERR_SEQ);
		_test("0123456_78", "HELLO", ERR_SEQ);
		_test("01234567 8", "HELLO", ERR_SEQ);
		_test("-01234567_", "HELLO", ERR_SEQ);
		
		_test("0123456789", " ", ERR_PIN);
		_test("0123456789", "Hello", ERR_PIN);
		_test("0123456789", "HELLO?", ERR_PIN);
		_test("0123456789", "hello!", ERR_PIN);
		_test("0123456789", "thingsandstuffs", ERR_PIN);
		
		_testGoodProgram("0123456789", "HELLO", "43556");
		_testGoodProgram("3231132213", "HELLO", "11332");
		
		_testGoodProgram("0123456789", "BAD", "223");
		_testGoodProgram("0123456789", "ABE", "223");
		
		_testGoodProgram("3231132213", "BAD", "331");
		_testGoodProgram("3231132213", "ABE", "331");
		_testGoodProgram("3231132213", "BAG", "331");
		_testGoodProgram("3231132213", "AGOT", "3121");
	}
	
	public void testBonusCountValues() {
		_testCountValues(1, new int[] {}, 0);
		_testCountValues(1, new int[] {0}, 0);
		_testCountValues(1, new int[] {0, 0, 0}, 0);
		_testCountValues(2, new int[] {0, 0, 0}, 0);
		_testCountValues(3, new int[] {0, 0, 0}, 0);
		
		_testCountValues(0, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(1, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(2, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(3, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(4, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(5, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(6, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(7, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(8, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testCountValues(9, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		
		_testCountValues(0, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 0);
		_testCountValues(1, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 3);
		_testCountValues(2, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 3);
		_testCountValues(3, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 4);
	}
	
	public void testBonusNumPossible() {
		_testNumPossible(new int[] {}, new int[] {}, 1);
		_testNumPossible(new int[] {}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		
		_testNumPossible(new int[] {0}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {2}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {3}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {4}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {5}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {6}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {7}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {8}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {9}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		
		_testNumPossible(new int[] {0, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {1, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {2, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {3, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {0, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {4, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {5, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {6, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {7, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {8, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		_testNumPossible(new int[] {9, -1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0);
		
		_testNumPossible(new int[] {0, 1}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {1, 9}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {2, 8}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {3, 7}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {6, 4}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		_testNumPossible(new int[] {5, 5}, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 1);
		
		_testNumPossible(new int[] {}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 1);
		_testNumPossible(new int[] {0}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 0);
		_testNumPossible(new int[] {1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 3);
		_testNumPossible(new int[] {2}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 3);
		_testNumPossible(new int[] {3}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 4);
		
		_testNumPossible(new int[] {0, 1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 0);
		_testNumPossible(new int[] {1, 0}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 0);
		
		_testNumPossible(new int[] {1, 1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 9);
		_testNumPossible(new int[] {1, 1, 1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 27);
		_testNumPossible(new int[] {1, 2}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 9);
		_testNumPossible(new int[] {2, 1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 9);
		_testNumPossible(new int[] {2, 2}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 9);
		_testNumPossible(new int[] {1, 2, 1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 27);
		_testNumPossible(new int[] {2, 1, 2}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 27);
		_testNumPossible(new int[] {1, 3}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 12);
		_testNumPossible(new int[] {3, 1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 12);
		_testNumPossible(new int[] {3, 3}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 16);
		_testNumPossible(new int[] {1, 2, 3}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 36);
		_testNumPossible(new int[] {1, 3, 2}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 36);
		
		_testNumPossible(new int[] {3, 1, 2, 1}, new int[] {3, 2, 3, 1, 1, 3, 2, 2, 1, 3}, 108);
	}
	
}
