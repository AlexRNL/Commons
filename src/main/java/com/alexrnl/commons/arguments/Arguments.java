package com.alexrnl.commons.arguments;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.alexrnl.commons.utils.object.ReflectUtils;

/**
 * Class which allow to parse string arguments and store them in a target object.
 * @author Alex
 */
public class Arguments {
	/** Logger */
	private static Logger			lg	= Logger.getLogger(Arguments.class.getName());
	
	/** The name of the program. */
	private final String			programName;
	/** The object which holds the target */
	private final Object			target;
	/** The list of parameters in the target */
	private final List<Parameter>	parameters;
	
	
	/**
	 * Constructor #1.<br />
	 * @param programName
	 *        the name of the program.
	 * @param target
	 *        the object which holds the target.
	 */
	public Arguments (final String programName, final Object target) {
		super();
		this.programName = programName;
		this.target = target;
		this.parameters = retrieveParameters(target);
	}
	
	/**
	 * Parse and build a list with the parameters field marked with the {@link Param} annotation in
	 * the object specified.
	 * @param obj
	 *        the object to parse for parameters.
	 * @return the list of parameters associated to this object.
	 */
	private static List<Parameter> retrieveParameters (final Object obj) {
		final List<Parameter> params = new LinkedList<>();
		for (final Field field : ReflectUtils.retrieveFields(obj.getClass(), Param.class)) {
			field.setAccessible(true);
			params.add(new Parameter(field, field.getAnnotation(Param.class)));
		}
		return params;
	}
	
	/**
	 * Parse the arguments and set the target in the target object.
	 * @param arguments
	 *        the arguments to parse.
	 */
	public void parse (final String... arguments) {
		parse(arguments);
	}
	
	/**
	 * Parse the arguments and set the target in the target object.
	 * @param arguments
	 *        the arguments to parse.
	 */
	public void parse (final Iterable<String> arguments) {
		// TODO
	}
	
	/**
	 * Display the parameter's usage on the standard output.
	 */
	public void usage () {
		System.out.println(this);
	}
	
	@Override
	public String toString () {
		// TODO
		return "";
	}
}
