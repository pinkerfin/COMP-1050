package edu.wit.cs.comp1050;

import java.util.function.Predicate;

/**
 * Functional interface for
 * testing a student
 * 
 * DO NOT CHANGE
 * 
 * @author Derbinsky
 */
@FunctionalInterface
public interface StudentFilter extends Predicate<Student> {
}
