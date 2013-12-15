package com.alexrnl.commons.arguments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark an attribute as a parameter.
 * @author Alex
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
	/**
	 * The name(s) of the parameter.<br />
	 * More than one name can be specified for an easier use.
	 */
	String[] names() default {};
	
	/**
	 * The description of the field.<br />
	 * This will be display if the help is requested.
	 */
	String description();
	
	/**
	 * <code>true</code> if the parameter is required.
	 */
	boolean required () default false;
	
}
