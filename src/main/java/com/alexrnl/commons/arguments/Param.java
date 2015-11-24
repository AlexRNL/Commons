package com.alexrnl.commons.arguments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import com.alexrnl.commons.arguments.validators.ParameterValidator;

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
	String[] names();
	
	/**
	 * The description of the field.<br />
	 * This will be display if the help is requested.
	 */
	String description();
	
	/**
	 * <code>true</code> if the parameter is required.
	 */
	boolean required() default false;
	
	/**
	 * This field is used to order parameters (e.g. in usage).<br />
	 * If blank, the order is determined using the required attribute, and finally the first name of
	 * the parameter.
	 */
	int order() default 0;
	
	/**
	 * The validator of the parameter value.<br />
	 * Use this to restrict parameter to certain values (e.g. positive numbers, non-empty strings, etc.).
	 */
	Class<? extends ParameterValidator> validator() default ParameterValidator.class;
	
	/**
	 * This field is used to annotate a {@link Collection} parameter which contains the specified class.<br />
	 * The regular parser for this type will then be used to fill the {@link Collection}.
	 */
	Class<?> itemClass() default Object.class ;
}
