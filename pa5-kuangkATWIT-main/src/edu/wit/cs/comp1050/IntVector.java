package edu.wit.cs.comp1050;

import java.util.function.BiPredicate;
import java.util.List;

/**
 * Using only primitives, arrays, and StringBuilder (for an efficient toString implementation) 
 * Implement an IntVector class; which is basically an array, 
 * but one that automatically changes in size to accommodate new/removed items.
 * 
 * @author kuangk
 *
 */
public class IntVector {
	
	// hint: think about what variables you need here (should only be two)
	private int size, cap;
	private int[] arr;
	
	/**
	 * Copies the first n values from
	 * src to dest
	 * 
	 * @param src source array (assumed to be large enough)
	 * @param dest destination array (assumed to be large enough)
	 * @param n how many values to copy
	 * @return destination array
	 */
	public static int[] copyTo(int[] src, int[] dest, int n) {
		for (int i = 0; i < n ; i++) {
			dest[i] = src[i];
		}
		return dest;
	}
	
	/**
	 * Computes new capacity when adding to the size:
	 * if enough capacity, return the current capacity;
	 * otherwise repeatedly double the capacity
	 * until enough (if capacity is 0, "doubling" it
	 * results in 1)
	 * 
	 * @param capacity current capacity
	 * @param size current size
	 * @param toAdd elements to add
	 * @return new capacity
	 */
	public static int doubleIfNeeded(int capacity, int size, int toAdd) {
		// hint: don't forget to handle the special case of capacity 0
		final int size2 = size + toAdd;
		
		while(capacity < size2) {
			if(capacity == 0) {
				capacity = 1; // change it to 1 lol
			} else {
			capacity = capacity * 2;
			}
		}
		return capacity;
	}
	
	//
	
	/**
	 * Initialize with capacity=10, size=0
	 */
	public IntVector() {
		// replace with your code
		// hint: use this()!
		this(10);
	}
	
	/**
	 * Initialize with size=0 and
	 * the supplied initial capacity (0
	 * if negative)
	 * 
	 * @param initCapacity initial capacity
	 */
	public IntVector(int initCapacity) {
		size = 0;
		if (initCapacity > 0) {
			cap = initCapacity;
			arr = new int[initCapacity];
		} else {
			cap  = 0;
			arr = null;
		}
	}
	
	/**
	 * Initialize with provided initial size,
	 * all of which have the same initial value.
	 * Capacity should be same as size.
	 * (0 if supplied invalid size.)
	 * 
	 * @param initSize initial size
	 * @param initValue initial value of all elements
	 */
	public IntVector(int initSize, int initValue) {
		if(initSize < 0) {
			initSize = 0;
		}
		
		size = cap = initSize;
		if(cap > 0) {
			arr = new int[cap];
			
			for(int i = 0; i < cap ; i++) {
				arr[i] = initValue;
			}
		}
		else {
			arr = null;
		}
	}
	
	/**
	 * Initializes the size,
	 * capacity, and values
	 * via a source array
	 * (0 if null)
	 * 
	 * @param source initial contents
	 */
	public IntVector(int[] source) {
		// replace with your code
		// hint: copyTo will help here
		
		if(source == null) {
			source = new int[] {};
			}
		
		size = cap = source.length;
		if (size > 0) {
			arr = copyTo(source, new int[size], size);
		} else {
			arr = null;
		}
	}
	
	/**
	 * [e_0, e_1, ... e_size] 
	 * 
	 * @return string representation
	 */
	@Override
	public String toString() {
		// hint: don't forget to use a StringBuilder
		final StringBuilder sb = new StringBuilder();
	
		sb.setLength(0);
		sb.append("[");
		
		if (arr != null && size > 0) {
			sb.append(String.valueOf(arr[0]));
			for (int i = 1; i < size; i++) {
				sb.append(String.format(", %d", arr[i]));
			}
		} sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Returns true if supplied
	 * object is also an IntVector
	 * and has the same size/contents
	 * (independent of capacity)
	 * 
	 * @param o other object
	 * @return true if same contents
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof IntVector) {
			final IntVector vector = (IntVector) o;
			if(vector.size == size) {
				for(int i = 0; i < size; i++) {
					if(arr[i] != vector.arr[i]) {
						return false;
					}
				} return true;
		}	else {
			return false;
		}
	} else {
		return false;
	}
}
	
	/**
	 * Returns true if supplied
	 * index is valid:
	 * 0 <= index < size
	 * 
	 * @param index index to check
	 * @return true if valid index
	 */
	public boolean validIndex(int index) {
		return checkValid(index, false);
		
//		if(0 <= index && index < size) {
//			//return (index >= 0 && index <= size);
//			return true;
//		} else {
//			//return (index >= 0 && index < size);
//			return false;
//		}
	}
	
	private boolean checkValid(int index, boolean b) {
		if (!b) {
			return (index >= 0 && index < size);
		} else {
			return (index >= 0 && index <= size);
		}
	}

	/**
	 * Gets the current size
	 * 
	 * @return current size
	 */
	public int getSize() {
		return size; 
	}
	
	/**
	 * Returns true if empty
	 * 
	 * @return true if size is 0
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns current capacity
	 * 
	 * @return current capacity
	 */
	public int getCapacity() {
		return cap; 
	}
	
	/**
	 * Gets the element at supplied
	 * index, null if invalid
	 * index
	 * 
	 * @param index desired index
	 * @return corresponding element, null if invalid index
	 */
	public Integer get(int index) {
		// hint: validIndex() will help here
	if(validIndex(index)) {
		return arr[index];
	} else {
		return null;
	}
	}
	
	/**
	 * Returns an array with
	 * the current contents
	 * 
	 * @return array with current elements
	 */
	public int[] toArray() {
		// hint: copyTo() will help here
		return copyTo(arr, new int[size], size);
	}
	
	/**
	 * Changes the value of an element
	 * 
	 * @param index index to change
	 * @param value value to set
	 * @return new value, null if invalid index
	 */
	public Integer set(int index, int value) {
		// hint: validIndex() will help here
		if (validIndex(index)) {
			return arr[index] = value;
		} else {
			return null;
		}
	}
	
	/**
	 * Reverses the contents
	 * 
	 * [1, 2, 3] => [3, 2, 1]
	 */
	public void reverse() {
// https://stackoverflow.com/questions/2137755/how-do-i-reverse-an-int-array-in-java
		for (int i = 0; i < size / 2; i++) {
		    int temp = arr[i];
		    arr[i] = arr[size - i - 1];
		    arr[size - i - 1] = temp;
		}
	}
	
	/**
	 * Makes sure there is room for
	 * at least n elements. If greater
	 * than current capacity, n becomes
	 * the new capacity.
	 * 
	 * @param n desired minimum capacity
	 */
	public void ensureCapacity(int n) {
		// replace with your code
		// hint: copyTo() will be useful here
		if(n > cap) {
			if (arr == null) {
				arr = new int[n];
			} else {
				arr = copyTo(arr, new int[n], size);
			}	
			cap = arr.length;
		}	
	}
	
	/**
	 * Adds an element to the end.
	 * 
	 * Capacity should increase according
	 * to the doubling policy (see
	 * doubleIfNeeded).
	 * 
	 * @param value value to add
	 * @return value added
	 */
	public int add(int value) {
		ensureCapacity(doubleIfNeeded(cap, size, 1));
		return (arr[size++] = value);
	}
	
	/**
	 * Add a value at a supplied index.
	 * If not the end, all values from
	 * the index are shifted right.
	 * 
	 * Capacity should increase according
	 * to the doubling policy (see
	 * doubleIfNeeded).
	 * 
	 * If invalid index, no change is made. Note
	 * that an index exactly equal to the size is allowed.
	 * 
	 * @param index index at which to insert
	 * @param value value to insert
	 * @return value added, null if invalid index
	 */
	public Integer add(int index, int value) {
	    // hint: you'll need doubleIfNeeded() and ensureCapacity() here
	    // hint: don't forget that an index equal to the size means to add to the end
		
		if(!checkValid(index, true)) { return null; }
		
		ensureCapacity(doubleIfNeeded(cap, size, 1));
		size++;
		
		for(int i = (size - 1); i > index; i--) {
			arr[i] = arr[i - 1];
		}
		arr[index] = value;
			return value;
		}
	
	/**
	 * Add a list of values to the end.
	 * 
	 * Capacity should increase according
	 * to the doubling policy (see
	 * doubleIfNeeded).
	 * 
	 * @param values list of values to add in order
	 */
	public void addAll(List<Integer> values) {
		// hint: take advantage of the other addAll() method!
		ensureCapacity(doubleIfNeeded(cap, size, values.size()));
		for (int i : values) {
			arr[size++] = i;
		}
	}
	
	/**
	 * Add a list of values at a supplied index.
	 * If not the end, all values from index
	 * are shifted right.
	 * 
	 * Capacity should increase according
	 * to the doubling policy (see
	 * doubleIfNeeded).
	 * 
	 * If invalid index, no change is made. Note
	 * that an index exactly equal to the size is allowed.
	 * 
	 * @param index
	 * @param values
	 */
	public void addAll(int index, List<Integer> values) {
		// replace with your code
		// hint: you'll need doubleIfNeeded() and ensureCapacity() here
		// hint: don't forget that an index equal to the size means to add to the end

	if(checkValid(index, true)) {
		final int add = values.size();
		
		ensureCapacity(doubleIfNeeded(cap, size, add));
		size += values.size();
		
		for (int i=(size-1); i>(index+add-1); i--) {
			arr[i] = arr[i-add];
		}
		
		for(int i = 0; i < add; i++) {
			arr[index + i] = values.get(i);
		}
	}
}
	//		if (validIndex(index) == true) {
//			final int numToAdd = values.size();
//			
//			ensureCapacity(doubleIfNeeded(cap, size, numToAdd));
//			size =  size + values.size();
//			
//			for (int i=(size-1); i>(index+numToAdd-1); i--) {
//				arr[i] = arr[i-numToAdd];
//			}
//			
//			for (int i=0; i<numToAdd; i++) {
//				arr[index+i] = values.get(i);
//			}
//		}
	
	
	/**
	 * Sets the size to 0
	 * without changing
	 * capacity.
	 */
	public void clear() {
		size = 0;
	}
	
	/**
	 * Reduces the capacity to
	 * the current size
	 */
	public void trimToSize() {
		// replace with your code
		// hint: copyTo() will help here
		
		if (cap != size) {
			if (size == 0) {
				arr = null;
			} else {
				arr = copyTo(arr, new int[size], size);
			}
			cap = size;
		}
	}
	
	/**
	 * Remove the last element in the list
	 * 
	 * @return removed element, null if was empty
	 */
	public Integer removeLast() {
	    // hint: take advantage of the other remove() method here!
		if (size > 0) {
			return arr[--size];
		} else {
			return null;
		}
	}
	
	/**
	 * Remove element at supplied index.
	 * All elements after are shifted
	 * left.
	 * 
	 * @param index index to remove
	 * @return removed value, null if invalid index
	 */
	public Integer remove(int index) {
	    // hint: don't forget to use validIndex() here
		if (validIndex(index)) {
			final int value = arr[index];
			for (int i = index; i < (size-1); i++) {
				arr[i] = arr[i+1];
			}	size--;
			return value;
		} else {
			return null;
		}
	}
	
	/**
	 * Removes the first element that has
	 * the supplied value.
	 * 
	 * @param value value to remove
	 * @return value, null if not found
	 */
	public Integer removeFirst(int value) {
		for (int i = 0; i < size; i++) {
			if (arr[i] == value) {
				return remove(i);
			}
		}
		return null;
	}

	/**
	 * Removes all elements of a
	 * particular value.
	 * 
	 * @param value value to remove
	 * @return number of elements removed
	 */
	public interface IntTester extends BiPredicate<Integer, Integer> {}

	public int removeAllIf(IntTester p) {
		final boolean[] b = new boolean[size];
		
		int count = 0;
		for (int i=0; i<size; i++) {
			if (p.test(i, arr[i])) {
				b[i] = true;
				count++;
			}
		}
		
		int removed = 0;
		for (int i=0; i<b.length; i++) {
			if (b[i]) {
				remove(i - (removed++));
			}
		}
		
		return count;
	}	
	
	public int removeAll(int value) {
		return removeAllIf((i, v) -> v == value);
	}
}