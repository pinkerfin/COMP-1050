package edu.wit.cs.comp1050;

/**
 * Filters students who are full time with valid majors
 * 
 * @author kuangk
 *
 */
public class FullTimeComputational implements StudentFilter {

    // hint: consider adding an array of valid computational majors here
  //  String[] valid = {"BCOS", "BSCN", "BSIS", "BSCO"};
	/**
	 * Filter for students that are full time
	 * and in a computational major:
	 * BCOS/BSCN/BSIS/BSCO
	 * 
	 * @param s student to consider
	 * @return true if student passes
	 */
	@Override
	public boolean test(Student s) {
		return (s.isFullTime() && (s.getMajor().equals("BCOS") || s.getMajor().equals("BSCN") || s.getMajor().equals("BSIS") || s.getMajor().equals("BSCO")));

//		return (s.isFullTime() && (s.getMajor().equals(valid)));
	}

}
