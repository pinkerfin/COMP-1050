package edu.wit.cs.comp1050;

import java.io.PrintWriter;
import java.util.Scanner;
// thanks
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * You are to write a program that reads two matrices from files, 
 * multiples them, and displays outputs both  to the terminal 
 * and a result file.
 *
 */
public class PA2c {
	
	/**
	 * Error to output if can't open any files
	 */
	public static final String ERR_FILE = "Error opening file(s)";
	
	/**
	 * Error to output if files open but matrices
	 * are of incompatible dimensions for multiplication
	 */
	public static final String ERR_DIMS = "Bad matrix dimensions";
	
	private static void _outputMatrix(PrintWriter out, int[][] m, boolean includeDimensions) {		
		for (int r=0; r<m.length; r++) {
			if (includeDimensions && r==0) {
				out.printf("%d%n%d%n", m.length, m[0].length);
			}
			for (int c=0; c<m[r].length; c++) {
				out.printf("%d", m[r][c]);
				out.printf((c<m[r].length-1)?" ":"%n");
			}
		}
	}
	
	/**
	 * Prints a matrix to the terminal
	 * without dimensions
	 * 
	 * @param m matrix to print
	 */
	public static void printMatrix(int[][] m) {
		_outputMatrix(new PrintWriter(System.out, true), m, false);
	}
	
	/**
	 * Prints a matrix to a file
	 * with associated dimensions
	 * 
	 * @param m matrix to print
	 * @param pw open file
	 */
	public static void printMatrix(int[][] m, PrintWriter pw) {
		_outputMatrix(pw, m, true);
	}
	
	
	
	
	
	
	// start here ====================================================================
	/**
	 * Checks if two matrices can be multiplied
	 * (i.e. the columns of the first match
	 * the rows of the second)
	 * 
	 * @param m1 matrix 1
	 * @param m2 matrix 2
	 * @return true if m1 x m2 is legal
	 */
	public static boolean canMultiply(int[][] m1, int[][] m2) {
		// if they are both equal to zero. can't be multiplied
		if(m1.length == 0 || m2.length == 0) {
			return false;
		}
		int m1Column = m1[0].length; // initializes the number for columns
		int m2Row= m2.length; // initializes the number of rows
		
		if(m1Column == m2Row) { // if they are equal in length
			return true;
		}
		return false;
	}
	
	/**
	 * Reads and returns a matrix from a scanner
	 * Format:
	 * m (# rows)
	 * n (# #cols)
	 * r0c0 r0c1 ... r0cn (values in row 0, column-by-column)
	 * r1c0 r1c1 ... r1cn (values in row 1, column-by-column)
	 * ...
	 * rmc0 rmc1 ... rmcn (values in last row, column-by-column)
	 * 
	 * Results in...
	 * int[][] {
	 * 	{r0c0, r0c1, ... r0cn},
	 *  {r1c0, r1c1, ... r1cn},
	 *  ...
	 *  {rmc0, rmc1, ... rmcn}
	 * }
	 * 
	 * @param s input source
	 * @return resulting matrix
	 */
	public static int[][] readMatrix(Scanner s) {
		final int mRows = s.nextInt(); // the rows read entered
		final int mColumns = s.nextInt(); // the columns read entered
		
		final int [][] matrix = new int[mRows][mColumns];
		
		for(int i = 0; i < mRows; i++) { // loop for the length of the matrix
			for(int j = 0; j < mColumns; j++) {
				matrix[i][j] = s.nextInt();
			}
		}
		return matrix;
	}
	
	/**
	 * Multiply two matrices and
	 * return the result
	 * 
	 * @param m1 matrix 1
	 * @param m2 matrix 2
	 * @return result of m1 x m2
	 */
	public static int[][] matrixMultiply(int[][] m1, int[][] m2) {		
	
		int m1Col = m1[0].length; // the length of m1 (columns/up)
		int m3Row = m1.length; // row length 
		int m3Col = m2[0].length; // column length
		
		int[][] m3 = new int[m3Row][m3Col];
		
		
		for(int i = 0; i < m3Row; i++) {
			for(int j = 0; j < m3Col; j++) {
				for(int k = 0; k < m1Col; k++) {
					//m3[i][j] += m1[i][k] * m2[j][k];
					m3[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return m3;
	}
	
// https://stackoverflow.com/questions/33578785/reading-two-matrices-from-two-files-and-outputting-a-matrix-of-the-two-multiplie
	/**
	 * Program to multiply matrices:
	 * 1. Ask for paths for 3 files (2 input, 1 output)
	 * 2. Check if inputs can be multiplied
	 * 3. If so, multiply!
	 *    - Output the full problem to the console
	 *    - Output only the result matrix to the file
	 * 
	 * @param args command-line arguments, ignored
	 */
	public static void main(String[] args){
		// Hint: paths should be read as an entire line!
		Scanner input = new Scanner(System.in);
		
// 1. Ask for paths for 3 files (2 input, 1 output)
		System.out.print("Enter path for matrix 1: ");
		String input1 = input.nextLine();
		
		System.out.print("Enter path for matrix 2: ");
		String input2 = input.nextLine();
		
		System.out.print("Enter path for result: ");
		String result = input.nextLine();
		input.close();
	
		// ==============
		// Initialize all the scanner in the try block
		try(Scanner fin1 = new Scanner(new File(input1)); // Separated by semicolon
			Scanner	fin2 = new Scanner(new File(input2)); // Tried to make it in a separate try block
			PrintWriter	output = new PrintWriter(new File(result)); // Allows it to write into the file
		){ 
			int[][] m1 = readMatrix(fin1); 
			int[][] m2 = readMatrix(fin2);
			
			
			printMatrix(m1);
			System.out.printf("X%n");
			printMatrix(m2);
			System.out.printf("=%n");
			
			
			if(canMultiply(m1, m2)) {
				int[][] m3 = matrixMultiply(m1, m2);
				printMatrix(m3);
				printMatrix(m3, output);
			} else {
				System.out.printf("%s%n", ERR_DIMS);
			}
			
		}	
			catch(IOException ex) {
				System.out.println(ERR_FILE);
				}
			}
		}
//https://www.youtube.com/watch?v=Bonrwnqpw1I