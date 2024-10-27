
package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA3a;
import junit.framework.TestCase;

public class PA3aTestCase extends TestCase {
	
	private static final String ERR_USAGE = "Please supply at least one argument at the command line.";
	
	private static final String[] A_EMPTY_ARRAY = {};
	
	private static final String[] A_EMPTY_STRING = {new String("")};
	private static final String[] A_SPACE = {new String(" ")};
	private static final String[] A_A = {new String("a")};
	private static final String[] A_B = {new String("B")};
	private static final String[] A_C = {new String("c")};
	private static final String[] A_ONE = {new String("1")};
	private static final String[] A_TWO = {new String("two")};
	private static final String[] A_THREE = {new String("THREE")};
	
	private static final String[] A_ABC123_1 = {
		new String("a"),new String("b"),new String("c"),
		new String("1"),new String("2"),new String("3"),
	};
	
	private static final String[] A_ABC123_2 = {
		new String("a b c 1 2 3"),
	};
	
	private static final String[] A_ABC123_3 = {
		new String("a"),new String("bc"),
		new String("1"),new String("TWO"),new String("3hree"),
	};
	
	private static final String[] A_ABC123_4 = {
		new String("a"),new String("b"),new String("c"),
		new String("1"),new String("two"),new String("3"),
		new String("ab"),new String("ac"),new String("bc"),
		new String("abcdefghijklmnopqrstuvwxyz"),
	};
	
	private static final String[] A_HELLO = {
		new String("hello"), new String("world!"),	
	};
	
	// https://www.poets.org/poetsorg/poem/5-7-5
	private static final String[] A_HAIKU_1 = {
		new String("round"),new String("lumps"),new String("of"),new String("cells"),new String("grow"),
		new String("up"),new String("to"),new String("love"),new String("porridge"),new String("later"),
		new String("become"),new String("The"),new String("Supremes"),
	};
	
	private static final String[] A_HAIKU_2 = {
		new String("round lumps of cells grow"),
		new String("up to love porridge   later"),
		new String("become The Supremes"),
	};
	
	private static final String[] A_QUEEN = {
		new String("I"),new String("see"),new String("a"),new String("little"),new String("silhouetto"),new String("of"),new String("a"),new String("man"),
		new String("Scaramouche"),new String("Scaramouche"),new String("will"),new String("you"),new String("do"),new String("the"),new String("Fandango"),
		new String("Thunderbolt"),new String("and"),new String("lightning"),new String("very"),new String("very"),new String("fright'ning"),new String("me"),	
	};
	
	private static final String[] A_DUNE = {
		new String("I"),new String("must"),new String("not"),new String("fear"),
		new String("Fear"),new String("is"),new String("the"),new String("mind-killer"),
		new String("Fear"),new String("is"),new String("the"),new String("little-death"),new String("that"),new String("brings"),new String("total"),new String("obliteration"),
		new String("I"),new String("will"),new String("face"),new String("my"),new String("fear"),
		new String("I"),new String("will"),new String("permit"),new String("it"),new String("to"),new String("pass"),new String("over"),new String("me"),new String("and"),new String("through"),new String("me"),
		new String("And"),new String("when"),new String("it"),new String("has"),new String("gone"),new String("past"),new String("I"),new String("will"),new String("turn"),new String("the"),new String("inner"),new String("eye"),new String("to"),new String("see"),new String("its"),new String("path"),
		new String("Where"),new String("the"),new String("fear"),new String("has"),new String("gone"),new String("there"),new String("will"),new String("be"),new String("nothing"),
		new String("Only"),new String("I"),new String("will"),new String("remain"),
	};
	
	//
	
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
			PA3a.main(a);
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testValidArgs(String[] a, boolean expected) {
		Boolean result = null;
		try {
			result = PA3a.validArgs(a);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testPrintWithSeparator(String[] a, String o, String btw, String c, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		System.setIn(new ByteArrayInputStream("".getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA3a.printWithSeparator(a, o, btw, c);
		} catch (ExitException e) {}
		
		assertEquals(msg, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testFlexibleQuote(String s, char b, char a, String expected) {
		String result = null;
		try {
			result = PA3a.flexibleQuote(s, b, a);
		} catch (ExitException e) {}
		assertEquals(expected, result);
	}
	
	private void _testOneOrBothOrNone(String f, String l, char o, char c, String expected) {
		String result = null;
		try {
			result = PA3a.oneOrBothOrNone(f, l, o, c);
		} catch (ExitException e) {}
		assertEquals(expected, result);
	}
	
	private void _testHasDuplicates(String[] a, boolean expected) {
		Boolean result = null;
		try {
			result = PA3a.hasDuplicates(a);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testSumOfElementLengths(String[] a, int expected) {
		Integer result = null;
		try {
			result = PA3a.sumOfElementLengths(a);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testAverageAsInt(int s, int c, int expected) {
		Integer result = null;
		try {
			result = PA3a.averageAsInt(s, c);
		} catch (ExitException e) {}
		assertEquals(expected, (int) result);
	}
	
	private void _testLastOfLength(String[] a, int len, String expected) {
		String result = null;
		try {
			result = PA3a.lastOfLength(a, len);
		} catch (ExitException e) {}
		assertSame(expected, result);
	}
	
	private void _testFirstOfLength(String[] a, int len, String expected) {
		String result = null;
		try {
			result = PA3a.firstOfLength(a, len);
		} catch (ExitException e) {}
		assertSame(expected, result);
	}
	
	private void _testLastLongestElement(String[] a, String expected) {
		String result = null;
		try {
			result = PA3a.lastLongestElement(a);
		} catch (ExitException e) {}
		assertSame(expected, result);
	}
	
	private void _testFirstLongestElement(String[] a, String expected) {
		String result = null;
		try {
			result = PA3a.firstLongestElement(a);
		} catch (ExitException e) {}
		assertSame(expected, result);
	}
	
	private void _testLastShortestElement(String[] a, String expected) {
		String result = null;
		try {
			result = PA3a.lastShortestElement(a);
		} catch (ExitException e) {}
		assertSame(expected, result);
	}
	
	private void _testFirstShortestElement(String[] a, String expected) {
		String result = null;
		try {
			result = PA3a.firstShortestElement(a);
		} catch (ExitException e) {}
		assertSame(expected, result);
	}
	
	public void testUsage() {
		_testValidArgs(A_EMPTY_ARRAY, false);
		
		_testValidArgs(A_EMPTY_STRING, true);
		
		_testValidArgs(A_SPACE, true);
		_testValidArgs(A_A, true);
		_testValidArgs(A_B, true);
		_testValidArgs(A_C, true);
		_testValidArgs(A_ONE, true);
		_testValidArgs(A_TWO, true);
		_testValidArgs(A_THREE, true);
		
		_testValidArgs(A_ABC123_1, true);
		_testValidArgs(A_ABC123_2, true);
		_testValidArgs(A_ABC123_3, true);
		_testValidArgs(A_ABC123_4, true);
		
		_testValidArgs(A_HELLO, true);
		
		_testValidArgs(A_HAIKU_1, true);
		_testValidArgs(A_QUEEN, true);
		_testValidArgs(A_DUNE, true);
	}
	
	public void testPrintWithSeparator() {
		_testPrintWithSeparator(A_EMPTY_ARRAY, "", "", "", "");
		
		_testPrintWithSeparator(A_EMPTY_STRING, "", "", "", "");
		_testPrintWithSeparator(A_EMPTY_STRING, ".", "", "", ".");
		_testPrintWithSeparator(A_EMPTY_STRING, "", "", ".", ".");
		_testPrintWithSeparator(A_EMPTY_STRING, "", ".", "", "");
		_testPrintWithSeparator(A_EMPTY_STRING, ".", ".", ".", "..");
		
		_testPrintWithSeparator(A_SPACE, "", "", "", " ");
		_testPrintWithSeparator(A_SPACE, ".", "", "", ". ");
		_testPrintWithSeparator(A_SPACE, "", "", ".", " .");
		_testPrintWithSeparator(A_SPACE, "", ".", "", " ");
		_testPrintWithSeparator(A_SPACE, ".", ".", ".", ". .");
		
		_testPrintWithSeparator(A_A, "", "", "", "a");
		_testPrintWithSeparator(A_A, ".", "", "", ".a");
		_testPrintWithSeparator(A_A, "", "", ".", "a.");
		_testPrintWithSeparator(A_A, "", ".", "", "a");
		_testPrintWithSeparator(A_A, ".", ".", ".", ".a.");
		
		_testPrintWithSeparator(A_ABC123_1, "", "", "", "abc123");
		_testPrintWithSeparator(A_ABC123_1, "", " ", "", "a b c 1 2 3");
		_testPrintWithSeparator(A_ABC123_1, "<", "", ">", "<abc123>");
		_testPrintWithSeparator(A_ABC123_1, "\"", ", ", "\"", "\"a, b, c, 1, 2, 3\"");
		
		_testPrintWithSeparator(A_HAIKU_2, "<<<", String.format("%n"), ">>>", String.format("<<<%s%n%s%n%s>>>", A_HAIKU_2[0], A_HAIKU_2[1], A_HAIKU_2[2]));
	}
	
	public void testFlexibleQuote() {
		_testFlexibleQuote("", ' ', ' ', "  ");
		_testFlexibleQuote(" ", ' ', ' ', "   ");
		
		_testFlexibleQuote("", '<', '>', "<>");
		_testFlexibleQuote(" ", '<', '>', "< >");
		_testFlexibleQuote("", '(', ')', "()");
		_testFlexibleQuote(" ", '(', ')', "( )");
		_testFlexibleQuote("", '[', ']', "[]");
		_testFlexibleQuote(" ", '[', ']', "[ ]");
		
		_testFlexibleQuote("Gastromancy", '`', '\'', "`Gastromancy\'");
	}
	
	public void testOneOrBothOrNone() {
		final String a1 = new String("a");
		final String a2 = new String("a");
		
		_testOneOrBothOrNone(null, null, '<', '>', "none");
		_testOneOrBothOrNone(null, null, '(', ']', "none");
		_testOneOrBothOrNone(null, null, '_', '_', "none");
		
		_testOneOrBothOrNone(a1, null, '<', '>', "<a>");
		_testOneOrBothOrNone(a1, null, '(', ']', "(a]");
		_testOneOrBothOrNone(a1, null, '_', '_', "_a_");
		_testOneOrBothOrNone(a2, null, '<', '>', "<a>");
		_testOneOrBothOrNone(a2, null, '(', ']', "(a]");
		_testOneOrBothOrNone(a2, null, '_', '_', "_a_");
		_testOneOrBothOrNone(null, a1, '<', '>', "<a>");
		_testOneOrBothOrNone(null, a1, '(', ']', "(a]");
		_testOneOrBothOrNone(null, a1, '_', '_', "_a_");
		_testOneOrBothOrNone(null, a2, '<', '>', "<a>");
		_testOneOrBothOrNone(null, a2, '(', ']', "(a]");
		_testOneOrBothOrNone(null, a2, '_', '_', "_a_");
		
		_testOneOrBothOrNone("first", null, '_', '_', "_first_");
		_testOneOrBothOrNone(null, "last", '!', '?', "!last?");
		
		_testOneOrBothOrNone(a1, a1, '<', '>', "<a>");
		_testOneOrBothOrNone(a2, a2, '<', '>', "<a>");
		_testOneOrBothOrNone(a1, a2, '<', '>', "first=<a>, last=<a>");
		_testOneOrBothOrNone(a2, a1, '<', '>', "first=<a>, last=<a>");
		
		_testOneOrBothOrNone(a1, a1, '(', ']', "(a]");
		_testOneOrBothOrNone(a2, a2, '(', ']', "(a]");
		_testOneOrBothOrNone(a1, a2, '(', ']', "first=(a], last=(a]");
		_testOneOrBothOrNone(a2, a1, '(', ']', "first=(a], last=(a]");
		
		_testOneOrBothOrNone("first", "last", '_', '_', "first=_first_, last=_last_");
		_testOneOrBothOrNone("last", "first", '_', '_', "first=_last_, last=_first_");
	}
	
	public void testHasDuplicates() {
		_testHasDuplicates(A_EMPTY_ARRAY, false);
		_testHasDuplicates(A_EMPTY_STRING, false);
		_testHasDuplicates(A_SPACE, false);
		
		_testHasDuplicates(new String[] {"a", "a"}, true);
		_testHasDuplicates(new String[] {"a", "b"}, false);
		_testHasDuplicates(new String[] {"b", "a"}, false);
		_testHasDuplicates(new String[] {"b", "b"}, true);
		_testHasDuplicates(new String[] {"a", "a", "a"}, true);
		_testHasDuplicates(new String[] {"a", "a", "b"}, true);
		_testHasDuplicates(new String[] {"a", "b", "a"}, true);
		_testHasDuplicates(new String[] {"b", "a", "a"}, true);
		_testHasDuplicates(new String[] {"a", "b", "b"}, true);
		_testHasDuplicates(new String[] {"b", "a", "b"}, true);
		_testHasDuplicates(new String[] {"b", "b", "b"}, true);
		_testHasDuplicates(new String[] {"a", "b", "c"}, false);
		
		_testHasDuplicates(A_HAIKU_1, false);
		_testHasDuplicates(A_HAIKU_2, false);
		_testHasDuplicates(A_QUEEN, true);
		_testHasDuplicates(A_DUNE, true);
	}
	
	public void testSumOfElementLengths() {
		_testSumOfElementLengths(A_EMPTY_ARRAY, 0);
		_testSumOfElementLengths(A_EMPTY_STRING, 0);
		
		_testSumOfElementLengths(A_SPACE, 1);
		_testSumOfElementLengths(A_A, 1);
		_testSumOfElementLengths(A_B, 1);
		_testSumOfElementLengths(A_C, 1);
		_testSumOfElementLengths(A_ONE, 1);
		_testSumOfElementLengths(A_TWO, 3);
		_testSumOfElementLengths(A_THREE, 5);
		
		_testSumOfElementLengths(A_ABC123_1, 6);
		_testSumOfElementLengths(A_ABC123_2, 11);
		_testSumOfElementLengths(A_ABC123_3, 12);
		_testSumOfElementLengths(A_ABC123_4, 40);
		
		_testSumOfElementLengths(A_HELLO, 11);
		
		_testSumOfElementLengths(A_HAIKU_1, 59);
		_testSumOfElementLengths(A_QUEEN, 113);
		_testSumOfElementLengths(A_DUNE, 236);
	}
	
	public void testAverageAsInt() {
		_testAverageAsInt(0, 1, 0);
		
		_testAverageAsInt(1, 1, 1);
		_testAverageAsInt(10, 10, 1);
		_testAverageAsInt(11, 10, 1);
		_testAverageAsInt(12, 10, 1);
		_testAverageAsInt(13, 10, 1);
		_testAverageAsInt(14, 10, 1);
		_testAverageAsInt(15, 10, 2);
		_testAverageAsInt(16, 10, 2);
		_testAverageAsInt(17, 10, 2);
		_testAverageAsInt(18, 10, 2);
		_testAverageAsInt(19, 10, 2);
		_testAverageAsInt(20, 10, 2);
		_testAverageAsInt(21, 10, 2);
		
		_testAverageAsInt(40, 10, 4);
		_testAverageAsInt(11, 2, 6);
		_testAverageAsInt(59, 13, 5);
		_testAverageAsInt(113, 22, 5);
		_testAverageAsInt(236, 61, 4);
	}
	
	public void testLastOfLength() {
		final String a1 = new String("a");
		final String a2 = new String("a");
		
		_testLastOfLength(new String[] {a1, a2}, 0, null);
		_testLastOfLength(new String[] {a2, a1}, 0, null);
		_testLastOfLength(new String[] {a1, a2}, 1, a2);
		_testLastOfLength(new String[] {a1, "", "", "", "", a2}, 1, a2);
		_testLastOfLength(new String[] {a2, a1}, 1, a1);
		_testLastOfLength(new String[] {a2, "", "", "", "", a1}, 1, a1);
		
		_testLastOfLength(A_EMPTY_ARRAY, -1, null);
		_testLastOfLength(A_EMPTY_ARRAY, 0, null);
		_testLastOfLength(A_EMPTY_ARRAY, 1, null);
		_testLastOfLength(A_EMPTY_ARRAY, 2, null);
		
		_testLastOfLength(A_EMPTY_STRING, -1, null);
		_testLastOfLength(A_EMPTY_STRING, 0, A_EMPTY_STRING[0]);
		_testLastOfLength(A_EMPTY_STRING, 1, null);
		_testLastOfLength(A_EMPTY_STRING, 2, null);
		
		for (String[] a : new String[][] {A_SPACE, A_A, A_B, A_C, A_ONE}) {
			_testLastOfLength(a, -1, null);
			_testLastOfLength(a, 0, null);
			_testLastOfLength(a, 1, a[0]);
			_testLastOfLength(a, 2, null);
		}		
		
		_testLastOfLength(A_TWO, -1, null);
		_testLastOfLength(A_TWO, 0, null);
		_testLastOfLength(A_TWO, 1, null);
		_testLastOfLength(A_TWO, 2, null);
		_testLastOfLength(A_TWO, 3, A_TWO[0]);
		_testLastOfLength(A_TWO, 4, null);
		
		_testLastOfLength(A_THREE, -1, null);
		_testLastOfLength(A_THREE, 0, null);
		_testLastOfLength(A_THREE, 1, null);
		_testLastOfLength(A_THREE, 2, null);
		_testLastOfLength(A_THREE, 3, null);
		_testLastOfLength(A_THREE, 4, null);
		_testLastOfLength(A_THREE, 5, A_THREE[0]);
		_testLastOfLength(A_THREE, 6, null);
		
		_testLastOfLength(A_ABC123_1, -1, null);
		_testLastOfLength(A_ABC123_1, 0, null);
		_testLastOfLength(A_ABC123_1, 1, A_ABC123_1[5]);
		_testLastOfLength(A_ABC123_1, 2, null);
		_testLastOfLength(A_ABC123_1, 3, null);
		_testLastOfLength(A_ABC123_1, 4, null);
		_testLastOfLength(A_ABC123_1, 5, null);
		_testLastOfLength(A_ABC123_1, 10, null);
		_testLastOfLength(A_ABC123_1, 11, null);
		_testLastOfLength(A_ABC123_1, 12, null);
		_testLastOfLength(A_ABC123_1, 24, null);
		_testLastOfLength(A_ABC123_1, 25, null);
		_testLastOfLength(A_ABC123_1, 26, null);
		
		_testLastOfLength(A_ABC123_2, -1, null);
		_testLastOfLength(A_ABC123_2, 0, null);
		_testLastOfLength(A_ABC123_2, 1, null);
		_testLastOfLength(A_ABC123_2, 2, null);
		_testLastOfLength(A_ABC123_2, 3, null);
		_testLastOfLength(A_ABC123_2, 4, null);
		_testLastOfLength(A_ABC123_2, 5, null);
		_testLastOfLength(A_ABC123_2, 10, null);
		_testLastOfLength(A_ABC123_2, 11, A_ABC123_2[0]);
		_testLastOfLength(A_ABC123_2, 12, null);
		_testLastOfLength(A_ABC123_2, 24, null);
		_testLastOfLength(A_ABC123_2, 25, null);
		_testLastOfLength(A_ABC123_2, 26, null);
		
		_testLastOfLength(A_ABC123_3, -1, null);
		_testLastOfLength(A_ABC123_3, 0, null);
		_testLastOfLength(A_ABC123_3, 1, A_ABC123_3[2]);
		_testLastOfLength(A_ABC123_3, 2, A_ABC123_3[1]);
		_testLastOfLength(A_ABC123_3, 3, A_ABC123_3[3]);
		_testLastOfLength(A_ABC123_3, 4, null);
		_testLastOfLength(A_ABC123_3, 5, A_ABC123_3[4]);
		_testLastOfLength(A_ABC123_3, 10, null);
		_testLastOfLength(A_ABC123_3, 11, null);
		_testLastOfLength(A_ABC123_3, 12, null);
		_testLastOfLength(A_ABC123_3, 24, null);
		_testLastOfLength(A_ABC123_3, 25, null);
		_testLastOfLength(A_ABC123_3, 26, null);
		
		_testLastOfLength(A_ABC123_4, -1, null);
		_testLastOfLength(A_ABC123_4, 0, null);
		_testLastOfLength(A_ABC123_4, 1, A_ABC123_4[5]);
		_testLastOfLength(A_ABC123_4, 2, A_ABC123_4[8]);
		_testLastOfLength(A_ABC123_4, 3, A_ABC123_4[4]);
		_testLastOfLength(A_ABC123_4, 4, null);
		_testLastOfLength(A_ABC123_4, 5, null);
		_testLastOfLength(A_ABC123_4, 10, null);
		_testLastOfLength(A_ABC123_4, 11, null);
		_testLastOfLength(A_ABC123_4, 12, null);
		_testLastOfLength(A_ABC123_4, 24, null);
		_testLastOfLength(A_ABC123_4, 25, null);
		_testLastOfLength(A_ABC123_4, 26, A_ABC123_4[9]);
		
		_testLastOfLength(A_HELLO, -1, null);
		_testLastOfLength(A_HELLO, 0, null);
		_testLastOfLength(A_HELLO, 1, null);
		_testLastOfLength(A_HELLO, 2, null);
		_testLastOfLength(A_HELLO, 3, null);
		_testLastOfLength(A_HELLO, 4, null);
		_testLastOfLength(A_HELLO, 5, A_HELLO[0]);
		_testLastOfLength(A_HELLO, 6, A_HELLO[1]);
		_testLastOfLength(A_HELLO, 7, null);
	}
	
	public void testFirstOfLength() {
		final String a1 = new String("a");
		final String a2 = new String("a");
		
		_testFirstOfLength(new String[] {a1, a2}, 0, null);
		_testFirstOfLength(new String[] {a2, a1}, 0, null);
		_testFirstOfLength(new String[] {a1, a2}, 1, a1);
		_testFirstOfLength(new String[] {a1, "", "", "", "", a2}, 1, a1);
		_testFirstOfLength(new String[] {a2, a1}, 1, a2);
		_testFirstOfLength(new String[] {a2, "", "", "", "", a1}, 1, a2);
		
		_testFirstOfLength(A_EMPTY_ARRAY, -1, null);
		_testFirstOfLength(A_EMPTY_ARRAY, 0, null);
		_testFirstOfLength(A_EMPTY_ARRAY, 1, null);
		_testFirstOfLength(A_EMPTY_ARRAY, 2, null);
		
		_testFirstOfLength(A_EMPTY_STRING, -1, null);
		_testFirstOfLength(A_EMPTY_STRING, 0, A_EMPTY_STRING[0]);
		_testFirstOfLength(A_EMPTY_STRING, 1, null);
		_testFirstOfLength(A_EMPTY_STRING, 2, null);
		
		for (String[] a : new String[][] {A_SPACE, A_A, A_B, A_C, A_ONE}) {
			_testFirstOfLength(a, -1, null);
			_testFirstOfLength(a, 0, null);
			_testFirstOfLength(a, 1, a[0]);
			_testFirstOfLength(a, 2, null);
		}		
		
		_testFirstOfLength(A_TWO, -1, null);
		_testFirstOfLength(A_TWO, 0, null);
		_testFirstOfLength(A_TWO, 1, null);
		_testFirstOfLength(A_TWO, 2, null);
		_testFirstOfLength(A_TWO, 3, A_TWO[0]);
		_testFirstOfLength(A_TWO, 4, null);
		
		_testFirstOfLength(A_THREE, -1, null);
		_testFirstOfLength(A_THREE, 0, null);
		_testFirstOfLength(A_THREE, 1, null);
		_testFirstOfLength(A_THREE, 2, null);
		_testFirstOfLength(A_THREE, 3, null);
		_testFirstOfLength(A_THREE, 4, null);
		_testFirstOfLength(A_THREE, 5, A_THREE[0]);
		_testFirstOfLength(A_THREE, 6, null);
		
		_testFirstOfLength(A_ABC123_1, -1, null);
		_testFirstOfLength(A_ABC123_1, 0, null);
		_testFirstOfLength(A_ABC123_1, 1, A_ABC123_1[0]);
		_testFirstOfLength(A_ABC123_1, 2, null);
		_testFirstOfLength(A_ABC123_1, 3, null);
		_testFirstOfLength(A_ABC123_1, 4, null);
		_testFirstOfLength(A_ABC123_1, 5, null);
		_testFirstOfLength(A_ABC123_1, 10, null);
		_testFirstOfLength(A_ABC123_1, 11, null);
		_testFirstOfLength(A_ABC123_1, 12, null);
		_testFirstOfLength(A_ABC123_1, 24, null);
		_testFirstOfLength(A_ABC123_1, 25, null);
		_testFirstOfLength(A_ABC123_1, 26, null);
		
		_testFirstOfLength(A_ABC123_2, -1, null);
		_testFirstOfLength(A_ABC123_2, 0, null);
		_testFirstOfLength(A_ABC123_2, 1, null);
		_testFirstOfLength(A_ABC123_2, 2, null);
		_testFirstOfLength(A_ABC123_2, 3, null);
		_testFirstOfLength(A_ABC123_2, 4, null);
		_testFirstOfLength(A_ABC123_2, 5, null);
		_testFirstOfLength(A_ABC123_2, 10, null);
		_testFirstOfLength(A_ABC123_2, 11, A_ABC123_2[0]);
		_testFirstOfLength(A_ABC123_2, 12, null);
		_testFirstOfLength(A_ABC123_2, 24, null);
		_testFirstOfLength(A_ABC123_2, 25, null);
		_testFirstOfLength(A_ABC123_2, 26, null);
		
		_testFirstOfLength(A_ABC123_3, -1, null);
		_testFirstOfLength(A_ABC123_3, 0, null);
		_testFirstOfLength(A_ABC123_3, 1, A_ABC123_3[0]);
		_testFirstOfLength(A_ABC123_3, 2, A_ABC123_3[1]);
		_testFirstOfLength(A_ABC123_3, 3, A_ABC123_3[3]);
		_testFirstOfLength(A_ABC123_3, 4, null);
		_testFirstOfLength(A_ABC123_3, 5, A_ABC123_3[4]);
		_testFirstOfLength(A_ABC123_3, 10, null);
		_testFirstOfLength(A_ABC123_3, 11, null);
		_testFirstOfLength(A_ABC123_3, 12, null);
		_testFirstOfLength(A_ABC123_3, 24, null);
		_testFirstOfLength(A_ABC123_3, 25, null);
		_testFirstOfLength(A_ABC123_3, 26, null);
		
		_testFirstOfLength(A_ABC123_4, -1, null);
		_testFirstOfLength(A_ABC123_4, 0, null);
		_testFirstOfLength(A_ABC123_4, 1, A_ABC123_4[0]);
		_testFirstOfLength(A_ABC123_4, 2, A_ABC123_4[6]);
		_testFirstOfLength(A_ABC123_4, 3, A_ABC123_4[4]);
		_testFirstOfLength(A_ABC123_4, 4, null);
		_testFirstOfLength(A_ABC123_4, 5, null);
		_testFirstOfLength(A_ABC123_4, 10, null);
		_testFirstOfLength(A_ABC123_4, 11, null);
		_testFirstOfLength(A_ABC123_4, 12, null);
		_testFirstOfLength(A_ABC123_4, 24, null);
		_testFirstOfLength(A_ABC123_4, 25, null);
		_testFirstOfLength(A_ABC123_4, 26, A_ABC123_4[9]);
		
		_testFirstOfLength(A_HELLO, -1, null);
		_testFirstOfLength(A_HELLO, 0, null);
		_testFirstOfLength(A_HELLO, 1, null);
		_testFirstOfLength(A_HELLO, 2, null);
		_testFirstOfLength(A_HELLO, 3, null);
		_testFirstOfLength(A_HELLO, 4, null);
		_testFirstOfLength(A_HELLO, 5, A_HELLO[0]);
		_testFirstOfLength(A_HELLO, 6, A_HELLO[1]);
		_testFirstOfLength(A_HELLO, 7, null);
	}
	
	public void testLastLongestElement() {
		for (String[] a : new String[][] {
			A_EMPTY_STRING, A_SPACE, 
			A_A, A_B, A_C, 
			A_ONE, A_TWO, A_THREE}) {
			_testLastLongestElement(a, a[0]);
		}
		
		_testLastLongestElement(A_ABC123_1, A_ABC123_1[5]);
		_testLastLongestElement(A_ABC123_2, A_ABC123_2[0]);
		_testLastLongestElement(A_ABC123_3, A_ABC123_3[4]);
		_testLastLongestElement(A_ABC123_4, A_ABC123_4[9]);
		
		_testLastLongestElement(A_HELLO, A_HELLO[1]);
		
		_testLastLongestElement(A_HAIKU_1, A_HAIKU_1[12]);
		_testLastLongestElement(A_HAIKU_2, A_HAIKU_2[1]);
		
		_testLastLongestElement(A_QUEEN, A_QUEEN[20]);
		_testLastLongestElement(A_DUNE, A_DUNE[15]);	
	}
	
	public void testFirstLongestElement() {
		for (String[] a : new String[][] {
			A_EMPTY_STRING, A_SPACE, 
			A_A, A_B, A_C, 
			A_ONE, A_TWO, A_THREE}) {
			_testFirstLongestElement(a, a[0]);
		}
		
		_testFirstLongestElement(A_ABC123_1, A_ABC123_1[0]);
		_testFirstLongestElement(A_ABC123_2, A_ABC123_2[0]);
		_testFirstLongestElement(A_ABC123_3, A_ABC123_3[4]);
		_testFirstLongestElement(A_ABC123_4, A_ABC123_4[9]);
		
		_testFirstLongestElement(A_HELLO, A_HELLO[1]);
		
		_testFirstLongestElement(A_HAIKU_1, A_HAIKU_1[8]);
		_testFirstLongestElement(A_HAIKU_2, A_HAIKU_2[1]);
		
		_testFirstLongestElement(A_QUEEN, A_QUEEN[8]);
		_testFirstLongestElement(A_DUNE, A_DUNE[11]);	
	}
	
	public void testLastShortestElement() {
		for (String[] a : new String[][] {
			A_EMPTY_STRING, A_SPACE, 
			A_A, A_B, A_C, 
			A_ONE, A_TWO, A_THREE}) {
			_testLastShortestElement(a, a[0]);
		}
		
		_testLastShortestElement(A_ABC123_1, A_ABC123_1[5]);
		_testLastShortestElement(A_ABC123_2, A_ABC123_2[0]);
		_testLastShortestElement(A_ABC123_3, A_ABC123_3[2]);
		_testLastShortestElement(A_ABC123_4, A_ABC123_4[5]);
		
		_testLastShortestElement(A_HELLO, A_HELLO[0]);
		
		_testLastShortestElement(A_HAIKU_1, A_HAIKU_1[6]);
		_testLastShortestElement(A_HAIKU_2, A_HAIKU_2[2]);
		
		_testLastShortestElement(A_QUEEN, A_QUEEN[6]);
		_testLastShortestElement(A_DUNE, A_DUNE[58]);	
	}
	
	public void testFirstShortestElement() {
		for (String[] a : new String[][] {
			A_EMPTY_STRING, A_SPACE, 
			A_A, A_B, A_C, 
			A_ONE, A_TWO, A_THREE}) {
			_testFirstShortestElement(a, a[0]);
		}
		
		_testFirstShortestElement(A_ABC123_1, A_ABC123_1[0]);
		_testFirstShortestElement(A_ABC123_2, A_ABC123_2[0]);
		_testFirstShortestElement(A_ABC123_3, A_ABC123_3[0]);
		_testFirstShortestElement(A_ABC123_4, A_ABC123_4[0]);
		
		_testFirstShortestElement(A_HELLO, A_HELLO[0]);
		
		_testFirstShortestElement(A_HAIKU_1, A_HAIKU_1[2]);
		_testFirstShortestElement(A_HAIKU_2, A_HAIKU_2[2]);
		
		_testFirstShortestElement(A_QUEEN, A_QUEEN[0]);
		_testFirstShortestElement(A_DUNE, A_DUNE[0]);	
	}
	
	public void testProgram() {
		_test(A_EMPTY_ARRAY, ERR_USAGE);
		
		_test(A_ABC123_4, TestSuite.terminalOutput(new String[] {
			"Arguments (10, no duplication): a b c 1 two 3 ab ac bc abcdefghijklmnopqrstuvwxyz",
			"Length: total=40, avg=4",
			"Shortest (1): first=<a>, last=<3>",
			"Longest (26): <abcdefghijklmnopqrstuvwxyz>",
			"Average (4): none"
		}));
		
		_test(A_HELLO, TestSuite.terminalOutput(new String[] {
			"Arguments (2, no duplication): hello world!",
			"Length: total=11, avg=6",
			"Shortest (5): <hello>",
			"Longest (6): <world!>",
			"Average (6): <world!>",
		}));
		
		_test(A_HAIKU_1, TestSuite.terminalOutput(new String[] {
			"Arguments (13, no duplication): round lumps of cells grow up to love porridge later become The Supremes",
			"Length: total=59, avg=5",
			"Shortest (2): first=<of>, last=<to>",
			"Longest (8): first=<porridge>, last=<Supremes>",
			"Average (5): first=<round>, last=<later>"	
		}));
		
		_test(A_QUEEN, TestSuite.terminalOutput(new String[] {
			"Arguments (22, has duplication): I see a little silhouetto of a man Scaramouche Scaramouche will you do the Fandango Thunderbolt and lightning very very fright'ning me",
			"Length: total=113, avg=5",
			"Shortest (1): first=<I>, last=<a>",
			"Longest (11): first=<Scaramouche>, last=<fright'ning>",
			"Average (5): none"
		}));
		
		_test(A_DUNE, TestSuite.terminalOutput(new String[] {
			"Arguments (61, has duplication): I must not fear Fear is the mind-killer Fear is the little-death that brings total obliteration I will face my fear I will permit it to pass over me and through me And when it has gone past I will turn the inner eye to see its path Where the fear has gone there will be nothing Only I will remain",
			"Length: total=236, avg=4",
			"Shortest (1): first=<I>, last=<I>",
			"Longest (12): first=<little-death>, last=<obliteration>",
			"Average (4): first=<must>, last=<will>"
		}));
	}
	
}
