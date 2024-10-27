package edu.wit.cs.comp1050.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PA1aTestCase.class,
	PA1bTestCase.class,
	PA1cTestCase.class,
	PA1dTestCase.class,
	PA1eTestCase.class,
})

public class TestSuite {
	static String stringOutput(String[] lines, Object[] values) {
		return String.format(String.join("", lines), values);
	}
}
