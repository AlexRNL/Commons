package com.alexrnl.commons.translation;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.utils.Configuration;
import com.alexrnl.commons.utils.StringUtils;

/**
 * Implementation of the translator.<br />
 * Class which provide utilities method to translate key to the locale set in the configuration
 * file.<br />
 * It features:
 * <ul>
 * <li>Translation inclusion: to avoid duplicate translation (i.e. for currency euro/dollar/yen) a
 * translation may include a reference to another key by prefixing it with the
 * {@link #INCLUDE_PREFIX character '§'}. Example:
 * 
 * <pre>
 * commons.currency		<=>		euro
 * commons.enterPrice	<=> 	Enter price in §commons.currency
 * </pre>
 * 
 * will be resolve as <em>Enter price in euro</em> when requesting the translation of key
 * <em>commons.enterPrice</em>.</li>
 * <li>Translation parameters: you may pass parameters to the translator which will be included in
 * the final translation. Reference the parameter location in the locale by using the
 * {@link #PARAMETER_PREFIX character %} and the digit referencing the parameter. Example:
 * 
 * <pre>
 * commons.priceTeeShirt	<=>		The price for the Tee-Shirt is of %0 euros.
 * </pre>
 * 
 * will be resolved as <em>The price for the Tee-Shirt is of 8 euros</em> when calling
 * {@link #get(String, Object...) get("commons.priceTeeShirt", 8)}.</li>
 * </ul>
 * @author Alex
 */
public class Translator extends Configuration {
	/** Logger */
	private static final Logger		LG					= Logger.getLogger(Translator.class.getName());
	
	/** Character which is used to separate hierarchy levels of translations */
	public static final Character	HIERARCHY_SEPARATOR	= '.';
	/** Character used for mnemonics in menu bar */
	public static final Character	MNEMONIC_MARK		= '#';
	/** Character used for parameters in translations */
	public static final Character	PARAMETER_PREFIX	= '%';
	/** Character used to include a translation into an other one */
	public static final Character	INCLUDE_PREFIX		= '§';
	
	/** Set with the missing translations keys (avoid displaying warning multiple times) */
	private final Set<String>		missingKeys;
	
	/**
	 * Constructor #1.<br />
	 * @param translationFile
	 *        the translation file to load.
	 */
	public Translator (final Path translationFile) {
		super(translationFile, true);
		this.missingKeys = new HashSet<>();
		
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Language locale file " + translationFile + " successfully loaded (" + size() + " keys loaded)");
		}
	}
	
	/**
	 * Get the translation of a specific key.
	 * @param key
	 *        the key to translate.
	 * @return the translation of the key from the specified locale file.
	 */
	@Override
	public String get (final String key) {
		if (!has(key)) {
			if (missingKeys.add(key)) {
				LG.warning("Cannot find translation for key " + key);
			}
			return key;
		}
		
		String translation = super.get(key);
		
		// Replace other translations included in the current one
		while (translation.contains(INCLUDE_PREFIX.toString())) {
			// Isolate the string to replace, from the Constants.INCLUDE_PREFIX to the next space
			final int location = translation.indexOf(INCLUDE_PREFIX) + 1;
			final int endLocation = translation.substring(location).indexOf(StringUtils.SPACE) + location;
			String strToReplace;
			if (endLocation < location) {
				// End of string case
				strToReplace = translation.substring(location);
			} else {
				strToReplace = translation.substring(location, endLocation);
			}
			final String backUpStr = strToReplace;
			
			// If the substring has some punctuation marks at the end, it may be shorter than
			// the current one (which stops before the space)
			while (!strToReplace.isEmpty() && !has(strToReplace)) {
				strToReplace = strToReplace.substring(0, strToReplace.length() - 1);
			}
			// In case it has not been found at all
			if (strToReplace.isEmpty()) {
				strToReplace = backUpStr;
				LG.warning("Could not found any suitable translation for the include key: " + strToReplace);
			}
			
			// Replace the prefix + key with the translation of the key (hence the substring)
			translation = translation.substring(0, location - 1) + get(strToReplace) + translation.substring(location + strToReplace.length());
		}
		
		return translation;
	}
	
	/**
	 * Get the translation of a specific key.
	 * @param key
	 *        the key to translate.
	 * @param parameters
	 *        the parameters to use to build the translation.
	 * @return the translation of the key from the specified locale file.
	 */
	public String get (final String key, final Object... parameters) {
		return get(key, Arrays.asList(parameters));
	}

	
	/**
	 * Get the translation of a specific key.
	 * @param key
	 *        the key to translate.
	 * @param parameters
	 *        the parameters to use to build the translation.
	 * @return the translation of the key from the specified locale file.
	 */
	public String get (final String key, final Collection<Object> parameters) {
		String translation = get(key);
		// Case with no translation found or no translator
		if (key.equals(translation) || parameters == null || parameters.isEmpty()) {
			return translation;
		}
		
		// Replace parameters
		int indexParameter = 0;
		for (final Object parameter : parameters) {
			final String strToReplace = PARAMETER_PREFIX.toString() + indexParameter++;
			if (!translation.contains(strToReplace)) {
				LG.warning("Parameter '" + parameter + "' cannot be put into the translation, '" +
						strToReplace + "' was not found.");
				continue;
			}
			translation = translation.replace(strToReplace, String.valueOf(parameter));
		}
		
		return translation;
	}
	
	/**
	 * Short-hand for {@link #get(String, Object...)}, translate the provide object.
	 * @param translatable
	 *        the {@link ParametrableTranslation} object to translate
	 * @return the translation of the object.
	 * @see #get(String, Object...)
	 */
	public String get (final ParametrableTranslation translatable) {
		return get(translatable.getTranslationKey(), translatable.getParameters());
	}
	
	/**
	 * Short-hand for {@link #get(String, Object...)}, translate the provide object.
	 * @param translatable
	 *        the {@link Translatable} object to translate
	 * @return the translation of the object.
	 * @see #get(String, Object...)
	 */
	public String get (final Translatable translatable) {
		return get(translatable.getTranslationKey());
	}
}