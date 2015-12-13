package com.alexrnl.commons.arguments;

import java.util.Collection;

/**
 * Implementation of {@link ParameterValueSetter} interface that handles cases where the item
 * class is undefined for a {@link Collection} parameter.
 */
public class UndefinedItemClass implements ParameterValueSetter {
	/** The parameter for which the item class was not defined. */
	private final Parameter	parameter;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter for which the item class was not defined.
	 */
	public UndefinedItemClass (final Parameter parameter) {
		this.parameter = parameter;
	}
	
	@Override
	public void setValue (final ParsingResults results, final ParsingParameters parameters) {
		results.addError("No item class defined for parameter " + parameter.getNames());
	}
}