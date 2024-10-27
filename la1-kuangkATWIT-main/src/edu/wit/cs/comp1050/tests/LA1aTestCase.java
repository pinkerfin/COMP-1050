package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.Assert;

import edu.wit.cs.comp1050.LA1a;
import junit.framework.TestCase;

public class LA1aTestCase extends TestCase {

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
			"Enter phrase 1: ",
			"Enter phrase 2: ",
			msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			LA1a.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testCountLetters(String s, int[] expected) {
		int[] result = null;
		try {
			result = LA1a.countLetters(s);
		} catch (ExitException e) {}
		Assert.assertArrayEquals(expected, result);
	}
	
	private void _testSameCounts(int[] c1, int[] c2, boolean expected) {
		Boolean result = null;
		try {
			result = LA1a.sameCounts(c1, c2);
		} catch (ExitException e) {}
		assertEquals(expected, (boolean) result); 
	}
	
	public void testCountLetters() {
		_testCountLetters("", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		_testCountLetters(" !?", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		
		_testCountLetters("a", new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		_testCountLetters("b", new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		_testCountLetters("z", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
		_testCountLetters("baz", new int[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
		
		_testCountLetters(
			"AbCdeFgHiJkLmNoPqRsTUvWxYz", 
			new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		);
		_testCountLetters(
			"AabBCcdDeEFfgGHhiIJjkKLlMmnNOopPQqrRSstTuUVvwWXxyYZz", 
			new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
		);
		_testCountLetters(
			"Aa bB!Cc?dD-e E/F+fgG= #$%HhiI^&*JjkK^&*LlM!@#m n N Oo p P    Qq  r RSstTuUVv3456567wWXxyYZz?!", 
			new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
		);
		
		_testCountLetters( "Silent",
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}
		);
		
		_testCountLetters( "Listen",
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}
		);
		
		_testCountLetters( "Anagrams",
			new int[] {3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0}
		);
		
		_testCountLetters( "Ars Magna",
			new int[] {3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0}
		);
		
		_testCountLetters( "William Shakespeare",
			new int[] {3, 0, 0, 0, 3, 0, 0, 1, 2, 0, 1, 2, 1, 0, 0, 1, 0, 1, 2, 0, 0, 0, 1, 0, 0, 0}
		);
		
		_testCountLetters( "I am a weakish speller",
			new int[] {3, 0, 0, 0, 3, 0, 0, 1, 2, 0, 1, 2, 1, 0, 0, 1, 0, 1, 2, 0, 0, 0, 1, 0, 0, 0}
		);
		
		_testCountLetters( "Madam Curie",
			new int[] {2, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0}
		);
		
		_testCountLetters( "Radium came",
			new int[] {2, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0}
		);
	}
	
	public void testSameCounts() {
		_testSameCounts(new int[] {}, new int[] {}, true);
		_testSameCounts(new int[] {}, new int[] {1}, false);
		_testSameCounts(new int[] {1}, new int[] {}, false);
		_testSameCounts(new int[] {1}, new int[] {1}, true);
		_testSameCounts(new int[] {0}, new int[] {0}, true);
		_testSameCounts(new int[] {0}, new int[] {1}, false);
		
		_testSameCounts(
			new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			true
		);
		
		_testSameCounts(
			new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			true
		);
		
		_testSameCounts(
			new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			true
		);
		
		_testSameCounts(
			new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			true
		);
		
		_testSameCounts(
			new int[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			new int[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			true
		);
		
		_testSameCounts(
			new int[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			new int[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			false
		);
		
		_testSameCounts(
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
			true
		);
		
		_testSameCounts(
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0},
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
			false
		);
		
		_testSameCounts(
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
			new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
			false
		);
	}
	
	private void _testProgram(String s1, String s2, boolean expected) {
		if (expected) {
			_test(s1, s2, "These phrases are anagrams.");
		} else {
			_test(s1, s2, "These phrases are not anagrams.");
		}
	}
	
	public void testProgram() {
		_testProgram("", "", true);
		_testProgram("a", "a", true);
		_testProgram("b", "b", true);
		_testProgram("z", "z", true);
		
		_testProgram("", "a", false);
		_testProgram("a", "", false);
		_testProgram("baz", "bar", false);
		_testProgram("bar", "baz", false);
		
		_testProgram("bar", "bar", true);
		_testProgram("baz", "baz", true);
		_testProgram("BaR", "bar", true);
		_testProgram("bAz", "baz", true);
		_testProgram("b123a?_r ", "b:)a(:r", true);
		_testProgram("baZ/////", "baz?", true);
		
		_testProgram("cinema", "iceman", true);
		_testProgram("cinema", "snowman", false);
		_testProgram("Silent!", "Listen?", true);
		_testProgram("Anagrams!", "__Ars Magna__", true);
		_testProgram("William Shakespeare!", "'I am a weakish speller :('", true);
		_testProgram("Madam Curie", "Radium came...", true);
		_testProgram("Madam Curie", "Radium comes...", false);
	}
	
}
