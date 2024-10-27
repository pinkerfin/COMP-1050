
package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.Assert;

import edu.wit.cs.comp1050.PA4a;
import edu.wit.cs.comp1050.Shifter;
import junit.framework.TestCase;

public class PA4aTestCase extends TestCase {
	
	private static final String ERR_USAGE = "Please supply correct inputs: <encrypted string> <substring>";
	private static final String ERR_NONE = "No valid shifts found.";
	
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
			PA4a.main(a);
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testShift(String s, int[] n, String[] eShift) {
		Shifter shifter;
		
		shifter = null;
		try {
			shifter = new Shifter(s);
		} catch (ExitException ex) {}
		assertNotNull(shifter);
		
		for (int i=0; i<n.length; i++) {
			String result = null;
			try {
				result = shifter.shift(n[i]);
			} catch (ExitException ex) {}
			assertNotNull(result);
			assertEquals(String.format("\"%s\".shift(%d)", s, n[i]), eShift[i], result);
		}
	}
	
	private void _testFindShift(String s, String sub, int[] e) {
		Shifter shifter = null;
		int[] result = null;
		
		try {
			shifter = new Shifter(s);
		} catch (ExitException ex) {}
		assertNotNull(shifter);
		
		try {
			result = shifter.findShift(sub);
		} catch (ExitException ex) {}
		assertNotNull(result);
		
		Assert.assertArrayEquals(e, result);
	}
	
	public void testShift() {
		_testShift("", 
			new int[] {-2, -1, 0, 1, 2, 10, 25, 26, 27, 100}, 
			new String[] {"", "", "", "", "", "", "", "", "", ""}
		);
		
		_testShift(" ", 
			new int[] {-2, -1, 0, 1, 2, 10, 25, 26, 27, 100}, 
			new String[] {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "}
		);
		
		_testShift("!!", 
			new int[] {-2, -1, 0, 1, 2, 10, 25, 26, 27, 100}, 
			new String[] {"!!", "!!", "!!", "!!", "!!", "!!", "!!", "!!", "!!", "!!"}
		);
		
		_testShift("ab", 
			new int[] {-2, -1, 0, 1, 2, 10, 25, 26, 27, 100}, 
			new String[] {"yz", "za", "ab", "bc", "cd", "kl", "za", "ab", "bc", "wx"}
		);
		
		_testShift("!ab + Ab ?= AB - aB!", 
			new int[] {-2, -1, 0, 1, 2, 10, 25, 26, 27, 100}, 
			new String[] {
				"!yz + Yz ?= YZ - yZ!", 
				"!za + Za ?= ZA - zA!", 
				"!ab + Ab ?= AB - aB!", 
				"!bc + Bc ?= BC - bC!", 
				"!cd + Cd ?= CD - cD!", 
				"!kl + Kl ?= KL - kL!", 
				"!za + Za ?= ZA - zA!", 
				"!ab + Ab ?= AB - aB!",
				"!bc + Bc ?= BC - bC!",
				"!wx + Wx ?= WX - wX!"
			}
		);
		
		_testShift("Jvtwbaly zjplujl pz mbu!", 
			new int[] {
				-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 
				22, 23, 24, 25, 26, 27, 28, 100
			}, 
			new String[] {
				"Htruzyjw xhnjshj nx kzs!",
				"Iusvazkx yioktik oy lat!",
				"Jvtwbaly zjplujl pz mbu!", // 0
				"Kwuxcbmz akqmvkm qa ncv!", // 1
				"Lxvydcna blrnwln rb odw!", // 2
				"Mywzedob cmsoxmo sc pex!", // 3
				"Nzxafepc dntpynp td qfy!", // 4
				"Oaybgfqd eouqzoq ue rgz!", // 5
				"Pbzchgre fpvrapr vf sha!", // 6
				"Qcadihsf gqwsbqs wg tib!", // 7
				"Rdbejitg hrxtcrt xh ujc!", // 8
				"Secfkjuh isyudsu yi vkd!", // 9
				"Tfdglkvi jtzvetv zj wle!", // 10
				"Ugehmlwj kuawfuw ak xmf!", // 11
				"Vhfinmxk lvbxgvx bl yng!", // 12
				"Wigjonyl mwcyhwy cm zoh!", // 13
				"Xjhkpozm nxdzixz dn api!", // 14
				"Ykilqpan oyeajya eo bqj!", // 15
				"Zljmrqbo pzfbkzb fp crk!", // 16
				"Amknsrcp qagclac gq dsl!", // 17
				"Bnlotsdq rbhdmbd hr etm!", // 18
				"Computer science is fun!", // 19
				"Dpnqvufs tdjfodf jt gvo!", // 20
				"Eqorwvgt uekgpeg ku hwp!", // 21
				"Frpsxwhu vflhqfh lv ixq!", // 22
				"Gsqtyxiv wgmirgi mw jyr!", // 23
				"Htruzyjw xhnjshj nx kzs!", // 24
				"Iusvazkx yioktik oy lat!", // 25
				"Jvtwbaly zjplujl pz mbu!",
				"Kwuxcbmz akqmvkm qa ncv!",
				"Lxvydcna blrnwln rb odw!",
				"Frpsxwhu vflhqfh lv ixq!",
			}
		);
	}
	
	public void testFindShift() {
		final int[] ALL = {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 
			9, 10, 11, 12, 13, 14, 15, 
			16, 17, 18, 19, 20, 21, 22, 
			23, 24, 25
		};
		
		final int[] NONE = {};
		
		_testFindShift("", "", ALL);
		_testFindShift("", " ", NONE);
		
		_testFindShift(" ", "", ALL);
		_testFindShift(" ", " ", ALL);
		_testFindShift(" ", "  ", NONE);
		_testFindShift(" ", "?", NONE);
		
		_testFindShift("!!", "", ALL);
		_testFindShift("!!", "!", ALL);
		_testFindShift("!!", "!!", ALL);
		_testFindShift("!!", "!!!", NONE);
		
		_testFindShift("ab", "", ALL);
		_testFindShift("ab", "a", new int[] {0, 25});
		_testFindShift("ab", "b", new int[] {0, 1});
		_testFindShift("ab", "ab", new int[] {0});
		_testFindShift("ab", "ac", NONE);
		_testFindShift("ab", "ba", NONE);
		_testFindShift("ab", "wx", new int[] {22});
		
		_testFindShift("Jvtwbaly zjplujl pz mbu!", "?", NONE);
		_testFindShift("Jvtwbaly zjplujl pz mbu!", " ", ALL);
		_testFindShift("Jvtwbaly zjplujl pz mbu!", "a", new int[] {0, 1, 2, 4, 5, 6, 7, 11, 14, 15, 17, 25});
		_testFindShift("Jvtwbaly zjplujl pz mbu!", "is", new int[] {9, 19});
		_testFindShift("Jvtwbaly zjplujl pz mbu!", "fun!", new int[] {19});
	}
	
	private void _testProgram(String[] a, String[] msg) {
		_test(a, String.join("%n", msg));
	}
	
	public void testProgram() {
		_test(new String[] {}, ERR_USAGE);
		_test(new String[] {"foo"}, ERR_USAGE);
		_test(new String[] {"foo", "bar", "baz"}, ERR_USAGE);
		
		_test(new String[] {"ab", "ac"}, ERR_NONE);
		_test(new String[] {"ab", "ba"}, ERR_NONE);
		_testProgram(new String[] {"ab", "ab"}, new String[] {"00: ab"});
		_testProgram(new String[] {"ab", "wx"}, new String[] {"22: wx"});
		_testProgram(new String[] {"ab", "a"}, new String[] {"00: ab", "25: za"});
		_testProgram(new String[] {"ab", "b"}, new String[] {"00: ab", "01: bc"});
		
		_test(new String[] {"Jvtwbaly zjplujl pz mbu!", "?"}, ERR_NONE);
		_testProgram(new String[] {"Jvtwbaly zjplujl pz mbu!", "is"}, new String[] {
			"09: Secfkjuh isyudsu yi vkd!",
			"19: Computer science is fun!"
		});
		_testProgram(new String[] {"Jvtwbaly zjplujl pz mbu!", "a"}, new String[] {
			"00: Jvtwbaly zjplujl pz mbu!",
			"01: Kwuxcbmz akqmvkm qa ncv!",
			"02: Lxvydcna blrnwln rb odw!",
			"04: Nzxafepc dntpynp td qfy!",
			"05: Oaybgfqd eouqzoq ue rgz!",
			"06: Pbzchgre fpvrapr vf sha!",
			"07: Qcadihsf gqwsbqs wg tib!",
			"11: Ugehmlwj kuawfuw ak xmf!",
			"14: Xjhkpozm nxdzixz dn api!",
			"15: Ykilqpan oyeajya eo bqj!",
			"17: Amknsrcp qagclac gq dsl!",
			"25: Iusvazkx yioktik oy lat!",
		});
		_testProgram(new String[] {"Jvtwbaly zjplujl pz mbu!", "fun!"}, new String[] {
			"19: Computer science is fun!"
		});
	}
	
}
