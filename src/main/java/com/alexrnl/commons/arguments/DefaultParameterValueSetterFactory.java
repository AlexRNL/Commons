package com.alexrnl.commons.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.alexrnl.commons.arguments.parsers.AbstractParser;
import com.alexrnl.commons.arguments.parsers.ByteParser;
import com.alexrnl.commons.arguments.parsers.CharParser;
import com.alexrnl.commons.arguments.parsers.ClassParser;
import com.alexrnl.commons.arguments.parsers.DoubleParser;
import com.alexrnl.commons.arguments.parsers.FloatParser;
import com.alexrnl.commons.arguments.parsers.IntParser;
import com.alexrnl.commons.arguments.parsers.LongParser;
import com.alexrnl.commons.arguments.parsers.ParameterParser;
import com.alexrnl.commons.arguments.parsers.PathParser;
import com.alexrnl.commons.arguments.parsers.ShortParser;
import com.alexrnl.commons.arguments.parsers.StringParser;
import com.alexrnl.commons.arguments.parsers.WByteParser;
import com.alexrnl.commons.arguments.parsers.WCharParser;
import com.alexrnl.commons.arguments.parsers.WDoubleParser;
import com.alexrnl.commons.arguments.parsers.WFloatParser;
import com.alexrnl.commons.arguments.parsers.WIntegerParser;
import com.alexrnl.commons.arguments.parsers.WLongParser;
import com.alexrnl.commons.arguments.parsers.WShortParser;

/**
 * Default implementation of the {@link ParameterValueSetterFactory}.
 * @author Alex
 */
public class DefaultParameterValueSetterFactory implements ParameterValueSetterFactory {
	/** Logger */
	private static final Logger					LG				= Logger.getLogger(DefaultParameterValueSetterFactory.class.getName());
	
	/** The default parameter parsers */
	private static final List<ParameterParser>	DEFAULT_PARSERS	= Collections.unmodifiableList(Arrays.asList(
																	// primitive types
																	new ByteParser(),
																	new CharParser(),
																	new DoubleParser(),
																	new FloatParser(),
																	new IntParser(),
																	new LongParser(),
																	new ShortParser(),
																	// wrappers
																	new WByteParser(),
																	new WCharParser(),
																	new WDoubleParser(),
																	new WFloatParser(),
																	new WIntegerParser(),
																	new WLongParser(),
																	new WShortParser(),
																	// others
																	new StringParser(),
																	new ClassParser(),
																	new PathParser()
																));
	

	/** Map with the parameter parsers to use */
	private final Map<Class<?>, ParameterParser>	parsers;
	
	/**
	 * Constructor #1.<br />
	 */
	public DefaultParameterValueSetterFactory () {
		super();
		this.parsers = new HashMap<>();
		for (final ParameterParser parser : DEFAULT_PARSERS) {
			if (addParameterParser(parser)) {
				LG.warning("Default parsers override each other for class " + parser.getFieldType());
			}
		}
	}
	
	@Override
	public <T> boolean addParameterParser (final AbstractParser<T> parser) {
		return addParameterParser((ParameterParser) parser);
	}
	
	/**
	 * Add a parameter parser to the current arguments.
	 * @param parser
	 *        the parser to add.
	 * @return <code>true</code> if a previous parser was already set for this field type.
	 * @see ParameterParser
	 */
	private boolean addParameterParser (final ParameterParser parser) {
		final boolean override = parsers.containsKey(parser.getFieldType());
		parsers.put(parser.getFieldType(), parser);
		return override;
	}
	
	@Override
	public ParameterValueSetter createParameterValueSetter (final Parameter parameter) {
		final Class<?> parameterType = parameter.getField().getType();
		if (parsers.containsKey(parameterType)) {
			return new GenericFieldSetter(parameter, parsers.get(parameterType));
		}
		if (Collection.class.isAssignableFrom(parameterType)) {
			return new CollectionFieldSetter(parameter, Collections.unmodifiableMap(parsers));
		}
		return new ParserNotFound(parameter);
	}
}
