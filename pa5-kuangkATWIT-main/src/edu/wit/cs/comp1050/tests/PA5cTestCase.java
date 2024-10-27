
package edu.wit.cs.comp1050.tests;

import java.security.Permission;
import java.util.List;

import edu.wit.cs.comp1050.FullTimeComputational;
import edu.wit.cs.comp1050.LowGPA;
import edu.wit.cs.comp1050.Student;
import edu.wit.cs.comp1050.StudentFilter;
import junit.framework.TestCase;

public class PA5cTestCase extends TestCase {
	
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
	
	private void _testCurrent(String semester, int year, String semesterYear) {
		assertEquals(String.format("getCurrentSemester"), semester, Student.getCurrentSemester());
		assertEquals(String.format("getCurrentYear"), year, Student.getCurrentYear());
		assertEquals(String.format("getCurrentSemesterYear"), semesterYear, Student.getCurrentSemesterYear());
	}
	
	private void _testReset(String semester, int year, String semesterYear) {
		try {
			Student.resetSemesterYear();
		} catch (ExitException ex) {}
		
		_testCurrent(semester, year, semesterYear);
	}
	
	private void _testNext(String semester, int year, String semesterYear) {
		try {
			Student.nextSemester();
		} catch (ExitException ex) {}
		
		_testCurrent(semester, year, semesterYear);
	}
	
	private void _testContents(Student s, String sE, String mE, String semE, int yE, double gpaE, boolean ftE) {
		assertEquals(String.format("toString"), sE, s.toString());
		assertEquals(String.format("getMajor"), mE, s.getMajor());
		assertEquals(String.format("getSemester"), semE, s.getSemester());
		assertEquals(String.format("getYear"), yE, s.getYear());
		assertEquals(String.format("getGPA"), gpaE, s.getGPA(), 1.0E-5);
		assertEquals(String.format("isFullTime"), ftE, s.isFullTime());
	}
	
	private Student _construct(String major, String sE, String semE, int yE, double gpaE, boolean ftE) {
		Student s;
		
		s = null;
		try {
			s = new Student(major);
		} catch (ExitException ex) {}
		assertNotNull("Could not construct", s);
		
		_testContents(s, sE, major, semE, yE, gpaE, ftE);
		
		return s;
	}
	
	private void _testAdd(Student s, int c, double w, String sE, String major, String semE, int yE, double gpaE, boolean ftE) {
		try {
			s.addClass(c, w);
		} catch (ExitException ex) {}
		
		_testContents(s, sE, major, semE, yE, gpaE, ftE);
	}
	
	private StudentFilter _constructFTComp() {
		StudentFilter f;
		
		f = null;
		try {
			f = new FullTimeComputational();
		} catch (ExitException ex) {}
		assertNotNull("Could not construct", f);
		
		return f;
	}
	
	private StudentFilter _constructLowGPA() {
		StudentFilter f;
		
		f = null;
		try {
			f = new LowGPA();
		} catch (ExitException ex) {}
		assertNotNull("Could not construct", f);
		
		return f;
	}
	
	private void _testFind(StudentFilter f, Student[] lE) {
		List<Student> r;
		
		r = null;
		try {
			r = Student.find(f);
		} catch (ExitException ex) {}
		assertNotNull("Find returns null", r);
		assertEquals("find returns wrong number of students", lE.length, r.size());
		
		for (int i=0; i<lE.length; i++) {
			assertTrue(String.format("find result #%d", i), lE[i]==r.get(i));
		}
	}
	
	public void testStatic() {
		_testReset("Fall", 2017, "Fall 2017");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		_testNext("Spring", 2019, "Spring 2019");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		_testNext("Spring", 2019, "Spring 2019");
		_testNext("Summer", 2019, "Summer 2019");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		_testNext("Spring", 2019, "Spring 2019");
		_testNext("Summer", 2019, "Summer 2019");
		_testNext("Fall", 2019, "Fall 2019");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		_testNext("Spring", 2019, "Spring 2019");
		_testNext("Summer", 2019, "Summer 2019");
		_testNext("Fall", 2019, "Fall 2019");
		_testNext("Spring", 2020, "Spring 2020");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		_testNext("Spring", 2019, "Spring 2019");
		_testNext("Summer", 2019, "Summer 2019");
		_testNext("Fall", 2019, "Fall 2019");
		_testNext("Spring", 2020, "Spring 2020");
		_testNext("Summer", 2020, "Summer 2020");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		_testNext("Spring", 2019, "Spring 2019");
		_testNext("Summer", 2019, "Summer 2019");
		_testNext("Fall", 2019, "Fall 2019");
		_testNext("Spring", 2020, "Spring 2020");
		_testNext("Summer", 2020, "Summer 2020");
		_testNext("Fall", 2020, "Fall 2020");
		
		_testReset("Fall", 2017, "Fall 2017");
		_testNext("Spring", 2018, "Spring 2018");
		_testNext("Summer", 2018, "Summer 2018");
		_testNext("Fall", 2018, "Fall 2018");
		_testNext("Spring", 2019, "Spring 2019");
		_testNext("Summer", 2019, "Summer 2019");
		_testNext("Fall", 2019, "Fall 2019");
		_testNext("Spring", 2020, "Spring 2020");
		_testNext("Summer", 2020, "Summer 2020");
		_testNext("Fall", 2020, "Fall 2020");
		_testNext("Spring", 2021, "Spring 2021");
	}
	
	public void testConstruct() {
		Student s1, s2, s3, s4;
		
		_testReset("Fall", 2017, "Fall 2017");
		s1 = _construct("MJOR", "Fall 2017 #1", "Fall", 2017, 0., false);
		
		_testNext("Spring", 2018, "Spring 2018");
		_testContents(s1, "Fall 2017 #1", "MJOR", "Fall", 2017, 0., false);
		
		s2 = _construct("BCOS", "Spring 2018 #1", "Spring", 2018, 0., false);
		s3 = _construct("BSCN", "Spring 2018 #2", "Spring", 2018, 0., false);
		s4 = _construct("BSIS", "Spring 2018 #3", "Spring", 2018, 0., false);
		
		_testNext("Summer", 2018, "Summer 2018");
		_testContents(s1, "Fall 2017 #1", "MJOR", "Fall", 2017, 0., false);
		_testContents(s2, "Spring 2018 #1", "BCOS", "Spring", 2018, 0., false);
		_testContents(s3, "Spring 2018 #2", "BSCN", "Spring", 2018, 0., false);
		_testContents(s4, "Spring 2018 #3", "BSIS", "Spring", 2018, 0., false);
		_construct("BCOS", "Summer 2018 #1", "Summer", 2018, 0., false);
		_construct("BCOS", "Summer 2018 #2", "Summer", 2018, 0., false);
		_construct("BCOS", "Summer 2018 #3", "Summer", 2018, 0., false);
		_construct("BCOS", "Summer 2018 #4", "Summer", 2018, 0., false);
		_construct("BCOS", "Summer 2018 #5", "Summer", 2018, 0., false);
		
		_testNext("Fall", 2018, "Fall 2018");
		_testContents(s1, "Fall 2017 #1", "MJOR", "Fall", 2017, 0., false);
		_testContents(s2, "Spring 2018 #1", "BCOS", "Spring", 2018, 0., false);
		_testContents(s3, "Spring 2018 #2", "BSCN", "Spring", 2018, 0., false);
		_testContents(s4, "Spring 2018 #3", "BSIS", "Spring", 2018, 0., false);
		_construct("BCOS", "Fall 2018 #1", "Fall", 2018, 0., false);
		_construct("BCOS", "Fall 2018 #2", "Fall", 2018, 0., false);
		_construct("BCOS", "Fall 2018 #3", "Fall", 2018, 0., false);
		_construct("BCOS", "Fall 2018 #4", "Fall", 2018, 0., false);
		_construct("BCOS", "Fall 2018 #5", "Fall", 2018, 0., false);
	}
	
	public void testAdd() {
		Student s1, s2, s3;
		
		_testReset("Fall", 2017, "Fall 2017");
		s1 = _construct("MJOR", "Fall 2017 #1", "Fall", 2017, 0., false);
		
		_testAdd(s1, 4, 1., "Fall 2017 #1", "MJOR", "Fall", 2017, 1., false);
		_testAdd(s1, 4, 2., "Fall 2017 #1", "MJOR", "Fall", 2017, 1.5, false);
		_testAdd(s1, 4, 4., "Fall 2017 #1", "MJOR", "Fall", 2017, (28./12.), true);
		_testAdd(s1, 3, 3., "Fall 2017 #1", "MJOR", "Fall", 2017, (37./15.), true);
		
		_testNext("Spring", 2018, "Spring 2018");
		_testContents(s1, "Fall 2017 #1", "MJOR", "Fall", 2017, (37./15.), true);
		
		s2 = _construct("BCOS", "Spring 2018 #1", "Spring", 2018, 0., false);
		_testAdd(s2, 4, 4., "Spring 2018 #1", "BCOS", "Spring", 2018, 4., false);
		_testAdd(s2, 4, 3., "Spring 2018 #1", "BCOS", "Spring", 2018, 3.5, false);
		_testAdd(s2, 4, 2., "Spring 2018 #1", "BCOS", "Spring", 2018, 3., true);
		_testAdd(s2, 1, 4., "Spring 2018 #1", "BCOS", "Spring", 2018, (40./13.), true);
		
		s3 = _construct("BSCN", "Spring 2018 #2", "Spring", 2018, 0., false);
		_testAdd(s3, 2, 2., "Spring 2018 #2", "BSCN", "Spring", 2018, 2., false);
		_testAdd(s3, 4, 2., "Spring 2018 #2", "BSCN", "Spring", 2018, 2., false);
		_testAdd(s3, 4, 4., "Spring 2018 #2", "BSCN", "Spring", 2018, (28./10.), false);
		_testAdd(s3, 4, 3., "Spring 2018 #2", "BSCN", "Spring", 2018, (40./14.), true);
		
		_testNext("Summer", 2018, "Summer 2018");
		_testContents(s1, "Fall 2017 #1", "MJOR", "Fall", 2017, (37./15.), true);
		_testContents(s2, "Spring 2018 #1", "BCOS", "Spring", 2018, (40./13.), true);
		_testContents(s3, "Spring 2018 #2", "BSCN", "Spring", 2018, (40./14.), true);
	}
	
	public void testFullTimeComputational() {
		final StudentFilter f = _constructFTComp();
		
		Student s;
		
		_testReset("Fall", 2017, "Fall 2017");
		
		s = _construct("MJOR", "Fall 2017 #1", "Fall", 2017, 0., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 1., "Fall 2017 #1", "MJOR", "Fall", 2017, 1., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 2., "Fall 2017 #1", "MJOR", "Fall", 2017, 1.5, false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #1", "MJOR", "Fall", 2017, (28./12.), true);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 3, 3., "Fall 2017 #1", "MJOR", "Fall", 2017, (37./15.), true);
		
		s = _construct("BCOS", "Fall 2017 #2", "Fall", 2017, 0., false);		
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #2", "BCOS", "Fall", 2017, 4., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #2", "BCOS", "Fall", 2017, 3.5, false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 2., "Fall 2017 #2", "BCOS", "Fall", 2017, 3., true);
		assertTrue("FullTimeComputational", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #2", "BCOS", "Fall", 2017, (40./13.), true);
		assertTrue("FullTimeComputational", f.test(s));
		
		s = _construct("BSIS", "Fall 2017 #3", "Fall", 2017, 0., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #3", "BSIS", "Fall", 2017, 4., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #3", "BSIS", "Fall", 2017, 3.5, false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 2., "Fall 2017 #3", "BSIS", "Fall", 2017, 3., true);
		assertTrue("FullTimeComputational", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #3", "BSIS", "Fall", 2017, (40./13.), true);
		assertTrue("FullTimeComputational", f.test(s));
		
		s = _construct("BSCN", "Fall 2017 #4", "Fall", 2017, 0., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #4", "BSCN", "Fall", 2017, 4., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #4", "BSCN", "Fall", 2017, 3.5, false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 2, 2., "Fall 2017 #4", "BSCN", "Fall", 2017, (32./10.), false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #4", "BSCN", "Fall", 2017, (36./11.), false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #4", "BSCN", "Fall", 2017, (40./12.), true);
		assertTrue("FullTimeComputational", f.test(s));
		
		s = _construct("BSCO", "Fall 2017 #5", "Fall", 2017, 0., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #5", "BSCO", "Fall", 2017, 4., false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #5", "BSCO", "Fall", 2017, 3.5, false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 2, 2., "Fall 2017 #5", "BSCO", "Fall", 2017, (32./10.), false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #5", "BSCO", "Fall", 2017, (36./11.), false);
		assertFalse("FullTimeComputational", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #5", "BSCO", "Fall", 2017, (40./12.), true);
		assertTrue("FullTimeComputational", f.test(s));
	}
	
	public void testLowGPA() {
		final StudentFilter f = _constructLowGPA();
		
		Student s;
		
		_testReset("Fall", 2017, "Fall 2017");
		
		s = _construct("MJOR", "Fall 2017 #1", "Fall", 2017, 0., false);
		assertTrue("LowGPA", f.test(s));
		_testAdd(s, 4, 1., "Fall 2017 #1", "MJOR", "Fall", 2017, 1., false);
		assertTrue("LowGPA", f.test(s));
		_testAdd(s, 4, 2., "Fall 2017 #1", "MJOR", "Fall", 2017, 1.5, false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #1", "MJOR", "Fall", 2017, (28./12.), true);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 3, 3., "Fall 2017 #1", "MJOR", "Fall", 2017, (37./15.), true);
		
		s = _construct("BCOS", "Fall 2017 #2", "Fall", 2017, 0., false);
		assertTrue("LowGPA", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #2", "BCOS", "Fall", 2017, 4., false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #2", "BCOS", "Fall", 2017, 3.5, false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 2., "Fall 2017 #2", "BCOS", "Fall", 2017, 3., true);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #2", "BCOS", "Fall", 2017, (40./13.), true);
		assertFalse("LowGPA", f.test(s));
		
		s = _construct("BSIS", "Fall 2017 #3", "Fall", 2017, 0., false);
		assertTrue("LowGPA", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #3", "BSIS", "Fall", 2017, 4., false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #3", "BSIS", "Fall", 2017, 3.5, false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 2., "Fall 2017 #3", "BSIS", "Fall", 2017, 3., true);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #3", "BSIS", "Fall", 2017, (40./13.), true);
		assertFalse("LowGPA", f.test(s));
		
		s = _construct("BSCN", "Fall 2017 #4", "Fall", 2017, 0., false);
		assertTrue("LowGPA", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #4", "BSCN", "Fall", 2017, 4., false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #4", "BSCN", "Fall", 2017, 3.5, false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 2, 2., "Fall 2017 #4", "BSCN", "Fall", 2017, (32./10.), false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #4", "BSCN", "Fall", 2017, (36./11.), false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #4", "BSCN", "Fall", 2017, (40./12.), true);
		assertFalse("LowGPA", f.test(s));
		
		s = _construct("BSCO", "Fall 2017 #5", "Fall", 2017, 0., false);
		assertTrue("LowGPA", f.test(s));
		_testAdd(s, 4, 4., "Fall 2017 #5", "BSCO", "Fall", 2017, 4., false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 3., "Fall 2017 #5", "BSCO", "Fall", 2017, 3.5, false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 2, 2., "Fall 2017 #5", "BSCO", "Fall", 2017, (32./10.), false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #5", "BSCO", "Fall", 2017, (36./11.), false);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 1, 4., "Fall 2017 #5", "BSCO", "Fall", 2017, (40./12.), true);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 0., "Fall 2017 #5", "BSCO", "Fall", 2017, (40./16.), true);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 0., "Fall 2017 #5", "BSCO", "Fall", 2017, (40./20.), true);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 0., "Fall 2017 #5", "BSCO", "Fall", 2017, (40./24.), true);
		assertFalse("LowGPA", f.test(s));
		_testAdd(s, 4, 0., "Fall 2017 #5", "BSCO", "Fall", 2017, (40./28.), true);
		assertTrue("LowGPA", f.test(s));
	}
	
	public void testFind() {
		Student s1, s2, s3, s4;
		
		final StudentFilter fFTComp = _constructFTComp(); // Full-Time Cmputational
		final StudentFilter fLowGPA = _constructLowGPA(); // Low GPA
		final StudentFilter fPT = s->!s.isFullTime(); // Not full-time
		final StudentFilter fFT = new StudentFilter() {
			@Override
			public boolean test(Student s) {
				return s.isFullTime(); // Full time
			}
		};
		
		_testReset("Fall", 2017, "Fall 2017");
		
		//
		
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {});
		
		//
		
		s1 = _construct("MJOR", "Fall 2017 #1", "Fall", 2017, 0., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s1});
		_testFind(fPT, new Student[] {s1});
		_testFind(fFT, new Student[] {});
		
		s2 = _construct("BCOS", "Fall 2017 #2", "Fall", 2017, 0., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s1, s2});
		_testFind(fPT, new Student[] {s1, s2});
		_testFind(fFT, new Student[] {});
		
		s3 = _construct("BSIS", "Fall 2017 #3", "Fall", 2017, 0., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s1, s2, s3});
		_testFind(fPT, new Student[] {s1, s2, s3});
		_testFind(fFT, new Student[] {});
		
		s4 = _construct("BSCN", "Fall 2017 #4", "Fall", 2017, 0., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s1, s2, s3, s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		//
		
		_testAdd(s1, 4, 3., "Fall 2017 #1", "MJOR", "Fall", 2017, 3., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s2, s3, s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s2, 4, 3., "Fall 2017 #2", "BCOS", "Fall", 2017, 3., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s3, s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s3, 4, 2., "Fall 2017 #3", "BSIS", "Fall", 2017, 2., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s4, 4, 1., "Fall 2017 #4", "BSCN", "Fall", 2017, 1., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		//
		
		_testAdd(s1, 4, 4., "Fall 2017 #1", "MJOR", "Fall", 2017, 3.5, false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s2, 1, 4., "Fall 2017 #2", "BCOS", "Fall", 2017, (16./5.), false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s3, 4, 1., "Fall 2017 #3", "BSIS", "Fall", 2017, (12./8.), false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s4, 1, 4., "Fall 2017 #4", "BSCN", "Fall", 2017, (8./5.), false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {s1, s2, s3, s4});
		_testFind(fFT, new Student[] {});
		
		//
		
		_testAdd(s1, 4, 4., "Fall 2017 #1", "MJOR", "Fall", 2017, (44./12.), true);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {s2, s3, s4});
		_testFind(fFT, new Student[] {s1});
		
		_testAdd(s2, 4, 2., "Fall 2017 #2", "BCOS", "Fall", 2017, (24./9.), false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {s2, s3, s4});
		_testFind(fFT, new Student[] {s1});
		
		_testAdd(s3, 4, 4., "Fall 2017 #3", "BSIS", "Fall", 2017, (28./12.), true);
		_testFind(fFTComp, new Student[] {s3});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {s2, s4});
		_testFind(fFT, new Student[] {s1, s3});
		
		_testAdd(s4, 4, 1., "Fall 2017 #4", "BSCN", "Fall", 2017, (12./9.), false);
		_testFind(fFTComp, new Student[] {s3});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s2, s4});
		_testFind(fFT, new Student[] {s1, s3});
		
		//
		
		_testAdd(s4, 4, 1., "Fall 2017 #4", "BSCN", "Fall", 2017, (16./13.), true);
		_testFind(fFTComp, new Student[] {s3, s4});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s2});
		_testFind(fFT, new Student[] {s1, s3, s4});
		
		_testAdd(s2, 3, 3., "Fall 2017 #2", "BCOS", "Fall", 2017, (33./12.), true);
		_testFind(fFTComp, new Student[] {s2, s3, s4});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {s1, s2, s3, s4});
		
		//
		
		_testNext("Spring", 2018, "Spring 2018");
		
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {});
		
		s1 = _construct("BSCO", "Spring 2018 #1", "Spring", 2018, 0., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s1});
		_testFind(fPT, new Student[] {s1});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s1, 4, 1., "Spring 2018 #1", "BSCO", "Spring", 2018, (4./4.), false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s1});
		_testFind(fPT, new Student[] {s1});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s1, 4, 2., "Spring 2018 #1", "BSCO", "Spring", 2018, (12./8.), false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {s1});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s1, 4, 1., "Spring 2018 #1", "BSCO", "Spring", 2018, (16./12.), true);
		_testFind(fFTComp, new Student[] {s1});
		_testFind(fLowGPA, new Student[] {s1});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {s1});
		
		_testAdd(s1, 4, 3., "Spring 2018 #1", "BSCO", "Spring", 2018, (28./16.), true);
		_testFind(fFTComp, new Student[] {s1});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {s1});
		
		_testAdd(s1, 4, 3.33, "Spring 2018 #1", "BSCO", "Spring", 2018, (41.32/20.), true);
		_testFind(fFTComp, new Student[] {s1});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {s1});
		
		s2 = _construct("BSCO", "Spring 2018 #2", "Spring", 2018, 0., false);
		_testFind(fFTComp, new Student[] {s1});
		_testFind(fLowGPA, new Student[] {s2});
		_testFind(fPT, new Student[] {s2});
		_testFind(fFT, new Student[] {s1});
		
		//
		
		_testNext("Summer", 2018, "Summer 2018");
		
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {});
		
		//
		
		_testNext("Fall", 2018, "Fall 2018");
		
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {});
		_testFind(fPT, new Student[] {});
		_testFind(fFT, new Student[] {});
		
		s4 = _construct("BSCN", "Fall 2018 #1", "Fall", 2018, 0., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s4});
		_testFind(fPT, new Student[] {s4});
		_testFind(fFT, new Student[] {});
		
		s3 = _construct("BSCN", "Fall 2018 #2", "Fall", 2018, 0., false);
		_testFind(fFTComp, new Student[] {});
		_testFind(fLowGPA, new Student[] {s4, s3});
		_testFind(fPT, new Student[] {s4, s3});
		_testFind(fFT, new Student[] {});
		
		_testAdd(s4, 12, 3.5, "Fall 2018 #1", "BSCN", "Fall", 2018, 3.5, true);
		_testFind(fFTComp, new Student[] {s4});
		_testFind(fLowGPA, new Student[] {s3});
		_testFind(fPT, new Student[] {s3});
		_testFind(fFT, new Student[] {s4});
	}
}