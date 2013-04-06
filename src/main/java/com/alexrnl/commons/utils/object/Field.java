package com.alexrnl.commons.utils.object;

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
 * @author Alex
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
}
