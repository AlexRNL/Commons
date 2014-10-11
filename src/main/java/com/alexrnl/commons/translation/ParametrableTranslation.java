package com.alexrnl.commons.translation;

/**
 * Interface for configurable translations keys.<br />
 * Classes defining translations for a given dialog, title, label can be parameterize easily using
 * this interface.
 * @author Alex
 */
public interface ParametrableTranslation extends Translatable {
	
	/**
	 * Return the parameters to use when building the translation of the key.
	 * @return the parameters to use.
	 */
	Object[] getParameters ();
}
