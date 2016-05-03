package com.alexrnl.commons.arguments;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.arguments.parsers.AbstractParser;
import com.alexrnl.commons.arguments.parsers.ParameterParser;
import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.utils.StringUtils;
import com.alexrnl.commons.utils.object.ReflectUtils;

/**
 * Class which allow to parse string arguments and store them in a target object.
 * @author Alex
 */
public class Arguments {
	/** Logger */
	private static final Logger						LG							= Logger.getLogger(Arguments.class.getName());
	
	/** The tab character */
	private static final Character					TAB							= '\t';
	/** The number of tabs between the name of the argument and their description */
	private static final int						NB_TABS_BEFORE_DESCRIPTION	= 4;
	/** The character to use to indicate that strings will be joined. */
	private static final String						JOIN_MARK					= "\"";
	/** The character to escape the join mark in strings */
	private static final String						ESCAPER						= "\\";
	/** The short name for the help command */
	public static final String						HELP_SHORT_NAME				= "-h";
	/** The long name for the help command */
	public static final String						HELP_LONG_NAME				= "--help";
	
	/** The factory for parameter value setters */
	private final ParameterValueSetterFactory		valueSetterFactory;
	/** The name of the program */
	private final String							programName;
	/** The object which holds the target */
	private final Object							target;
	/** The list of parameters in the target */
	private final SortedSet<Parameter>				parameters;
	/** The output to use to display the usage of the arguments */
	private final PrintStream						out;
	/** Flag to allow unknown parameters*/
	private final boolean							allowUnknownParameters;
	
	/**
	 * Constructor #1.<br />
	 * Output is standard output, unknown parameter trigger error in parsing.
	 * @param programName
	 *        the name of the program.
	 * @param target
	 *        the object which holds the target.
	 */
	public Arguments (final String programName, final Object target) {
		this(programName, target, false);
	}
	
	/**
	 * Constructor #2.<br />
	 * Output is standard output.
	 * @param programName
	 *        the name of the program.
	 * @param target
	 *        the object which holds the target.
	 * @param allowUnknownParameters
	 *        if <code>true</code> allow parsing unknown parameters.
	 */
	public Arguments (final String programName, final Object target, final boolean allowUnknownParameters) {
		this(programName, target, System.out, allowUnknownParameters);
	}
	
	/**
	 * Constructor #2.<br />
	 * Output is standard output.
	 * @param programName
	 *        the name of the program.
	 * @param target
	 *        the object which holds the target.
	 * @param out
	 *        the output stream to use for displaying argument's usage.
	 */
	public Arguments (final String programName, final Object target, final PrintStream out) {
		this(programName, target, out, false);
	}
	
	/**
	 * Constructor #3.<br />
	 * @param programName
	 *        the name of the program.
	 * @param target
	 *        the object which holds the target.
	 * @param out
	 *        the output stream to use for displaying argument's usage.
	 * @param allowUnknownParameters
	 *        if <code>true</code> allow parsing unknown parameters.
	 */
	public Arguments (final String programName, final Object target, final PrintStream out, final boolean allowUnknownParameters) {
		super();
		this.programName = programName;
		this.target = target;
		this.parameters = Collections.unmodifiableSortedSet(retrieveParameters(target));
		this.out = out;
		this.allowUnknownParameters = allowUnknownParameters;
		valueSetterFactory = new DefaultParameterValueSetterFactory();
	}
	
	/**
	 * Parse and build a list with the parameters field marked with the {@link Param} annotation in
	 * the object specified.
	 * @param obj
	 *        the object to parse for parameters.
	 * @return the list of parameters associated to this object.
	 */
	private static SortedSet<Parameter> retrieveParameters (final Object obj) {
		final SortedSet<Parameter> params = new TreeSet<>();
		for (final Field field : ReflectUtils.retrieveFields(obj.getClass(), Param.class)) {
			field.setAccessible(true);
			final Parameter param = new Parameter(field, field.getAnnotation(Param.class));
			if (params.contains(param)) {
				throw new IllegalArgumentException("Parameter " + param.getNames() + " could not " +
						"be added, there is an other parameter with the same short name and order");
			}
			params.add(param);
		}
		return params;
	}
	
	/**
	 * Add a parameter parser to the current arguments.<br />
	 * Using an abstract parser allows to dynamically build collection using the parser.
	 * @param <T>
	 *        the type of parameter parsed.
	 * @param parser
	 *        the parser to add.
	 * @return <code>true</code> if a previous parser was already set for this field type.
	 * @see ParameterParser
	 * @see AbstractParser
	 */
	public <T> boolean addParameterParser (final AbstractParser<T> parser) {
		return valueSetterFactory.addParameterParser(parser);
	}
	
	/**
	 * Parse the arguments and set the target in the target object.
	 * @param arguments
	 *        the arguments to parse.
	 */
	public void parse (final String... arguments) {
		parse(Arrays.asList(arguments));
	}
	
	/**
	 * Parse the arguments and set the target in the target object.
	 * @param arguments
	 *        the arguments to parse.
	 * @throws IllegalArgumentException
	 *         if there is an error (or several) during parsing the arguments.
	 */
	public void parse (final Iterable<String> arguments) {
		final Iterable<String> args = joinArguments(arguments);
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Parsing arguments " + args.toString());
		}
		
		final ParsingResults results = new ParsingResults(getRequiredParameters());
		initializeBooleansParameters();
		
		// Parse arguments provided
		final Iterator<String> iterator = args.iterator();
		while (iterator.hasNext()) {
			final String argument = iterator.next();
			if (LG.isLoggable(Level.INFO)) {
				LG.info("Processing argument " + argument);
			}
			
			final Parameter currentParameter = getParameterByName(argument);
			
			// Skip the following cases: help, parameter unknown, booleans parameters and missing values
			if (isHelp(results, argument)
					|| !isParameterFound(results, argument, currentParameter)
					|| checkBooleanParameter(results, currentParameter)
					|| missingParameterValue(results, argument, iterator.hasNext())) {
				continue;
			}
			
			valueSetterFactory.createParameterValueSetter(currentParameter)
				.setValue(results, new ParsingParameters(target, iterator.next(), argument));
		}
		
		errorAndHelpProcessing(results);
	}

	/**
	 * Check if the name of the argument is an alias of the help command.
	 * @param results
	 *        the results of the parsing.
	 * @param name
	 *        the name to check.
	 * @return <code>true</code> if the argument is the help command.
	 */
	private static boolean isHelp (final ParsingResults results, final String name) {
		if (HELP_SHORT_NAME.equals(name) || HELP_LONG_NAME.equals(name)) {
			results.setHelpRequested(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Join arguments which are between the {@link #JOIN_MARK}.<br />
	 * @param arguments
	 *        the arguments to join.
	 * @return the joined arguments.
	 */
	static List<String> joinArguments (final Iterable<String> arguments) {
		final List<String> joined = new LinkedList<>();
		
		StringBuilder currentArgument = null;
		for (String argument : arguments) {
			// Start with separator
			if (argument.startsWith(JOIN_MARK)
					&& currentArgument == null) {
				argument = argument.substring(JOIN_MARK.length());
				currentArgument = new StringBuilder();
			}
			
			if (currentArgument != null) {
				if (currentArgument.length() > 0) {
					currentArgument.append(' ');
				}
				currentArgument.append(argument);
			} else {
				replaceAllAndAppend(joined, argument);
			}
			
			// End with separator
			if (argument.endsWith(JOIN_MARK)
					&& !argument.endsWith(ESCAPER + JOIN_MARK)
					&& currentArgument != null) {
				replaceAllAndAppend(joined, currentArgument.substring(0, currentArgument.length() - 1));
				currentArgument = null;
			}
		}
		if (currentArgument != null) {
			replaceAllAndAppend(joined, currentArgument.toString());
		}
		
		return joined;
	}
	
	/**
	 * Replace the sequence {@link #ESCAPER} + {@link #JOIN_MARK} by the {@link #JOIN_MARK} value,
	 * then the result is appended to the provided list.
	 * @param list
	 *        the list to append the computed result to.
	 * @param value
	 *        the value to add to the list, after cleaning it.
	 */
	private static void replaceAllAndAppend (final List<String> list, final String value) {
		String result = value;
		while (result.contains(ESCAPER + JOIN_MARK)) {
			result = result.replace(ESCAPER + JOIN_MARK, JOIN_MARK);
		}
		list.add(result);
	}
	
	/**
	 * Build a set with the reference of required parameters.
	 * @return the required parameters of the target.
	 */
	private Set<Parameter> getRequiredParameters () {
		final Set<Parameter> requiredParameters = new HashSet<>(parameters.size(), 1.0f);
		for (final Parameter parameter : parameters) {
			if (parameter.isRequired()) {
				requiredParameters.add(parameter);
			}
		}
		return requiredParameters;
	}
	
	/**
	 * Initialize all Booleans parameters to false.<br />
	 * No need to initialize booleans because they are set to false by default.
	 */
	private void initializeBooleansParameters () {
		for (final Parameter parameter : parameters) {
			if (parameter.getField().getType().equals(Boolean.class)) {
				try {
					parameter.getField().set(target, Boolean.FALSE);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LG.warning("Parameter " + parameter.getNames() + " value could not be " +
							"initialize to false: " + ExceptionUtils.display(e));
				}
			}
		}
	}
	
	/**
	 * Find a parameter according to its name.
	 * @param name
	 *        the name of the parameter to find.
	 * @return the parameter which has the specified name.
	 */
	private Parameter getParameterByName (final String name) {
		for (final Parameter parameter : parameters) {
			if (parameter.getNames().contains(name)) {
				return parameter;
			}
		}
		return null;
	}
	
	/**
	 * Process unknown arguments cases.
	 * @param results
	 *        the parsing results to update.
	 * @param argumentName
	 *        the name of the argument.
	 * @param currentParameter
	 *        the parameter to verify.
	 * @return <code>true</code> if the parameter was found.
	 */
	private boolean isParameterFound (final ParsingResults results, final String argumentName, final Parameter currentParameter) {
		if (currentParameter == null) {
			if (!allowUnknownParameters) {
				results.addError("No parameter with name " + argumentName + " found.");
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Check if the current parameter is a boolean and process it accordingly.
	 * @param results
	 *        the parsing results to update.
	 * @param currentParameter
	 *        the parameter to process.
	 * @return <code>true</code> if the parameter was a boolean parameter and has been processed.
	 */
	private boolean checkBooleanParameter (final ParsingResults results, final Parameter currentParameter) {
		final Class<?> type = currentParameter.getField().getType();
		if (type.equals(Boolean.class) || type.equals(boolean.class)) {
			try {
				if (type.equals(boolean.class)) {
					currentParameter.getField().setBoolean(target, true);
				} else {
					currentParameter.getField().set(target, Boolean.TRUE);
				}
				results.removeRequiredParameter(currentParameter);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				LG.warning("Parameter " + currentParameter.getNames() + " boolean value could not be set: "
						+ ExceptionUtils.display(e));
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Process cases where the value of the parameter is missing.
	 * @param results
	 *        the parsing results to update.
	 * @param argumentName
	 *        the name of the argument.
	 * @param hasValue
	 *        if there is a value to assign to the parameter.
	 * @return <code>true</code> if there is no value to assign to the parameter.
	 */
	private static boolean missingParameterValue (final ParsingResults results, final String argumentName, final boolean hasValue) {
		if (!hasValue) {
			results.addError("No value found for parameter " + argumentName + ".");
		}
		return !hasValue;
	}

	/**
	 * Process parsing results.<br />
	 * Display error messages and/or help if required, and throw an {@link IllegalArgumentException}
	 * if the parsing failed.
	 * @param results
	 *        the result of the parsing.
	 * @throws IllegalArgumentException
	 *         if the parsing of arguments has failed.
	 */
	private void errorAndHelpProcessing (final ParsingResults results) {
		// Check that all required arguments were set
		final Set<Parameter> requiredParameters = results.getRequiredParameters();
		if (!requiredParameters.isEmpty()) {
			final List<Set<String>> listParamNames = new ArrayList<>(requiredParameters.size());
			for (final Parameter parameter : requiredParameters) {
				listParamNames.add(parameter.getNames());
			}
			results.addError("The following parameters were not set: "
					+ StringUtils.separateWith(", ", listParamNames) + ".");
		}
		
		IllegalArgumentException e = null;
		final List<String> errors = results.getErrors();
		if (!errors.isEmpty() && !results.isHelpRequested()) {
			final String errorsMessage = StringUtils.separateWith("\n", errors);
			out.println(errorsMessage);
			e = new IllegalArgumentException(errorsMessage);
		}
		if (results.isHelpRequested() || !errors.isEmpty()) {
			usage();
		}
		if (e != null) {
			throw e;
		}
	}

	/**
	 * Display the parameter's usage on the output specified in the constructor.
	 */
	public void usage () {
		out.println(this);
	}
	
	@Override
	public String toString () {
		final StringBuilder usage = new StringBuilder(programName).append(" usage as follow:");
		for (final Parameter param : parameters) {
			usage.append(StringUtils.NEW_LINE).append(TAB);
			if (!param.isRequired()) {
				usage.append('[');
			} else {
				usage.append(' ');
			}
			usage.append("  ").append(StringUtils.separateWith(", ", param.getNames()));
			int nbTabs = NB_TABS_BEFORE_DESCRIPTION;
			while (nbTabs-- > 0) {
				usage.append(TAB);
			}
			usage.append(param.getDescription());
			if (!param.isRequired()) {
				usage.append("  ]");
			}
		}
		return usage.toString();
	}
}
