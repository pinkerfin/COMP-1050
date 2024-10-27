
package edu.wit.cs.comp1050.tests;

import java.security.Permission;

import org.junit.Assert;

import edu.wit.cs.comp1050.PA5b;
import junit.framework.TestCase;

public class PA5bTestCase extends TestCase {
	
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
	
	private void _testPatternMatch(String n, String h, String[] mE) {
		String[] r;
		
		r = null;
		try {
			r = PA5b.patternMatch(n, h);
		} catch (ExitException ex) {}
		
		Assert.assertArrayEquals(String.format("patternMatch('%s', '%s')", n, h),  mE, r);
	}
	
	public void testPatternMatch() {
		_testPatternMatch("a", "", new String[] {});
		
		_testPatternMatch("a", "abcba", new String[] {"a", "a"});
		_testPatternMatch("b", "abcba", new String[] {"b", "b"});
		_testPatternMatch("c", "c", new String[] {"c"});
		_testPatternMatch("-", "abcba", new String[] {"a", "b", "c", "b", "a"});
		
		_testPatternMatch("ab", "abcba", new String[] {"ab"});
		_testPatternMatch("ac", "abcba", new String[] {});
		_testPatternMatch("ba", "abcba", new String[] {"ba"});
		_testPatternMatch("bc", "abcba", new String[] {"bc"});
		_testPatternMatch("ca", "abcba", new String[] {});
		_testPatternMatch("cb", "abcba", new String[] {"cb"});
		_testPatternMatch("a-", "abcba", new String[] {"ab"});
		_testPatternMatch("b-", "abcba", new String[] {"bc", "ba"});
		_testPatternMatch("c-", "abcba", new String[] {"cb"});
		_testPatternMatch("-a", "abcba", new String[] {"ba"});
		_testPatternMatch("-b", "abcba", new String[] {"ab", "cb"});
		_testPatternMatch("-c", "abcba", new String[] {"bc"});
		_testPatternMatch("--", "abcba", new String[] {"ab", "bc", "cb", "ba"});
		_testPatternMatch("abc", "abcba", new String[] {"abc"});
		_testPatternMatch("bcb", "abcba", new String[] {"bcb"});
		_testPatternMatch("---", "abcba", new String[] {"abc", "bcb", "cba"});
		_testPatternMatch("b-b", "abcba", new String[] {"bcb"});
		_testPatternMatch("-b-", "abcba", new String[] {"abc", "cba"});
		_testPatternMatch("----", "abcba", new String[] {"abcb", "bcba"});
		_testPatternMatch("-----", "abcba", new String[] {"abcba"});
		_testPatternMatch("------", "abcba", new String[] {});
		
		_testPatternMatch("A--G-", "ACTGGTACTGA", new String[] {"ACTGG", "ACTGA"});
		_testPatternMatch("-GG-", "ACTGGTACTGA", new String[] {"TGGT"});
		_testPatternMatch("-GGC", "ACTGGTACTGA", new String[] {});
	}
	
}
