package edu.wit.cs.comp1050.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PA3aTestCase.class,
	PA3bTestCase.class,
	PA3cTestCase.class,
})

public class TestSuite {
	static String stringOutput(String[] lines, Object[] values) {
		return String.format(String.join("", lines), values);
	}
	
	static String terminalOutput(String[] lines) {
		return String.join(String.format("%n"), lines);
	}
}
