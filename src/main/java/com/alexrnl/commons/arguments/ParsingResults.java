package com.alexrnl.commons.arguments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class which contains the result of the parsing.
 * @author Alex
 */
public class ParsingResults {
	/** Flag indicating if the help was requested */
	private boolean					helpRequested;
	/** The required parameters */
	private final Set<Parameter>	requiredParameters;
	/** The list of errors */
	private final List<String>		errors;
	
	/**
	 * Constructor #1.<br />
	 * @param requiredParameters
	 *        the required parameter for the parsing.
	 */
	public ParsingResults (final Set<Parameter> requiredParameters) {
		super();
		this.requiredParameters = new HashSet<>(requiredParameters);
		this.errors = new ArrayList<>();
	}
	
	/**
	 * Return the attribute helpRequested.
	 * @return the attribute helpRequested.
	 */
	public boolean isHelpRequested () {
		return helpRequested;
	}
	
	/**
	 * Set the attribute helpRequested.
	 * @param helpRequested
	 *        the attribute helpRequested.
	 */
	public void setHelpRequested (final boolean helpRequested) {
		this.helpRequested = helpRequested;
	}
	
	/**
	 * Remove a required parameter.
	 * @param requiredParameter
	 *        the required parameter to remove.
	 */
	public void removeRequiredParameter (final Parameter requiredParameter) {
		requiredParameters.remove(requiredParameter);
	}
	
	/**
	 * Return an unmodifiable view of the required parameters.
	 * @return the required parameters.
	 */
	public Set<Parameter> getRequiredParameters () {
		return Collections.unmodifiableSet(requiredParameters);
	}
	
	/**
	 * Add an error to the current parsing.
	 * @param error
	 *        the error to add.
	 */
	public void addError (final String error) {
		if (error == null || error.isEmpty()) {
			throw new IllegalArgumentException("Cannot add null or empty error");
		}
		errors.add(error);
	}
	
	/**
	 * Return an unmodifiable view of the errors.
	 * @return the errors.
	 */
	public List<String> getErrors () {
		return Collections.unmodifiableList(errors);
	}
	
}