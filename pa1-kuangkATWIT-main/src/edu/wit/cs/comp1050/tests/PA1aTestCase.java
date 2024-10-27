package edu.wit.cs.comp1050.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp1050.PA1a;
import junit.framework.TestCase;

public class PA1aTestCase extends TestCase {
	
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
	
	public void testOutput() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final String expected = "Hello World!";
		
		System.setOut(new PrintStream(outContent));
		try {
			PA1a.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(String.format("%s%n", expected), outContent.toString());
		
		System.setOut(null);
	}

}
