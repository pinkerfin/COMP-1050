package edu.wit.cs.comp1050;

import java.util.ArrayList;
import java.util.List;

/**
 * Your task is to implement three classes 
 * that support queries over a simple database2 of students for a
 * semester. To begin, implement the following static methods 
 * related to setting and advancing semesters
 * 
 * @author kuangk
 *
 */

public class Student {
	// hint: think carefully about your static variables (take advantage of the below SEMESTERS array for the semester)
	// hint: also think carefully about your instance variables for each student
	
	/**
	 * Semester names
	 */
	public static final String[] SEMESTERS = {"Spring", "Summer", "Fall"};
	// 0, 1, 2
	
	/**
	 * Credits threshold for full-time classification 
	 */
	public static final int FT_CREDITS = 12;
	
	//
		final private static ArrayList<Student> all = new ArrayList<Student>();
		private static int currentSemester;
		private static int currentYear;
		
		private static void temp(int sem, int year) {
			currentSemester = sem;
			currentYear = year;
			all.clear();
		}
		
	/**
	 * Sets the current semester to Fall 2017 and clears the database of students
	 */
	public static void resetSemesterYear() {
//		currentSemester = 2;
//		currentYear = 2017;	
		temp(2, 2017);
	}
	
	/**
	 * Sets to the semester following the
	 * current semester and clears the database of students
	 */
	public static void nextSemester() {
//		final int s = currentSemester++;
//		final int y = currentYear++;
		
		final int s = (currentSemester + 1) % SEMESTERS.length;
		if(currentSemester == 2) {
			currentYear++;
		}
		final int y = currentYear;
		
//		final int y = currentYear + ((s==2)?1:0);

//		if(currentSemester > 2) {
//			final int s = currentSemester + 1;
//		} else {final int s = currentSemester -1;}
//		final int y = currentYear;

//		int s = currentSemester; 
//		int y = currentYear;
		
//		if(currentSemester == 0 || currentSemester == 1) { // spring summer fall
//			s = currentSemester++;
//		}else if (currentSemester == 2) {
//			s = currentSemester = 0;
//		}
		
//		s = (currentSemester+1) % SEMESTERS.length;
//		y = currentYear + ((s==2)?1:0);
//		
		temp(s, y);
//		all.clear();
	}
	
	/**
	 * Gets the current semester
	 * 
	 * @return Summer/Fall/Spring
	 */
	public static String getCurrentSemester() {
		return SEMESTERS[currentSemester];
	}
	
	/**
	 * Gets the current year
	 * 
	 * @return current year
	 */
	public static int getCurrentYear() {
		return currentYear;
	}
	
	/**
	 * Gets the current semester and year
	 * 
	 * @return "semester year"
	 */
	public static String getCurrentSemesterYear() {
		return String.format("%s %d", getCurrentSemester(), getCurrentYear());
	}
	
	/**
	 * Searches all students in the current semester/year
	 * (in order of construction) and returns a list
	 * that pass a supplied filter
	 * 
	 * @param f filter to apply to each student
	 * @return a list of students that satisfy the filter
	 */
	public static List<Student> find(StudentFilter f) {
		final ArrayList<Student> find = new ArrayList<>();
		
		for(Student s : all) {
			if (f.test(s)) {
				find.add(s);
			}
		}
			return find;
	}
	
	//
	final private int sId;
	final private String sMajor;
	final private int sSem;
	final private int sYear;
	
	private double sPoints;
	private int sCredits;
	
	/**
	 * Creates a new student, in a supplied major,
	 * associated with the current semester/year
	 * 
	 * @param major student major
	 */

	
	public Student(String major) {
		// hint: think about all the things you'll have to do here
		//       for both instance and static variables
		
		sSem = currentSemester;
		sYear = currentYear;
		
		all.add(this);
		sId = all.size();
		
		sMajor = major;
		
		sPoints = 0;
		sCredits = 0;
		
	}
	
	/**
	 * String representation of the student.
	 * The "studentnum" portion refers to
	 * the order in which the student was
	 * constructed in the current semester/year,
	 * starting at #1, #2, ... 
	 * 
	 * @return "semester year #studentnum"
	 */
	@Override
	public String toString() {
		return String.format("%s %d #%d", SEMESTERS[sSem], sYear, sId);
	}
	
	/**
	 * Gets the semester for this
	 * student (may be different
	 * than the current semester)
	 * 
	 * @return Summer/Fall/Spring
	 */
	public String getSemester() {
		return SEMESTERS[sSem];
	}
	
	/**
	 * Gets the year for this
	 * student (may be different
	 * than the current year)
	 * 
	 * @return year
	 */
	public int getYear() {
		return sYear;
	}
	
	/**
	 * Gets the major for this
	 * student
	 * 
	 * @return student major
	 */
	public String getMajor() {
		return sMajor;
	}
	
	/**
	 * Adds a weighted course result 
	 * 
	 * @param credits course credits (assumed to be positive)
	 * @param weight numeric weight (e.g. A = 4; assumed to be positive)
	 */
	public void addClass(int credits, double weight) {
		// hint: you can compute the GPA as a running GPA and so don't 
	    //       need to store every credit/weight pair
		sPoints += (credits*weight);
		sCredits += credits;
	}
	
	/**
	 * Returns true if the student
	 * is full time with respect to
	 * currently added course credits
	 * 
	 * @return true if credits is at least the threshold
	 */
	public boolean isFullTime() {
		return sCredits >= FT_CREDITS;
//		if(sCredits >= FT_CREDITS) {
//			return true;
//		}	else return false;
	}
	
	/**
	 * Computes the student's current GPA
	 * with respect to added course credits/
	 * weights
	 * 
	 * @return student GPA (0 if no credits)
	 */
	public double getGPA() {
		if(sCredits > 0) {
			return sPoints / sCredits;
		} else {
			return 0;
		}
		
	}
	
	public static void main(String[] args) {
		Student.resetSemesterYear(); // Spring 2017
		Student.nextSemester(); // Summer 2017
		StudentFilter f1 = new LowGPA();
		StudentFilter f2 = new FullTimeComputational();
		
		Student s1 = new Student("BCOS"); // Summer 2017 #1
		Student s2 = new Student("BMED"); // Summer 2017 #2
		System.out.println(Student.find(f1)); // [Summer 2017 #1, Summer 2017 #2]
		System.out.println(Student.find(f2)); // []
		s1.addClass(4, 3); // GPA=3.0, FT=false
		s1.addClass(4, 3.5); // GPA=3.25, FT=false
		s1.addClass(4, 4); // GPA=3.5, FT=true
		s2.addClass(4, 3); // GPA=3.0, FT=false
		s2.addClass(4, 3.5); // GPA=3.25, FT=false
		s2.addClass(4, 4); // GPA=3.5, FT=true
		s2.addClass(4, 4); // GPA=3.625, FT=true
		System.out.println(Student.find(f1)); // []
		System.out.println(Student.find(f2)); // [Summer 2017 #1]
		Student.nextSemester(); // Fall 2017
		Student s3 = new Student("BSIS"); // Fall 2017 #1
		System.out.println(Student.find(f1)); // [Fall 2017 #1]
		System.out.println(Student.find(f2)); // []
		
	}
	
}