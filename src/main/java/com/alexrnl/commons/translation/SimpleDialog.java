package com.alexrnl.commons.translation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Simple implementation of the {@link Dialog} interface.<br />
 * This class also implements the {@link ParametrableTranslation} interface which returns an empty
 * parameter array by default (i.e. no parameter are accepted by this dialog); specify the parameter
 * at instantiation if necessary.<br />
 * This class is immutable.
 * @author Alex
 */
public class SimpleDialog implements Dialog {
	/** The title of the prompt */
	private final String				title;
	/** The message of the prompt */
	private final String				message;
	/** The parameters of the prompt */
	private final Collection<Object>	parameters;

	/**
	 * Constructor #1.<br />
	 * @param title
	 *        the title of the prompt.
	 * @param message
	 *        the message of the prompt.
	 * @param parameters
	 *        the parameters of the prompt.
	 */
	public SimpleDialog (final String title, final String message, final Collection<Object> parameters) {
		super();
		this.title = title;
		this.message = message;
		this.parameters = Collections.unmodifiableCollection(parameters);
	}
	
	/**
	 * Constructor #2.<br />
	 * @param title
	 *        the title of the prompt.
	 * @param message
	 *        the message of the prompt.
	 * @param parameters
	 *        the parameters of the prompt.
	 */
	public SimpleDialog (final String title, final String message, final Object... parameters) {
		this(title, message, Arrays.asList(parameters));
	}
	
	/**
	 * Constructor #3.<br />
	 * @param title
	 *        the title of the prompt.
	 * @param message
	 *        the message of the prompt.
	 */
	public SimpleDialog (final String title, final String message) {
		this(title, message, Collections.emptyList());
	}

	@Override
	public String title () {
		return title;
	}
	
	@Override
	public String message () {
		return message;
	}
	
	@Override
	public Collection<Object> getParameters () {
		return parameters;
	}
	
	@Override
	public String getTranslationKey () {
		return message();
	}
	
}
