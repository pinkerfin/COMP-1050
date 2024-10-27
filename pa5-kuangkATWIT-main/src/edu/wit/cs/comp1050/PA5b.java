package edu.wit.cs.comp1050;

import java.util.ArrayList;

/**
 * 
 * a simple pattern-matching method
 * 
 * @author kuangk
 *
 */
public class PA5b {
	
	/**
	 * Returns an array of all sequences of substrings
	 * of haystack that match the needle pattern.
	 * 
	 * The dash (-) is a single-character wildcard (i.e.
	 * it can match any character), all others much
	 * match directly.
	 *
	 * You may not use existing pattern matching or regular expression methods!
	 * 
	 * @param needle pattern for which to search
	 * @param haystack text over which to search
	 * @return an array of substrings of haystack that match needle
	 */
	public static String[] patternMatch(String needle, String haystack) {
		// suggestion: consider adding a helper method to check if one substring of haystack matches needle
		final ArrayList<String> find = new ArrayList<String>();
		final StringBuilder sb = new StringBuilder(needle);
		
		for(int i = 0; i <= (haystack.length() - needle.length()); i++) {
			boolean found = true;
			
			for(int j = 0; j < needle.length(); j++) {
				if((needle.charAt(j) != haystack.charAt(i+j))
				&& (needle.charAt(j) != '-' )) {
					found = false;
				}
			}
			
			if(found) {
//				for(int k = 0; k < needle.length(); k++) {
//					sb.setCharAt(k, haystack.charAt(i + k));
//				}
				for(int j = 0; j < needle.length(); j++) {
					sb.setCharAt(j, haystack.charAt(i + j));
				}
				find.add(sb.toString());
		}
	}
		final String[] ans = new String[find.size()]; 
				for(int i = 0; i < find.size(); i++) {
					ans[i] = find.get(i);
				}
			return ans;
	}
}