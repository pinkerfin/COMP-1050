
package edu.wit.cs.comp1050.tests;

import java.security.Permission;
import java.util.Arrays;

import org.junit.Assert;

import edu.wit.cs.comp1050.IntVector;
import junit.framework.TestCase;

public class PA5aTestCase extends TestCase {
	
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
	
	private void _testContents(IntVector v, int sizeE, int capE, String sE, int[] aE) {
		assertEquals(String.format("Size"), sizeE, v.getSize());
		assertEquals(String.format("Capacity"), capE, v.getCapacity());
		assertEquals(String.format("toString"), sE, v.toString());
		
		if (sizeE>0) {
			assertFalse("isEmpty", v.isEmpty());
		} else {
			assertTrue("isEmpty", v.isEmpty());
		}
		
		//
		
		final int[] badIndexesAll = {-100, -1, -12};
		
		for (int i : badIndexesAll) {
			assertFalse(String.format("validIndex(%d)", i), v.validIndex(i));
			assertNull(String.format("get(%d)", i), v.get(i));
		}
		
		if (aE != null) {
			Assert.assertArrayEquals("toArray", aE, v.toArray());
			
			final int[] badIndexes = {aE.length, aE.length+1, aE.length*2};
			for (int i : badIndexes) {
				assertFalse(String.format("validIndex(%d)", i), v.validIndex(i));
				assertNull(String.format("get(%d)", i), v.get(i));
			}
			
			for (int i=0; i<aE.length; i++) {
				assertTrue(String.format("validIndex(%d)", i), v.validIndex(i));
				assertEquals(String.format("get(%d)", i), aE[i], (int) v.get(i));
			}
			
			assertTrue("equals", v.equals(new IntVector(aE)));
			assertFalse("equals", v.equals(aE));
			
			if (aE.length==0) {
				assertFalse("equals", v.equals(new IntVector(new int[] {1})));
			} else {
				assertFalse("equals", v.equals(new IntVector(new int[] {})));
				
				final int[] badA = new int[aE.length];
				for (int i=0; i<aE.length; i++) {
					badA[i] = aE[i];
				}
				badA[badA.length-1]--;
				
				assertFalse("equals", v.equals(new IntVector(badA)));
			}
		}
	}
	
	private IntVector _construct(int sizeE, int capE, String sE, int[] aE) {
		IntVector v;
		
		v = null;
		try {
			v = new IntVector();
		} catch (ExitException ex) {}
		assertNotNull("Could not construct via no-arg", v);
		
		_testContents(v, sizeE, capE, sE, aE);
		
		return v;
	}
	
	private IntVector _construct(int initCapacity, int sizeE, int capE, String sE, int[] aE) {
		IntVector v;
		
		v = null;
		try {
			v = new IntVector(initCapacity);
		} catch (ExitException ex) {}
		assertNotNull("Could not construct via initCapacity", v);
		
		_testContents(v, sizeE, capE, sE, aE);
		
		return v;
	}
	
	private IntVector _construct(int initSize, int initValue, int sizeE, int capE, String sE, int[] aE) {
		IntVector v;
		
		v = null;
		try {
			v = new IntVector(initSize, initValue);
		} catch (ExitException ex) {}
		assertNotNull("Could not construct via initSize/Value", v);
		
		_testContents(v, sizeE, capE, sE, aE);
		
		return v;
	}
	
	private IntVector _construct(int[] source, int sizeE, int capE, String sE) {
		IntVector v;
		
		v = null;
		try {
			v = new IntVector(source);
		} catch (ExitException ex) {}
		assertNotNull("Could not construct via array", v);
		
		_testContents(v, sizeE, capE, sE, source);
		
		return v;
	}
	
	private void _testCopyTo(int[] s, int[] d, int n, int[] aE) {
		int[] r;
		
		r = null;
		try {
			r = IntVector.copyTo(s, d, n);
		} catch (ExitException ex) {}
		assertEquals("copyTo returns dest", d, r);
		Assert.assertArrayEquals("dest contents", aE, r);
	}
	
	private void _testDoubleIfNeeded(int capacity, int size, int toAdd, int e) {
		Integer r;
		
		r = null;
		try {
			r = IntVector.doubleIfNeeded(capacity, size, toAdd);
		} catch (ExitException ex) {}
		assertNotNull(String.format("doubleIfNeeded(%d, %d, %d)", capacity, size, toAdd), r);
		assertEquals(String.format("doubleIfNeeded(%d, %d, %d)", capacity, size, toAdd), e, (int) r);
	}
	
	private void _testSet(IntVector v, int idx, int value, Integer rE, int sizeE, int capE, String sE, int[] aE) {
		Integer result;
		
		result = (rE==null)?-1:null;
		try {
			result = v.set(idx, value);
		} catch (ExitException ex) {}
		assertEquals(String.format("set(%d, %d) return", idx, value), rE, result);
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testReverse(IntVector v, int sizeE, int capE, String sE, int[] aE) {
		try {
			v.reverse();
		} catch (ExitException ex) {}
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testEnsure(IntVector v, int n, int sizeE, int capE, String sE, int[] aE) {
		try {
			v.ensureCapacity(n);
		} catch (ExitException ex) {}
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testAdd(IntVector v, int value, int rE, int sizeE, int capE, String sE, int[] aE) {
		Integer result;
		
		result = null;
		try {
			result = v.add(value);
		} catch (ExitException ex) {}
		assertEquals(String.format("add(%d) return", value), rE, (int) result);
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testAddAll(IntVector v, Integer[] values, int sizeE, int capE, String sE, int[] aE) {
		try {
			v.addAll(Arrays.asList(values));
		} catch (ExitException ex) {}
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testAdd(IntVector v, int index, int value, Integer rE, int sizeE, int capE, String sE, int[] aE) {
		Integer result;
		
		result = value + 1;
		try {
			result = v.add(index, value);
		} catch (ExitException ex) {}
		assertEquals(String.format("add(%d, %d) return", index, value), rE, result);
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testAddAll(IntVector v, int index, Integer[] values, int sizeE, int capE, String sE, int[] aE) {
		try {
			v.addAll(index, Arrays.asList(values));
		} catch (ExitException ex) {}
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testRemoveLast(IntVector v, Integer rE, int sizeE, int capE, String sE, int[] aE) {
		Integer result;
		
		result = Integer.MAX_VALUE;
		try {
			result = v.removeLast();
		} catch (ExitException ex) {}
		assertEquals(String.format("removeLast() return"), rE, result);
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testRemove(IntVector v, int index, Integer rE, int sizeE, int capE, String sE, int[] aE) {
		Integer result;
		
		result = Integer.MAX_VALUE;
		try {
			result = v.remove(index);
		} catch (ExitException ex) {}
		assertEquals(String.format("remove(%d) return", index), rE, result);
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testRemoveFirst(IntVector v, int value, Integer rE, int sizeE, int capE, String sE, int[] aE) {
		Integer result;
		
		result = Integer.MAX_VALUE;
		try {
			result = v.removeFirst(value);
		} catch (ExitException ex) {}
		assertEquals(String.format("removeFirst(%d) return", value), rE, result);
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testRemoveAll(IntVector v, int value, int rE, int sizeE, int capE, String sE, int[] aE) {
		Integer result;
		
		result = Integer.MAX_VALUE;
		try {
			result = v.removeAll(value);
		} catch (ExitException ex) {}
		assertEquals(String.format("removeAll(%d) return", value), rE, (int) result);
		
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testClear(IntVector v, int sizeE, int capE, String sE, int[] aE) {
		try {
			v.clear();
		} catch (ExitException ex) {}
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testTrim(IntVector v, int sizeE, int capE, String sE, int[] aE) {
		try {
			v.trimToSize();
		} catch (ExitException ex) {}
		_testContents(v, sizeE, capE, sE, aE);
	}
	
	private void _testGet(IntVector v, int index, Integer rE) {
		Integer result;
		
		result = Integer.MAX_VALUE;
		try {
			result = v.get(index);
		} catch (ExitException ex) {}
		assertEquals(String.format("get(%d) return", index), rE, result);
	}
	
	private void _testSet(IntVector v, int index, int value, Integer rE) {
		Integer result;
		
		result = Integer.MAX_VALUE;
		try {
			result = v.set(index, value);
		} catch (ExitException ex) {}
		assertEquals(String.format("set(%d, %d) return", index, value), rE, result);
	}
	
	public void testConstruct() {
		final int[] EMPTY_ARRAY = new int[] {};
		
		_construct(0, 10, "[]", EMPTY_ARRAY);
		
		_construct(-10, 0, 0, "[]", EMPTY_ARRAY);
		_construct(0, 0, 0, "[]", EMPTY_ARRAY);
		_construct(10, 0, 10, "[]", EMPTY_ARRAY);
		_construct(100, 0, 100, "[]", EMPTY_ARRAY);
		
		_construct(-10, 1, 0, 0, "[]", EMPTY_ARRAY);
		_construct(0, 0, 0, 0, "[]", EMPTY_ARRAY);
		_construct(0, 1, 0, 0, "[]", EMPTY_ARRAY);
		_construct(0, 42, 0, 0, "[]", EMPTY_ARRAY);
		_construct(1, 0, 1, 1, "[0]", new int[] {0});
		_construct(1, 1, 1, 1, "[1]", new int[] {1});
		_construct(2, 718, 2, 2, "[718, 718]", new int[] {718, 718});
		_construct(3, 14, 3, 3, "[14, 14, 14]", new int[] {14, 14, 14});
		
		_construct(null, 0, 0, "[]");
		_construct(EMPTY_ARRAY, 0, 0, "[]");
		_construct(new int[] {0}, 1, 1, "[0]");
		_construct(new int[] {12}, 1, 1, "[12]");
		_construct(new int[] {1, 2}, 2, 2, "[1, 2]");
	}
	
	public void testCopyTo() {
		_testCopyTo(new int[] {1, 2, 3}, new int[] {0, 0, 0}, 0, new int[] {0, 0, 0});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {-1, -2, -3}, 0, new int[] {-1, -2, -3});
		
		_testCopyTo(new int[] {1, 2, 3}, new int[] {0, 0, 0}, 1, new int[] {1, 0, 0});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {0, 0, 0}, 2, new int[] {1, 2, 0});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {0, 0, 0}, 3, new int[] {1, 2, 3});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {-1, -2, -3}, 1, new int[] {1, -2, -3});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {-1, -2, -3}, 2, new int[] {1, 2, -3});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {-1, -2, -3}, 3, new int[] {1, 2, 3});
		
		_testCopyTo(new int[] {1, 2, 3}, new int[] {0, 0}, 0, new int[] {0, 0});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {0, 0}, 1, new int[] {1, 0});
		_testCopyTo(new int[] {1, 2, 3}, new int[] {0, 0}, 2, new int[] {1, 2});
	}
	
	public void testDoubleIfNeeded() {
		_testDoubleIfNeeded(0, 0, 0, 0);
		
		_testDoubleIfNeeded(0, 0, 1, 1);
		_testDoubleIfNeeded(0, 0, 2, 2);
		_testDoubleIfNeeded(0, 0, 3, 4);
		_testDoubleIfNeeded(0, 0, 4, 4);
		_testDoubleIfNeeded(0, 0, 5, 8);
		_testDoubleIfNeeded(0, 0, 6, 8);
		_testDoubleIfNeeded(0, 0, 7, 8);
		_testDoubleIfNeeded(0, 0, 8, 8);
		_testDoubleIfNeeded(0, 0, 9, 16);
		_testDoubleIfNeeded(0, 0, 10, 16);
		_testDoubleIfNeeded(0, 0, 11, 16);
		_testDoubleIfNeeded(0, 0, 12, 16);
		_testDoubleIfNeeded(0, 0, 13, 16);
		_testDoubleIfNeeded(0, 0, 14, 16);
		_testDoubleIfNeeded(0, 0, 15, 16);
		_testDoubleIfNeeded(0, 0, 16, 16);
		_testDoubleIfNeeded(0, 0, 17, 32);
		
		_testDoubleIfNeeded(1, 0, 0, 1);
		_testDoubleIfNeeded(1, 0, 1, 1);
		_testDoubleIfNeeded(1, 1, 0, 1);
		_testDoubleIfNeeded(1, 1, 1, 2);
		_testDoubleIfNeeded(1, 0, 2, 2);
		_testDoubleIfNeeded(1, 1, 2, 4);
		_testDoubleIfNeeded(1, 0, 3, 4);
		_testDoubleIfNeeded(1, 1, 3, 4);
		_testDoubleIfNeeded(1, 0, 4, 4);
		_testDoubleIfNeeded(1, 1, 4, 8);
		_testDoubleIfNeeded(1, 0, 5, 8);
		_testDoubleIfNeeded(1, 1, 5, 8);
		_testDoubleIfNeeded(1, 0, 6, 8);
		_testDoubleIfNeeded(1, 1, 6, 8);
		_testDoubleIfNeeded(1, 0, 7, 8);
		_testDoubleIfNeeded(1, 1, 7, 8);
		_testDoubleIfNeeded(1, 0, 8, 8);
		_testDoubleIfNeeded(1, 1, 8, 16);
		_testDoubleIfNeeded(1, 0, 9, 16);
		_testDoubleIfNeeded(1, 1, 9, 16);
		_testDoubleIfNeeded(1, 0, 10, 16);
		_testDoubleIfNeeded(1, 1, 10, 16);
		_testDoubleIfNeeded(1, 0, 11, 16);
		_testDoubleIfNeeded(1, 1, 11, 16);
		_testDoubleIfNeeded(1, 0, 12, 16);
		_testDoubleIfNeeded(1, 1, 12, 16);
		_testDoubleIfNeeded(1, 0, 13, 16);
		_testDoubleIfNeeded(1, 1, 13, 16);
		_testDoubleIfNeeded(1, 0, 14, 16);
		_testDoubleIfNeeded(1, 1, 14, 16);
		_testDoubleIfNeeded(1, 0, 15, 16);
		_testDoubleIfNeeded(1, 1, 15, 16);
		_testDoubleIfNeeded(1, 0, 16, 16);
		_testDoubleIfNeeded(1, 1, 16, 32);
		_testDoubleIfNeeded(1, 0, 17, 32);
		_testDoubleIfNeeded(1, 1, 17, 32);
		
		_testDoubleIfNeeded(2, 0, 0, 2);
		_testDoubleIfNeeded(2, 0, 1, 2);
		_testDoubleIfNeeded(2, 1, 0, 2);
		_testDoubleIfNeeded(2, 1, 1, 2);
		_testDoubleIfNeeded(2, 2, 0, 2);
		_testDoubleIfNeeded(2, 2, 1, 4);
		_testDoubleIfNeeded(2, 0, 2, 2);
		_testDoubleIfNeeded(2, 1, 2, 4);
		_testDoubleIfNeeded(2, 2, 2, 4);
		_testDoubleIfNeeded(2, 0, 3, 4);
		_testDoubleIfNeeded(2, 1, 3, 4);
		_testDoubleIfNeeded(2, 2, 3, 8);
		_testDoubleIfNeeded(2, 0, 4, 4);
		_testDoubleIfNeeded(2, 1, 4, 8);
		_testDoubleIfNeeded(2, 2, 4, 8);
		_testDoubleIfNeeded(2, 0, 5, 8);
		_testDoubleIfNeeded(2, 1, 5, 8);
		_testDoubleIfNeeded(2, 2, 5, 8);
		_testDoubleIfNeeded(2, 0, 6, 8);
		_testDoubleIfNeeded(2, 1, 6, 8);
		_testDoubleIfNeeded(2, 2, 6, 8);
		_testDoubleIfNeeded(2, 0, 7, 8);
		_testDoubleIfNeeded(2, 1, 7, 8);
		_testDoubleIfNeeded(2, 2, 7, 16);
		_testDoubleIfNeeded(2, 0, 8, 8);
		_testDoubleIfNeeded(2, 1, 8, 16);
		_testDoubleIfNeeded(2, 2, 8, 16);
		_testDoubleIfNeeded(2, 0, 9, 16);
		_testDoubleIfNeeded(2, 1, 9, 16);
		_testDoubleIfNeeded(2, 2, 9, 16);
		_testDoubleIfNeeded(2, 0, 10, 16);
		_testDoubleIfNeeded(2, 1, 10, 16);
		_testDoubleIfNeeded(2, 2, 10, 16);
		_testDoubleIfNeeded(2, 0, 11, 16);
		_testDoubleIfNeeded(2, 1, 11, 16);
		_testDoubleIfNeeded(2, 2, 11, 16);
		_testDoubleIfNeeded(2, 0, 12, 16);
		_testDoubleIfNeeded(2, 1, 12, 16);
		_testDoubleIfNeeded(2, 2, 12, 16);
		_testDoubleIfNeeded(2, 0, 13, 16);
		_testDoubleIfNeeded(2, 1, 13, 16);
		_testDoubleIfNeeded(2, 2, 13, 16);
		_testDoubleIfNeeded(2, 0, 14, 16);
		_testDoubleIfNeeded(2, 1, 14, 16);
		_testDoubleIfNeeded(2, 2, 14, 16);
		_testDoubleIfNeeded(2, 0, 15, 16);
		_testDoubleIfNeeded(2, 1, 15, 16);
		_testDoubleIfNeeded(2, 2, 15, 32);
		_testDoubleIfNeeded(2, 0, 16, 16);
		_testDoubleIfNeeded(2, 1, 16, 32);
		_testDoubleIfNeeded(2, 2, 16, 32);
		_testDoubleIfNeeded(2, 0, 17, 32);
		_testDoubleIfNeeded(2, 1, 17, 32);
		_testDoubleIfNeeded(2, 2, 17, 32);
		
		_testDoubleIfNeeded(3, 0, 0, 3);
		_testDoubleIfNeeded(3, 1, 0, 3);
		_testDoubleIfNeeded(3, 2, 0, 3);
		_testDoubleIfNeeded(3, 3, 0, 3);
		_testDoubleIfNeeded(3, 0, 1, 3);
		_testDoubleIfNeeded(3, 1, 1, 3);
		_testDoubleIfNeeded(3, 2, 1, 3);
		_testDoubleIfNeeded(3, 3, 1, 6);
		_testDoubleIfNeeded(3, 0, 2, 3);
		_testDoubleIfNeeded(3, 1, 2, 3);
		_testDoubleIfNeeded(3, 2, 2, 6);
		_testDoubleIfNeeded(3, 3, 2, 6);
		_testDoubleIfNeeded(3, 0, 3, 3);
		_testDoubleIfNeeded(3, 1, 3, 6);
		_testDoubleIfNeeded(3, 2, 3, 6);
		_testDoubleIfNeeded(3, 3, 3, 6);
		_testDoubleIfNeeded(3, 0, 4, 6);
		_testDoubleIfNeeded(3, 1, 4, 6);
		_testDoubleIfNeeded(3, 2, 4, 6);
		_testDoubleIfNeeded(3, 3, 4, 12);
		_testDoubleIfNeeded(3, 0, 5, 6);
		_testDoubleIfNeeded(3, 1, 5, 6);
		_testDoubleIfNeeded(3, 2, 5, 12);
		_testDoubleIfNeeded(3, 3, 5, 12);
		_testDoubleIfNeeded(3, 0, 6, 6);
		_testDoubleIfNeeded(3, 1, 6, 12);
		_testDoubleIfNeeded(3, 2, 6, 12);
		_testDoubleIfNeeded(3, 3, 6, 12);
		_testDoubleIfNeeded(3, 0, 7, 12);
		_testDoubleIfNeeded(3, 1, 7, 12);
		_testDoubleIfNeeded(3, 2, 7, 12);
		_testDoubleIfNeeded(3, 3, 7, 12);
		_testDoubleIfNeeded(3, 0, 8, 12);
		_testDoubleIfNeeded(3, 1, 8, 12);
		_testDoubleIfNeeded(3, 2, 8, 12);
		_testDoubleIfNeeded(3, 3, 8, 12);
		_testDoubleIfNeeded(3, 0, 9, 12);
		_testDoubleIfNeeded(3, 1, 9, 12);
		_testDoubleIfNeeded(3, 2, 9, 12);
		_testDoubleIfNeeded(3, 3, 9, 12);
		_testDoubleIfNeeded(3, 0, 10, 12);
		_testDoubleIfNeeded(3, 1, 10, 12);
		_testDoubleIfNeeded(3, 2, 10, 12);
		_testDoubleIfNeeded(3, 3, 10, 24);
		_testDoubleIfNeeded(3, 0, 11, 12);
		_testDoubleIfNeeded(3, 1, 11, 12);
		_testDoubleIfNeeded(3, 2, 11, 24);
		_testDoubleIfNeeded(3, 3, 11, 24);
		_testDoubleIfNeeded(3, 0, 12, 12);
		_testDoubleIfNeeded(3, 1, 12, 24);
		_testDoubleIfNeeded(3, 2, 12, 24);
		_testDoubleIfNeeded(3, 3, 12, 24);
		_testDoubleIfNeeded(3, 0, 13, 24);
		_testDoubleIfNeeded(3, 1, 13, 24);
		_testDoubleIfNeeded(3, 2, 13, 24);
		_testDoubleIfNeeded(3, 3, 13, 24);
		
		_testDoubleIfNeeded(8, 6, 100, 128);
		_testDoubleIfNeeded(10, 9, 100, 160);
	}
	
	public void testSet() {
		IntVector v1 = _construct(5, 0, 5, 5, "[0, 0, 0, 0, 0]", new int[] {0, 0, 0, 0, 0});
		_testSet(v1, -1, 1, null, 5, 5, "[0, 0, 0, 0, 0]", new int[] {0, 0, 0, 0, 0});
		_testSet(v1, 0, 1, 1, 5, 5, "[1, 0, 0, 0, 0]", new int[] {1, 0, 0, 0, 0});
		_testSet(v1, 1, 2, 2, 5, 5, "[1, 2, 0, 0, 0]", new int[] {1, 2, 0, 0, 0});
		_testSet(v1, 2, 3, 3, 5, 5, "[1, 2, 3, 0, 0]", new int[] {1, 2, 3, 0, 0});
		_testSet(v1, 3, 4, 4, 5, 5, "[1, 2, 3, 4, 0]", new int[] {1, 2, 3, 4, 0});
		_testSet(v1, 4, 5, 5, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
		_testSet(v1, 5, 6, null, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
		
		v1 = _construct(new int[] {0, 0, 0, 0, 0}, 5, 5, "[0, 0, 0, 0, 0]");
		_testSet(v1, -1, 1, null, 5, 5, "[0, 0, 0, 0, 0]", new int[] {0, 0, 0, 0, 0});
		_testSet(v1, 0, 1, 1, 5, 5, "[1, 0, 0, 0, 0]", new int[] {1, 0, 0, 0, 0});
		_testSet(v1, 1, 2, 2, 5, 5, "[1, 2, 0, 0, 0]", new int[] {1, 2, 0, 0, 0});
		_testSet(v1, 2, 3, 3, 5, 5, "[1, 2, 3, 0, 0]", new int[] {1, 2, 3, 0, 0});
		_testSet(v1, 3, 4, 4, 5, 5, "[1, 2, 3, 4, 0]", new int[] {1, 2, 3, 4, 0});
		_testSet(v1, 4, 5, 5, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
		_testSet(v1, 5, 6, null, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
	}
	
	public void testReverse() {
		IntVector v1 = _construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]");
		_testReverse(v1, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
		_testReverse(v1, 5, 5, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		
		v1 = _construct(new int[] {}, 0, 0, "[]");
		_testReverse(v1, 0, 0, "[]", new int[] {});
		
		v1 = _construct(new int[] {1}, 1, 1, "[1]");
		_testReverse(v1, 1, 1, "[1]", new int[] {1});
		
		v1 = _construct(new int[] {2, 1}, 2, 2, "[2, 1]");
		_testReverse(v1, 2, 2, "[1, 2]", new int[] {1, 2});
	}
	
	public void testEnsure() {
		IntVector v1 = _construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]");
		
		_testEnsure(v1, 1, 5, 5, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		_testEnsure(v1, 2, 5, 5, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		_testEnsure(v1, 3, 5, 5, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		_testEnsure(v1, 4, 5, 5, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		_testEnsure(v1, 5, 5, 5, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		
		_testEnsure(v1, 6, 5, 6, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		_testEnsure(v1, 20, 5, 20, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
	}
	
	public void testAddToEnd() {
		IntVector v1 = _construct(new int[] {}, 0, 0, "[]");
		_testAdd(v1, 9, 9, 1, 1, "[9]", new int[] {9});
		_testAdd(v1, 8, 8, 2, 2, "[9, 8]", new int[] {9, 8});
		_testAdd(v1, 7, 7, 3, 4, "[9, 8, 7]", new int[] {9, 8, 7});
		_testAdd(v1, 1, 1, 4, 4, "[9, 8, 7, 1]", new int[] {9, 8, 7, 1});
		_testAdd(v1, 2, 2, 5, 8, "[9, 8, 7, 1, 2]", new int[] {9, 8, 7, 1, 2});
		_testAddAll(v1, 
			new Integer[] {3, 4, 5, 6, 7, 8, 9, 10, 11}, 14, 16, 
			"[9, 8, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]", 
			new int[] {9, 8, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}
		);
		
		v1 = _construct(new int[] {}, 0, 0, "[]");
		_testAddAll(v1, 
			new Integer[] {9, 8, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}, 14, 16, 
			"[9, 8, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]", 
			new int[] {9, 8, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}
		);
		
		//
		
		v1 = _construct(0, 10, "[]", new int[] {});
		_testAdd(v1, 9, 9, 1, 10, "[9]", new int[] {9});
		_testAdd(v1, 8, 8, 2, 10, "[9, 8]", new int[] {9, 8});
		_testAdd(v1, 7, 7, 3, 10, "[9, 8, 7]", new int[] {9, 8, 7});
		_testAdd(v1, 1, 1, 4, 10, "[9, 8, 7, 1]", new int[] {9, 8, 7, 1});
		_testAdd(v1, 2, 2, 5, 10, "[9, 8, 7, 1, 2]", new int[] {9, 8, 7, 1, 2});
		_testAdd(v1, 2, 2, 6, 10, "[9, 8, 7, 1, 2, 2]", new int[] {9, 8, 7, 1, 2, 2});
		_testAdd(v1, 2, 2, 7, 10, "[9, 8, 7, 1, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2});
		_testAdd(v1, 2, 2, 8, 10, "[9, 8, 7, 1, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2});
		_testAdd(v1, 2, 2, 9, 10, "[9, 8, 7, 1, 2, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2});
		_testAdd(v1, 2, 2, 10, 10, "[9, 8, 7, 1, 2, 2, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2});
		_testAdd(v1, 2, 2, 11, 20, "[9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2});
		_testAddAll(v1, 
			new Integer[] {1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}, 23, 40, 
			"[9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10]", 
			new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}
		);
		
		v1 = _construct(0, 10, "[]", new int[] {});
		_testAddAll(v1, 
			new Integer[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}, 23, 40, 
			"[9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10]", 
			new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}
		);
		
		//
		
		v1 = _construct(6, 0, 6, "[]", new int[] {});
		_testAdd(v1, 9, 9, 1, 6, "[9]", new int[] {9});
		_testAdd(v1, 8, 8, 2, 6, "[9, 8]", new int[] {9, 8});
		_testAdd(v1, 7, 7, 3, 6, "[9, 8, 7]", new int[] {9, 8, 7});
		_testAdd(v1, 1, 1, 4, 6, "[9, 8, 7, 1]", new int[] {9, 8, 7, 1});
		_testAdd(v1, 2, 2, 5, 6, "[9, 8, 7, 1, 2]", new int[] {9, 8, 7, 1, 2});
		_testAdd(v1, 2, 2, 6, 6, "[9, 8, 7, 1, 2, 2]", new int[] {9, 8, 7, 1, 2, 2});
		_testAdd(v1, 2, 2, 7, 12, "[9, 8, 7, 1, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2});
		_testAdd(v1, 2, 2, 8, 12, "[9, 8, 7, 1, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2});
		_testAdd(v1, 2, 2, 9, 12, "[9, 8, 7, 1, 2, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2});
		_testAdd(v1, 2, 2, 10, 12, "[9, 8, 7, 1, 2, 2, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2});
		_testAdd(v1, 2, 2, 11, 12, "[9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2]", new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2});
		_testAddAll(v1, 
			new Integer[] {1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}, 23, 24, 
			"[9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10]", 
			new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}
		);
		
		v1 = _construct(6, 0, 6, "[]", new int[] {});
		_testAddAll(v1, 
			new Integer[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}, 23, 24, 
			"[9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10]", 
			new int[] {9, 8, 7, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10}
		);
		
		//
		
		v1 = _construct(3, 1, 3, 3, "[1, 1, 1]", new int[] {1, 1, 1});
		_testAdd(v1, 9, 9, 4, 6, "[1, 1, 1, 9]", new int[] {1, 1, 1, 9});
		_testAdd(v1, 8, 8, 5, 6, "[1, 1, 1, 9, 8]", new int[] {1, 1, 1, 9, 8});
		_testAdd(v1, 7, 7, 6, 6, "[1, 1, 1, 9, 8, 7]", new int[] {1, 1, 1, 9, 8, 7});
		_testAdd(v1, 1, 1, 7, 12, "[1, 1, 1, 9, 8, 7, 1]", new int[] {1, 1, 1, 9, 8, 7, 1});
		_testAdd(v1, 2, 2, 8, 12, "[1, 1, 1, 9, 8, 7, 1, 2]", new int[] {1, 1, 1, 9, 8, 7, 1, 2});
		_testAddAll(v1, 
			new Integer[] {10, 20, 30, 40, 50, 60, 70, 80, 90, -100}, 18, 24, 
			"[1, 1, 1, 9, 8, 7, 1, 2, 10, 20, 30, 40, 50, 60, 70, 80, 90, -100]", 
			new int[] {1, 1, 1, 9, 8, 7, 1, 2, 10, 20, 30, 40, 50, 60, 70, 80, 90, -100}
		);
		
		v1 = _construct(3, 1, 3, 3, "[1, 1, 1]", new int[] {1, 1, 1});
		_testAddAll(v1, 
			new Integer[] {9, 8, 7, 1, 2, 10, 20, 30, 40, 50, 60, 70, 80, 90, -100}, 18, 24, 
			"[1, 1, 1, 9, 8, 7, 1, 2, 10, 20, 30, 40, 50, 60, 70, 80, 90, -100]", 
			new int[] {1, 1, 1, 9, 8, 7, 1, 2, 10, 20, 30, 40, 50, 60, 70, 80, 90, -100}
		);
	}
	
	public void testAddAt() {
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			-1, 1000, null, 5, 5, 
			"[1, 2, 3, 4, 5]", 
			new int[] {1, 2, 3, 4, 5}
		);
		
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			0, 100, 100, 6, 10, 
			"[100, 1, 2, 3, 4, 5]", 
			new int[] {100, 1, 2, 3, 4, 5}
		);
		
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			1, -300, -300, 6, 10, 
			"[1, -300, 2, 3, 4, 5]", 
			new int[] {1, -300, 2, 3, 4, 5}
		);
		
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			2, 20, 20, 6, 10, 
			"[1, 2, 20, 3, 4, 5]", 
			new int[] {1, 2, 20, 3, 4, 5}
		);
		
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			3, -200, -200, 6, 10, 
			"[1, 2, 3, -200, 4, 5]", 
			new int[] {1, 2, 3, -200, 4, 5}
		);
		
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			4, 42, 42, 6, 10, 
			"[1, 2, 3, 4, 42, 5]", 
			new int[] {1, 2, 3, 4, 42, 5}
		);
		
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			5, -1, -1, 6, 10, 
			"[1, 2, 3, 4, 5, -1]", 
			new int[] {1, 2, 3, 4, 5, -1}
		);
		
		_testAdd(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"), 
			6, -2, null, 5, 5, 
			"[1, 2, 3, 4, 5]", 
			new int[] {1, 2, 3, 4, 5}
		);
		
		//
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			-1, new Integer[] {10, 20, 30}, 5, 5, 
			"[5, 4, 3, 2, 1]", 
			new int[] {5, 4, 3, 2, 1}
		);
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			0, new Integer[] {100, 200, 300}, 8, 10, 
			"[100, 200, 300, 5, 4, 3, 2, 1]", 
			new int[] {100, 200, 300, 5, 4, 3, 2, 1}
		);
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			1, new Integer[] {-3, -2, -1, 0, 0, 1000}, 11, 20, 
			"[5, -3, -2, -1, 0, 0, 1000, 4, 3, 2, 1]", 
			new int[] {5, -3, -2, -1, 0, 0, 1000, 4, 3, 2, 1}
		);
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			2, new Integer[] {5, 5, 5}, 8, 10, 
			"[5, 4, 5, 5, 5, 3, 2, 1]", 
			new int[] {5, 4, 5, 5, 5, 3, 2, 1}
		);
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			3, new Integer[] {0, 0}, 7, 10, 
			"[5, 4, 3, 0, 0, 2, 1]", 
			new int[] {5, 4, 3, 0, 0, 2, 1}
		);
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			4, new Integer[] {55512}, 6, 10, 
			"[5, 4, 3, 2, 55512, 1]", 
			new int[] {5, 4, 3, 2, 55512, 1}
		);
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			5, new Integer[] {8675, 309}, 7, 10, 
			"[5, 4, 3, 2, 1, 8675, 309]", 
			new int[] {5, 4, 3, 2, 1, 8675, 309}
		);
		
		_testAddAll(
			_construct(new int[] {5, 4, 3, 2, 1}, 5, 5, "[5, 4, 3, 2, 1]"), 
			6, new Integer[] {1, 2, 3, 4, 5}, 5, 5, 
			"[5, 4, 3, 2, 1]", 
			new int[] {5, 4, 3, 2, 1}
		);
	}
	
	public void testRemove() {
		_testRemoveLast(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			5, 4, 5, "[1, 2, 3, 4]", new int[] {1, 2, 3, 4}
		);
		
		_testRemoveLast(
			_construct(new int[] {1, 2, 3, 4}, 4, 4, "[1, 2, 3, 4]"),
			4, 3, 4, "[1, 2, 3]", new int[] {1, 2, 3}
		);
		
		_testRemoveLast(
			_construct(new int[] {1, 2, 3}, 3, 3, "[1, 2, 3]"),
			3, 2, 3, "[1, 2]", new int[] {1, 2}
		);
		
		_testRemoveLast(
			_construct(new int[] {1, 2}, 2, 2, "[1, 2]"),
			2, 1, 2, "[1]", new int[] {1}
		);
		
		_testRemoveLast(
			_construct(new int[] {1}, 1, 1, "[1]"),
			1, 0, 1, "[]", new int[] {}
		);
		
		_testRemoveLast(
			_construct(new int[] {}, 0, 0, "[]"),
			null, 0, 0, "[]", new int[] {}
		);
		
		_testRemoveLast(
			_construct(0, 10, "[]", new int[] {}),
			null, 0, 10, "[]", new int[] {}
		);
		
		_testRemoveLast(
			_construct(5, 0, 5, "[]", new int[] {}),
			null, 0, 5, "[]", new int[] {}
		);
		
		//
		
		IntVector v1 = _construct(5, -1, 5, 5, "[-1, -1, -1, -1, -1]", new int[] {-1, -1, -1, -1, -1});
		_testRemoveLast(v1, -1, 4, 5, "[-1, -1, -1, -1]", new int[] {-1, -1, -1, -1});
		_testRemoveLast(v1, -1, 3, 5, "[-1, -1, -1]", new int[] {-1, -1, -1});
		_testRemoveLast(v1, -1, 2, 5, "[-1, -1]", new int[] {-1, -1});
		_testRemoveLast(v1, -1, 1, 5, "[-1]", new int[] {-1});
		_testRemoveLast(v1, -1, 0, 5, "[]", new int[] {});
		_testRemoveLast(v1, null, 0, 5, "[]", new int[] {});
		
		//
		
		_testRemove(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			-1, null, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5}
		);
		
		_testRemove(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			0, 1, 4, 5, "[2, 3, 4, 5]", new int[] {2, 3, 4, 5}
		);
		
		_testRemove(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			1, 2, 4, 5, "[1, 3, 4, 5]", new int[] {1, 3, 4, 5}
		);
		
		_testRemove(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			2, 3, 4, 5, "[1, 2, 4, 5]", new int[] {1, 2, 4, 5}
		);
		
		_testRemove(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			3, 4, 4, 5, "[1, 2, 3, 5]", new int[] {1, 2, 3, 5}
		);
		
		_testRemove(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			4, 5, 4, 5, "[1, 2, 3, 4]", new int[] {1, 2, 3, 4}
		);
		
		_testRemove(
			_construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]"),
			5, null, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5}
		);
		
		v1 = _construct(new int[] {5, 4, 3, 2, 1, 0}, 6, 6, "[5, 4, 3, 2, 1, 0]");
		_testRemove(v1, 5, 0, 5, 6, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		_testRemove(v1, 0, 5, 4, 6, "[4, 3, 2, 1]", new int[] {4, 3, 2, 1});
		_testRemove(v1, 2, 2, 3, 6, "[4, 3, 1]", new int[] {4, 3, 1});
		_testRemove(v1, 5, null, 3, 6, "[4, 3, 1]", new int[] {4, 3, 1});
		_testRemove(v1, 1, 3, 2, 6, "[4, 1]", new int[] {4, 1});
		_testRemove(v1, 0, 4, 1, 6, "[1]", new int[] {1});
		_testRemove(v1, 0, 1, 0, 6, "[]", new int[] {});
		
		//
		
		_testRemoveFirst(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			0, null, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}
		);
		
		_testRemoveFirst(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			1, 1, 8, 9, "[2, 3, 4, 5, 4, 3, 2, 1]", new int[] {2, 3, 4, 5, 4, 3, 2, 1}
		);
		
		_testRemoveFirst(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			2, 2, 8, 9, "[1, 3, 4, 5, 4, 3, 2, 1]", new int[] {1, 3, 4, 5, 4, 3, 2, 1}
		);
		
		_testRemoveFirst(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			3, 3, 8, 9, "[1, 2, 4, 5, 4, 3, 2, 1]", new int[] {1, 2, 4, 5, 4, 3, 2, 1}
		);
		
		_testRemoveFirst(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			4, 4, 8, 9, "[1, 2, 3, 5, 4, 3, 2, 1]", new int[] {1, 2, 3, 5, 4, 3, 2, 1}
		);
		
		_testRemoveFirst(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			5, 5, 8, 9, "[1, 2, 3, 4, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 4, 3, 2, 1}
		);
		
		_testRemoveFirst(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			6, null, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}
		);
		
		v1 = _construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]");
		_testRemoveFirst(v1, 0, null, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1});
		_testRemoveFirst(v1, 1, 1, 8, 9, "[2, 3, 4, 5, 4, 3, 2, 1]", new int[] {2, 3, 4, 5, 4, 3, 2, 1});
		_testRemoveFirst(v1, 1, 1, 7, 9, "[2, 3, 4, 5, 4, 3, 2]", new int[] {2, 3, 4, 5, 4, 3, 2});
		_testRemoveFirst(v1, 1, null, 7, 9, "[2, 3, 4, 5, 4, 3, 2]", new int[] {2, 3, 4, 5, 4, 3, 2});
		_testRemoveFirst(v1, 5, 5, 6, 9, "[2, 3, 4, 4, 3, 2]", new int[] {2, 3, 4, 4, 3, 2});
		_testRemoveFirst(v1, 5, null, 6, 9, "[2, 3, 4, 4, 3, 2]", new int[] {2, 3, 4, 4, 3, 2});
		_testRemoveFirst(v1, 4, 4, 5, 9, "[2, 3, 4, 3, 2]", new int[] {2, 3, 4, 3, 2});
		_testRemoveFirst(v1, 4, 4, 4, 9, "[2, 3, 3, 2]", new int[] {2, 3, 3, 2});
		_testRemoveFirst(v1, 4, null, 4, 9, "[2, 3, 3, 2]", new int[] {2, 3, 3, 2});
		_testRemoveFirst(v1, 2, 2, 3, 9, "[3, 3, 2]", new int[] {3, 3, 2});
		_testRemoveFirst(v1, 3, 3, 2, 9, "[3, 2]", new int[] {3, 2});
		_testRemoveFirst(v1, 3, 3, 1, 9, "[2]", new int[] {2});
		_testRemoveFirst(v1, 3, null, 1, 9, "[2]", new int[] {2});
		_testRemoveFirst(v1, 2, 2, 0, 9, "[]", new int[] {});
		_testRemoveFirst(v1, 2, null, 0, 9, "[]", new int[] {});
		
		//
		
		_testRemoveAll(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			0, 0, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}
		);
		
		_testRemoveAll(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			1, 2, 7, 9, "[2, 3, 4, 5, 4, 3, 2]", new int[] {2, 3, 4, 5, 4, 3, 2}
		);
		
		_testRemoveAll(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			2, 2, 7, 9, "[1, 3, 4, 5, 4, 3, 1]", new int[] {1, 3, 4, 5, 4, 3, 1}
		);
		
		_testRemoveAll(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			3, 2, 7, 9, "[1, 2, 4, 5, 4, 2, 1]", new int[] {1, 2, 4, 5, 4, 2, 1}
		);
		
		_testRemoveAll(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			4, 2, 7, 9, "[1, 2, 3, 5, 3, 2, 1]", new int[] {1, 2, 3, 5, 3, 2, 1}
		);
		
		_testRemoveAll(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			5, 1, 8, 9, "[1, 2, 3, 4, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 4, 3, 2, 1}
		);
		
		
		_testRemoveAll(
			_construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]"),
			6, 0, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}
		);
		
		v1 = _construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]");
		_testRemoveAll(v1, 0, 0, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1});
		_testRemoveAll(v1, 5, 1, 8, 9, "[1, 2, 3, 4, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 4, 3, 2, 1});
		_testRemoveAll(v1, 5, 0, 8, 9, "[1, 2, 3, 4, 4, 3, 2, 1]", new int[] {1, 2, 3, 4, 4, 3, 2, 1});
		_testRemoveAll(v1, 1, 2, 6, 9, "[2, 3, 4, 4, 3, 2]", new int[] {2, 3, 4, 4, 3, 2});
		_testRemoveAll(v1, 1, 0, 6, 9, "[2, 3, 4, 4, 3, 2]", new int[] {2, 3, 4, 4, 3, 2});
		_testRemoveAll(v1, 4, 2, 4, 9, "[2, 3, 3, 2]", new int[] {2, 3, 3, 2});
		_testRemoveAll(v1, 4, 0, 4, 9, "[2, 3, 3, 2]", new int[] {2, 3, 3, 2});
		_testRemoveAll(v1, 2, 2, 2, 9, "[3, 3]", new int[] {3, 3});
		_testRemoveAll(v1, 2, 0, 2, 9, "[3, 3]", new int[] {3, 3});
		_testRemoveAll(v1, 3, 2, 0, 9, "[]", new int[] {});
		_testRemoveAll(v1, 3, 0, 0, 9, "[]", new int[] {});
	}

	public void testClearTrim() {
		IntVector v1;
		
		//
		
		v1 = _construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]");
		_testClear(v1, 0, 9, "[]", new int[] {});
		_testTrim(v1, 0, 0, "[]", new int[] {});
		
		//
		
		v1 = _construct(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1}, 9, 9, "[1, 2, 3, 4, 5, 4, 3, 2, 1]");
		_testRemoveAll(v1, 4, 2, 7, 9, "[1, 2, 3, 5, 3, 2, 1]", new int[] {1, 2, 3, 5, 3, 2, 1});
		_testTrim(v1, 7, 7, "[1, 2, 3, 5, 3, 2, 1]", new int[] {1, 2, 3, 5, 3, 2, 1});
		_testClear(v1, 0, 7, "[]", new int[] {});
		_testTrim(v1, 0, 0, "[]", new int[] {});
	}
	
	public void testIntegration() {
		IntVector a;
		
		a = _construct(0, 0, 0, "[]", new int[] {});
		_testAdd(a, 1, 1, 1, 1, "[1]", new int[] {1});
		_testAdd(a, 2, 2, 2, 2, "[1, 2]", new int[] {1, 2});
		_testAdd(a, 3, 3, 3, 4, "[1, 2, 3]", new int[] {1, 2, 3});
		_testAdd(a, 4, 4, 4, 4, "[1, 2, 3, 4]", new int[] {1, 2, 3, 4});
		_testAdd(a, 5, 5, 5, 8, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
		_testTrim(a, 5, 5, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
		_testReverse(a, 5, 5, "[5, 4, 3, 2, 1]", new int[] {5, 4, 3, 2, 1});
		_testAdd(a, 0, 0, 6, 10, "[5, 4, 3, 2, 1, 0]", new int[] {5, 4, 3, 2, 1, 0});
		_testReverse(a, 6, 10, "[0, 1, 2, 3, 4, 5]", new int[] {0, 1, 2, 3, 4, 5});
		_testRemoveLast(a, 5, 5, 10, "[0, 1, 2, 3, 4]", new int[] {0, 1, 2, 3, 4});
		_testRemoveLast(a, 4, 4, 10, "[0, 1, 2, 3]", new int[] {0, 1, 2, 3});
		_testRemoveLast(a, 3, 3, 10, "[0, 1, 2]", new int[] {0, 1, 2});
		_testTrim(a, 3, 3, "[0, 1, 2]", new int[] {0, 1, 2});
		
		_testGet(a, -1, null);
		_testSet(a, -1, -1, null);
		_testGet(a, -1, null);
		
		_testGet(a, 0, 0);
		_testSet(a, 0, 100, 100);
		_testGet(a, 0, 100);
		
		_testGet(a, 1, 1);
		_testSet(a, 1, 200, 200);
		_testGet(a, 1, 200);
		
		_testGet(a, 2, 2);
		_testSet(a, 2, 300, 300);
		_testGet(a, 2, 300);
		
		_testGet(a, 3, null);
		_testSet(a, 3, 400, null);
		_testGet(a, 3, null);
		
		_testRemove(a, -1, null, 3, 3, "[100, 200, 300]", new int[] {100, 200, 300});
		_testRemove(a, 10, null, 3, 3, "[100, 200, 300]", new int[] {100, 200, 300});
		_testRemove(a, 0, 100, 2, 3, "[200, 300]", new int[] {200, 300});
		_testRemove(a, 1, 300, 1, 3, "[200]", new int[] {200});
		_testRemove(a, 0, 200, 0, 3, "[]", new int[] {});
		_testRemove(a, 0, null, 0, 3, "[]", new int[] {});
		
		_testAdd(a, 1, 1, 1, 3, "[1]", new int[] {1});
		_testAdd(a, 2, 2, 2, 3, "[1, 2]", new int[] {1, 2});
		_testAdd(a, 3, 3, 3, 3, "[1, 2, 3]", new int[] {1, 2, 3});
		_testAdd(a, 4, 4, 4, 6, "[1, 2, 3, 4]", new int[] {1, 2, 3, 4});
		_testAdd(a, 5, 5, 5, 6, "[1, 2, 3, 4, 5]", new int[] {1, 2, 3, 4, 5});
		
		_testRemove(a, 0, 1, 4, 6, "[2, 3, 4, 5]", new int[] {2, 3, 4, 5});
		_testRemove(a, 0, 2, 3, 6, "[3, 4, 5]", new int[] {3, 4, 5});
		_testRemove(a, 0, 3, 2, 6, "[4, 5]", new int[] {4, 5});
		
		_testRemoveFirst(a, 0, null, 2, 6, "[4, 5]", new int[] {4, 5});
		_testRemoveFirst(a, 1, null, 2, 6, "[4, 5]", new int[] {4, 5});
		_testRemoveFirst(a, 2, null, 2, 6, "[4, 5]", new int[] {4, 5});
		_testRemoveFirst(a, 3, null, 2, 6, "[4, 5]", new int[] {4, 5});
		_testRemoveFirst(a, 4, 4, 1, 6, "[5]", new int[] {5});
		_testRemoveFirst(a, 5, 5, 0, 6, "[]", new int[] {});
		_testRemoveFirst(a, 5, null, 0, 6, "[]", new int[] {});
		
		_testAdd(a, 0, 0, 1, 6, "[0]", new int[] {0});
		_testAdd(a, 1, 1, 2, 6, "[0, 1]", new int[] {0, 1});
		_testAdd(a, 1, 1, 3, 6, "[0, 1, 1]", new int[] {0, 1, 1});
		_testAdd(a, 1, 1, 4, 6, "[0, 1, 1, 1]", new int[] {0, 1, 1, 1});
		_testAdd(a, 1, 1, 5, 6, "[0, 1, 1, 1, 1]", new int[] {0, 1, 1, 1, 1});
		_testAdd(a, 0, 0, 6, 6, "[0, 1, 1, 1, 1, 0]", new int[] {0, 1, 1, 1, 1, 0});
		
		_testRemoveAll(a, 1, 4, 2, 6, "[0, 0]", new int[] {0, 0});
		_testRemoveAll(a, 1, 0, 2, 6, "[0, 0]", new int[] {0, 0});
		_testRemoveAll(a, 0, 2, 0, 6, "[]", new int[] {});
		
		_testTrim(a, 0, 0, "[]", new int[] {});
		a = _construct(new int[] {}, 0, 0, "[]");
		
		a = _construct(new int[] {1, 2, 3, 4, 5}, 5, 5, "[1, 2, 3, 4, 5]");
		_testAdd(a, 6, 6, 6, 10, "[1, 2, 3, 4, 5, 6]", new int[] {1, 2, 3, 4, 5, 6});
		_testRemoveAll(a, 2, 1, 5, 10, "[1, 3, 4, 5, 6]", new int[] {1, 3, 4, 5, 6});
		_testRemoveAll(a, 4, 1, 4, 10, "[1, 3, 5, 6]", new int[] {1, 3, 5, 6});
		_testRemoveAll(a, 6, 1, 3, 10, "[1, 3, 5]", new int[] {1, 3, 5});

		_testAddAll(a, new Integer[] {10, 20, 30, 40}, 7, 10, "[1, 3, 5, 10, 20, 30, 40]", new int[] {1, 3, 5, 10, 20, 30, 40});
		_testAdd(a, 3, 7, 7, 8, 10, "[1, 3, 5, 7, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 10, 20, 30, 40});
		_testAdd(a, 4, 9, 9, 9, 10, "[1, 3, 5, 7, 9, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 10, 20, 30, 40});
		_testAdd(a, 5, 11, 11, 10, 10, "[1, 3, 5, 7, 9, 11, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 11, 10, 20, 30, 40});
		_testAdd(a, 6, 13, 13, 11, 20, "[1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40});
		
		_testEnsure(a, 10, 11, 20, "[1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40});
		_testEnsure(a, 15, 11, 20, "[1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40});
		_testEnsure(a, 20, 11, 20, "[1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40});
		_testEnsure(a, 25, 11, 25, "[1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40});
		
		_testTrim(a, 11, 11, "[1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40]", new int[] {1, 3, 5, 7, 9, 11, 13, 10, 20, 30, 40});
	}
}