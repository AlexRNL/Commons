package com.alexrnl.commons.arguments;

import java.util.Collection;
import java.util.logging.Logger;

import com.alexrnl.commons.arguments.parsers.AbstractParser;
import com.alexrnl.commons.arguments.validators.ParameterValidator;
import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Implementation of {@link ParameterValueSetter} for Collections type fields.
 * @author Alex
 * @param <T>
 *        the type of the item in the collection.
 */
public class CollectionFieldSetter<T> implements ParameterValueSetter {
	/** Logger */
	private static final Logger		LG	= Logger.getLogger(CollectionFieldSetter.class.getName());
			
	/** The parameter whose value to set */
	private final Parameter			parameter;
	/** The parser for the parameter */
	private final AbstractParser<T>	parser;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter whose value to set.
	 * @param parser
	 *        the parser for the parameter.
	 */
	public CollectionFieldSetter (final Parameter parameter, final AbstractParser<T> parser) {
		super();
		this.parameter = parameter;
		this.parser = parser;
	}
	
	@Override
	public void setValue (final ParsingResults results, final ParsingParameters parameters) {
		try {
			final Collection<T> collection = (Collection<T>) parameter.getField().get(parameters.getTarget());
			if (collection == null) {
				results.addError("Target collection for parameter " + parameters.getArgument() + " is null");
				return;
			}
			final T value = parser.getValue(parameters.getValue());
			if (!parameter.getValidator().equals(ParameterValidator.class)) {
				if (parameter.getValidator().isInterface()) {
					results.addError("Validator " + parameter.getValidator().getName()
							+ " could not be instantiated for parameter " + parameters.getArgument());
					LG.warning("Parameter " + parameters.getArgument() + "'s validator cannot be instantiated: validator "
							+ parameter.getValidator() + " is an interface");
					return;
				}
				parameter.getValidator().newInstance().validate(value);
			}
			collection.add(value);
			results.removeRequiredParameter(parameter);
		} catch (final IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			results.addError("Value " + parameters.getValue() + " could not be assigned to parameter "
					+ parameters.getArgument());
			LG.warning("Parameter " + parameters.getArgument() + " value could not be set: "
					+ ExceptionUtils.display(e));
		}
	}
}
