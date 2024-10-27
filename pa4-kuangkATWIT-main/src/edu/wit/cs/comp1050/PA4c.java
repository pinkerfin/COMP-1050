package edu.wit.cs.comp1050;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * A program that reads any number of integers from the 
 * console (via a Scanner), stores
 * them in an ArrayList, removes duplicates from the list, 
 * and then outputs the remaining distinct values.
 * 
 * @author kuangk
 *
 */
public class PA4c {
	
	/**
	 * Removes all duplicate values
	 * from the supplied list
	 * 
	 * @param list list from which to remove repeated elements
	 */
	public static void removeRepeated(ArrayList<Integer> list) {
		final ArrayList<Integer> hold = new ArrayList<>();
		
//		for (int i = 0; i < list.hashCode(); i++) {
		for (int i : list) {
			if(!hold.contains(i)) {
				hold.add(i);
			}
		}
		list.clear(); list.addAll(hold); list.trimToSize();
	
	}

	/**
	 * Reads numbers from the keyboard and
	 * outputs the list of distinct values
	 * 
	 * @param args command-line arguments, ignored
	 */
	public static void main(String[] args) {
		final ArrayList<Integer> list = new ArrayList<>();
		final Scanner input = new Scanner(System.in);
		
		System.out.print("Enter integers: ");
		while(input.hasNextInt()) {
			list.add(input.nextInt());
		}
		
		if(list.isEmpty()) {
			System.out.print("No values entered.");
		}	else {
			removeRepeated(list);
			System.out.print("The distinct integer(s):");
//		for (int i = 0; i < list.hashCode(); i++) {
		for (int i : list) {
			System.out.print(" " + i);
		}
	}
		System.out.println();
		input.close();
		
	}

}
