package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Permission;
import java.util.Scanner;

import edu.wit.cs.comp1050.PA2c;
import junit.framework.TestCase;

public class PA2cTestCase extends TestCase {
	
	private static final String ERR_FILE = "Error opening file(s)";
	private static final String ERR_DIMS = "Bad matrix dimensions";
	
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
	
	private void _test(String f1, String f2, String f3, String t) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String input = String.format("%s%n%s%n%s%n", f1, f2, f3);
		final String expected = TestSuite.stringOutput(new String[] {
			"Enter path for matrix 1: ",
			"Enter path for matrix 2: ",
			"Enter path for result: ",
			t + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA2c.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testCanMultiply(int[][] m1, int[][] m2, boolean expected) {
		Boolean result = null;
		try {
			result = PA2c.canMultiply(m1, m2);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result);
	}
	
	private void _testReadMatrix(Scanner s, int rE, int cE, String sE) {
		int[][] result = null;
		try {
			result = PA2c.readMatrix(s);
		} catch (ExitException e) {}
		
		assertNotNull("Null result", result);
		assertEquals("Row mismatch", rE, result.length);
		assertEquals("Columns mismatch", cE, result[0].length);
		
		//
		
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		try {
			PA2c.printMatrix(result);
		}  catch (ExitException e) {}
		
		assertEquals("Contents mismatch", sE, outContent.toString());
	}
	
	private void _testMatrixMultiply(int[][] m1, int[][] m2, int rE, int cE, String sE) {
		int[][] result = null;
		try {
			result = PA2c.matrixMultiply(m1, m2);
		} catch (ExitException e) {}
		
		assertNotNull("Null result", result);
		assertEquals("Row mismatch", rE, result.length);
		assertEquals("Columns mismatch", cE, result[0].length);
		
		//
		
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		try {
			PA2c.printMatrix(result);
		}  catch (ExitException e) {}
		
		assertEquals("Contents mismatch", sE, outContent.toString());
	}
	
	public void testCanMultiply() {
		final int[][] m_0 = {};
		final int[][] m_2_0 = {{}, {}};
		
		final int[][] m_1_1 = {{1}};
		final int[][] m_1_2 = {{2, 2}};
		final int[][] m_2_1 = {{3}, {3}};
		
		_testCanMultiply(m_0, m_0, false);
		_testCanMultiply(m_0, m_1_1, false);
		_testCanMultiply(m_1_1, m_0, false);
		_testCanMultiply(m_2_0, m_1_1, false);
		_testCanMultiply(m_1_1, m_2_0, false);
		_testCanMultiply(m_2_0, m_2_0, false);
		
		_testCanMultiply(m_1_1, m_1_1, true);
		
		_testCanMultiply(m_1_1, m_1_2, true);
		_testCanMultiply(m_1_2, m_1_1, false);
		_testCanMultiply(m_1_2, m_1_2, false);
		
		_testCanMultiply(m_1_1, m_2_1, false);
		_testCanMultiply(m_2_1, m_1_1, true);
		_testCanMultiply(m_1_2, m_2_1, true);
		_testCanMultiply(m_2_1, m_1_2, true);
		_testCanMultiply(m_2_1, m_2_1, false);
	}
	
	public void _testReadMatrixHelper(String s, int rE, int cE, String sE) {
		_testReadMatrix(new Scanner(String.format(s)), rE, cE, String.format(sE));
	}
	
	public void testReadMatrix() {
		_testReadMatrixHelper("1 1 1", 1, 1, "1%n");
		_testReadMatrixHelper("1%n1%n1%n", 1, 1, "1%n");
		
		_testReadMatrixHelper("1 2 2 2", 1, 2, "2 2%n");
		_testReadMatrixHelper("1%n2%n2 2%n", 1, 2, "2 2%n");
		
		_testReadMatrixHelper("2 1 3 3", 2, 1, "3%n3%n");
		_testReadMatrixHelper("2%n1%n3%n3%n", 2, 1, "3%n3%n");
		
		_testReadMatrixHelper("2 3 1 2 3 4 5 6", 2, 3, "1 2 3%n4 5 6%n");
		_testReadMatrixHelper("2%n3%n1 2 3%n4 5 6%n", 2, 3, "1 2 3%n4 5 6%n");
		
		_testReadMatrixHelper("3 3 1 1 1 2 2 2 3 3 3", 3, 3, "1 1 1%n2 2 2%n3 3 3%n");
		_testReadMatrixHelper("3%n3%n1 1 1%n2 2 2%n3 3 3%n", 3, 3, "1 1 1%n2 2 2%n3 3 3%n");
	}
	
	private void _testMatrixMultiplyHelper(int[][] m1, int[][] m2, int rE, int cE, String sE) {
		_testMatrixMultiply(m1, m2, rE, cE, String.format(sE));
	}
	
	public void testMatrixMultiply() {
		final int[][] m_1_1 = {{1}};
		final int[][] m_1_2 = {{2, 2}};
		final int[][] m_2_1 = {{3}, {3}};
		final int[][] m_2_3 = {{1, 2, 3}, {4, 5, 6}};
		final int[][] m_3_2 = {{7, 8}, {9, 10}, {11, 12}};
		final int[][] m_3_3 = {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
		
		_testMatrixMultiplyHelper(m_1_1, m_1_1, 1, 1, "1%n");
		_testMatrixMultiplyHelper(m_1_1, m_1_2, 1, 2, "2 2%n");
		_testMatrixMultiplyHelper(m_2_1, m_1_1, 2, 1, "3%n3%n");
		_testMatrixMultiplyHelper(m_2_1, m_1_2, 2, 2, "6 6%n6 6%n");
		_testMatrixMultiplyHelper(m_1_2, m_2_1, 1, 1, "12%n");
		_testMatrixMultiplyHelper(m_1_2, m_2_3, 1, 3, "10 14 18%n");
		_testMatrixMultiplyHelper(m_2_3, m_3_3, 2, 3, "14 14 14%n32 32 32%n");
		_testMatrixMultiplyHelper(m_2_3, m_3_2, 2, 2, "58 64%n139 154%n");
		_testMatrixMultiplyHelper(m_3_3, m_3_3, 3, 3, "6 6 6%n12 12 12%n18 18 18%n");
	}
	
	private void _testProgramBadFile() throws IOException {
		final File fGood1 = TestSuite.getGoodFile();
		final File fGood2 = TestSuite.getGoodFile();
		final String goodPath1 = fGood1.getAbsolutePath();
		final String goodPath2 = fGood2.getAbsolutePath();
		final String badPath = TestSuite.getBadPath("thingsandstuffs.txt");
		
		_test(goodPath1, badPath, goodPath2, ERR_FILE);
		_test(badPath, goodPath1, goodPath2, ERR_FILE);
		_test(badPath, badPath, goodPath2, ERR_FILE);
	}
	
	private void _testProgramBadDims(String m1, String m2, String rT) throws IOException {
		final File fGood1 = TestSuite.getGoodFile();
		final File fGood2 = TestSuite.getGoodFile();
		final File fGood3 = TestSuite.getGoodFile();
		
		TestSuite.putInFile(fGood1, String.format(m1));
		TestSuite.putInFile(fGood2, String.format(m2));
		
		_test(fGood1.getAbsolutePath(), fGood2.getAbsolutePath(), fGood3.getAbsolutePath(), rT + ERR_DIMS);
	}
	
	private void _testProgramGood(String m1, String m2, String rT, String rF) throws IOException {
		final File fGood1 = TestSuite.getGoodFile();
		final File fGood2 = TestSuite.getGoodFile();
		final File fGood3 = TestSuite.getGoodFile();
		
		TestSuite.putInFile(fGood1, String.format(m1));
		TestSuite.putInFile(fGood2, String.format(m2));
		
		_test(fGood1.getAbsolutePath(), fGood2.getAbsolutePath(), fGood3.getAbsolutePath(), rT);
		
		assertEquals("File Contents Error!", String.format(rF), TestSuite.getFileContents(fGood3));
	}
	
	private String _testProgramTerminalHelper(String a, String b, String c) {
		return a + "%nX%n" + b + "%n=%n" + c;
	}
	
	private String _testProgramTerminalHelper(String a, String b) {
		return _testProgramTerminalHelper(a, b, "");
	}
	
	public void testProgram() throws IOException {
		final String m_1_1_f = "1%n1%n1%n";
		final String m_1_1_t = "1";
		
		final String m_1_2_f = "1%n2%n2 2%n";
		final String m_1_2_t = "2 2";
		
		final String m_2_1_f = "2%n1%n3%n3%n";
		final String m_2_1_t = "3%n3";
		
		final String m_2_3_f = "2%n3%n1 2 3%n4 5 6%n";
		final String m_2_3_t = "1 2 3%n4 5 6";
		
		final String m_3_3_f = "3%n3%n1 1 1%n2 2 2%n3 3 3%n";
		final String m_3_3_t = "1 1 1%n2 2 2%n3 3 3";
		
		final String m_3_2_f = "3%n2%n7 8%n9 10%n11 12%n";
		final String m_3_2_t = "7 8%n9 10%n11 12";
		
		//
		
		_testProgramBadFile();
		
		_testProgramBadDims(m_1_1_f, m_2_1_f, _testProgramTerminalHelper(m_1_1_t, m_2_1_t));
		_testProgramBadDims(m_1_1_f, m_2_3_f, _testProgramTerminalHelper(m_1_1_t, m_2_3_t));
		_testProgramBadDims(m_1_1_f, m_3_3_f, _testProgramTerminalHelper(m_1_1_t, m_3_3_t));
		_testProgramBadDims(m_1_1_f, m_3_2_f, _testProgramTerminalHelper(m_1_1_t, m_3_2_t));
		_testProgramBadDims(m_1_2_f, m_1_1_f, _testProgramTerminalHelper(m_1_2_t, m_1_1_t));
		_testProgramBadDims(m_1_2_f, m_3_3_f, _testProgramTerminalHelper(m_1_2_t, m_3_3_t));
		_testProgramBadDims(m_1_2_f, m_3_2_f, _testProgramTerminalHelper(m_1_2_t, m_3_2_t));
		_testProgramBadDims(m_2_1_f, m_2_1_f, _testProgramTerminalHelper(m_2_1_t, m_2_1_t));
		_testProgramBadDims(m_2_1_f, m_2_3_f, _testProgramTerminalHelper(m_2_1_t, m_2_3_t));
		_testProgramBadDims(m_2_1_f, m_3_3_f, _testProgramTerminalHelper(m_2_1_t, m_3_3_t));
		_testProgramBadDims(m_2_1_f, m_3_2_f, _testProgramTerminalHelper(m_2_1_t, m_3_2_t));
		_testProgramBadDims(m_2_3_f, m_1_1_f, _testProgramTerminalHelper(m_2_3_t, m_1_1_t));
		_testProgramBadDims(m_2_3_f, m_1_2_f, _testProgramTerminalHelper(m_2_3_t, m_1_2_t));
		_testProgramBadDims(m_2_3_f, m_2_1_f, _testProgramTerminalHelper(m_2_3_t, m_2_1_t));
		_testProgramBadDims(m_2_3_f, m_2_3_f, _testProgramTerminalHelper(m_2_3_t, m_2_3_t));
		_testProgramBadDims(m_3_3_f, m_1_1_f, _testProgramTerminalHelper(m_3_3_t, m_1_1_t));
		_testProgramBadDims(m_3_3_f, m_2_1_f, _testProgramTerminalHelper(m_3_3_t, m_2_1_t));
		_testProgramBadDims(m_3_3_f, m_1_2_f, _testProgramTerminalHelper(m_3_3_t, m_1_2_t));
		_testProgramBadDims(m_3_3_f, m_2_3_f, _testProgramTerminalHelper(m_3_3_t, m_2_3_t));
		_testProgramBadDims(m_3_2_f, m_1_1_f, _testProgramTerminalHelper(m_3_2_t, m_1_1_t));
		_testProgramBadDims(m_3_2_f, m_1_2_f, _testProgramTerminalHelper(m_3_2_t, m_1_2_t));
		_testProgramBadDims(m_3_2_f, m_3_3_f, _testProgramTerminalHelper(m_3_2_t, m_3_3_t));
		
		_testProgramGood(m_1_1_f, m_1_1_f, _testProgramTerminalHelper(m_1_1_t, m_1_1_t, m_1_1_t), m_1_1_f);
		_testProgramGood(m_1_1_f, m_1_2_f, _testProgramTerminalHelper(m_1_1_t, m_1_2_t, m_1_2_t), m_1_2_f);
		_testProgramGood(m_2_1_f, m_1_1_f, _testProgramTerminalHelper(m_2_1_t, m_1_1_t, m_2_1_t), m_2_1_f);
		_testProgramGood(m_2_1_f, m_1_2_f, _testProgramTerminalHelper(m_2_1_t, m_1_2_t, "6 6%n6 6"), "2%n2%n6 6%n6 6%n");
		_testProgramGood(m_1_2_f, m_2_1_f, _testProgramTerminalHelper(m_1_2_t, m_2_1_t, "12"), "1%n1%n12%n");
		_testProgramGood(m_1_2_f, m_2_3_f, _testProgramTerminalHelper(m_1_2_t, m_2_3_t, "10 14 18"), "1%n3%n10 14 18%n");
		_testProgramGood(m_2_3_f, m_3_3_f, _testProgramTerminalHelper(m_2_3_t, m_3_3_t, "14 14 14%n32 32 32"), "2%n3%n14 14 14%n32 32 32%n");
		_testProgramGood(m_2_3_f, m_3_2_f, _testProgramTerminalHelper(m_2_3_t, m_3_2_t, "58 64%n139 154"), "2%n2%n58 64%n139 154%n");
		_testProgramGood(m_3_3_f, m_3_3_f, _testProgramTerminalHelper(m_3_3_t, m_3_3_t, "6 6 6%n12 12 12%n18 18 18"), "3%n3%n6 6 6%n12 12 12%n18 18 18%n");
	}
	
}
