package com.alexrnl.commons.arguments;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import com.alexrnl.commons.arguments.parsers.AbstractParser;
import com.alexrnl.commons.arguments.parsers.ParameterParser;
import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Implementation of {@link ParameterValueSetter} for Collections type fields.
 * @author Alex
 */
public class CollectionFieldSetter implements ParameterValueSetter {
	/** Logger */
	private static final Logger						LG	= Logger.getLogger(CollectionFieldSetter.class.getName());
			
	/** The parameter whose value to set */
	private final Parameter							parameter;
	/** The map for the available parsers */
	private final Map<Class<?>, ParameterParser>	parsers;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter whose value to set.
	 * @param parsers
	 *        the map for the available parser by class.
	 */
	public CollectionFieldSetter (final Parameter parameter, final Map<Class<?>, ParameterParser> parsers) {
		super();
		this.parameter = parameter;
		this.parsers = parsers;
	}
	
	@Override
	public void setValue (final ParsingResults results, final Object target, final String value, final String argument) {
		// TODO externalize error handling in factory?
		if (parameter.getItemClass() == Object.class) {
			results.addError("No item class defined for parameter " + parameter.getNames());
		} else if (parsers.containsKey(parameter.getItemClass())) {
			try {
				final AbstractParser<?> collectionItemParser = (AbstractParser<?>) parsers.get(parameter.getItemClass());
				final Collection collection = (Collection<?>) parameter.getField().get(target);
				if (collection == null) {
					results.addError("Target collection for parameter " + argument + " is null");
				} else {
					collection.add(collectionItemParser.getValue(value));
					results.removeRequiredParameter(parameter);
				}
			} catch (final IllegalArgumentException | IllegalAccessException e) {
				results.addError("Value " + value + " could not be assigned to parameter " + argument);
				LG.warning("Parameter " + argument + " value could not be set: "
						+ ExceptionUtils.display(e));
			}
		} else {
			results.addError("No parser found for type " + parameter.getItemClass().getName() + " (parameter "
					+ argument + ").");
		}
	}
}
