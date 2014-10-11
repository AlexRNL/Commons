package com.alexrnl.commons.translation;

/**
 * Interface to be implemented by translatable classes.
 * @author Alex
 */
public interface Translatable {
	
	/**
	 * The translation key of the element.
	 * @return the translation key.
	 */
	String getTranslationKey ();
}
