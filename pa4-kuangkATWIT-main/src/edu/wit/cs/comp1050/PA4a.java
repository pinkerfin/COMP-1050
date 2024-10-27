package edu.wit.cs.comp1050;

/**
 * 
 * Write a command-line program that decrypts a message that has been encrypted
 * using a Caesar cipher
 * 
 * @author kuangk
 *
 */
public class PA4a {
	
	/**
	 * Error if incorrect command-line arguments are supplied
	 */
	public static final String ERR_USAGE = "Please supply correct inputs: <encrypted string> <substring>";
	
	/**
	 * Error if shift could not be found
	 */
	public static final String ERR_NONE = "No valid shifts found.";

	/**
	 * Outputs all shifts of the encrypted string
	 * that contain the supplied substring
	 * 
	 * @param args command-line arguments: <encrypted string> <substring>
	 */
	public static void main(String[] args) {
		// if incorrect command line args are supplied
		if (args.length != 2) {
			System.out.println(ERR_USAGE);
			System.exit(0);
		}

		final Shifter shifter = new Shifter(args[0]);
		
		final int [] shift = shifter.findShift(args[1]);
		// prints error if there is no valid shift found
		if(shift.length == 0) {
			System.out.println(ERR_NONE);
		}
		else {
			//for(int i = 0; i < shift.length; i++) {
				for(int i : shift) {
				System.out.printf("%02d: %s%n", i, shifter.shift(i));
			}
		}
	}

}
