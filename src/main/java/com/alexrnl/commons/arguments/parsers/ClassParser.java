package com.alexrnl.commons.arguments.parsers;

import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.utils.object.ReflectUtils;

/**
 * Parser for the {@link Class} field type.
 * @author Alex
 */
@SuppressWarnings("rawtypes")
public class ClassParser extends AbstractParser<Class> {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(ClassParser.class.getName());
	
	/** The root package to avoid writing the package. */
	private final Package	rootPackage;
	
	/**
	 * Constructor #1.<br />
	 */
	public ClassParser () {
		this(null);
	}

	/**
	 * Constructor #2.<br />
	 * @param rootPackage
	 *        the root package to use for loading classes.
	 */
	public ClassParser (final Package rootPackage) {
		super(Class.class);
		this.rootPackage = rootPackage;
	}

	@Override
	protected Class getValue (final String parameter) throws IllegalArgumentException {
		try {
			return Class.forName(rootPackage != null ?
					rootPackage.getName() + ReflectUtils.PACKAGE_SEPARATOR + parameter
					: parameter);
		} catch (final ClassNotFoundException e) {
			lg.warning("Could not find class " + parameter + ": " + ExceptionUtils.display(e));
			throw new IllegalArgumentException("Could not find class " + parameter, e);
		}
	}
}
