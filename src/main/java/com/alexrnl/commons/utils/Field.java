package com.alexrnl.commons.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which allows to tag a method as representing a 'field'.<br />
 * This indicates that the method return a property which can be used to compare objects of the
 * same type together.<br />
 * This annotation is typically used on the getters of a class. The method it annotates should not
 * have any parameters.
 * The property {@link #useForHashCode()} indicates that the field should be used (or not) to
 * compute its {@link Object#hashCode() hash code}.<br />
 * @author Alex
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
	/**
	 * <code>true</code> if the return value of the method should be used for hash code computation.
	 */
	boolean useForHashCode() default true;
}
