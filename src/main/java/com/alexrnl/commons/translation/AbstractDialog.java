package com.alexrnl.commons.translation;


/**
 * This class is defining the translation for a dialog.<br />
 * A standard dialog is composed of a title and a message, this class factorise the definition of
 * the keys for these data and uses the {@link #toString()} method to build the key.<br />
 * This class also implements the {@link ParametrableTranslation} interface which returns an empty
 * parameter array by default (i.e. no parameter are accepted by this dialog); override the method
 * {@link #getParameters()} if necessary.
 * Example of implementation (with the use of the parameters):
 * 
 * <pre>
 * public class HelloDialog extends AbstractDialog {
 * 	private String		helloKey;
 * 	private Object[]	parameters;
 * 
 * 	public HelloDialog (String name) {
 * 		super();
 * 		this.helloKey = &quot;commons.dialogs.hello&quot;;
 * 		this.parameters = new Object[1];
 * 		parameters[0] = name;
 * 	}
 * 
 * 	public String toString () {
 * 		return helloKey;
 * 	}
 * 
 * 	public Object[] getParameters () {
 * 		return parameters;
 * 	}
 * 
 * }
 * </pre>
 * 
 * When instantiating this class, the translation key for the title will be
 * <code>commons.dialogs.hello.title</code> and the translation key for the message will be
 * <code>commons.dialogs.hello.message</code>.
 * @author Alex
 */
public abstract class AbstractDialog implements ParametrableTranslation {
	/** The string defining the key for the dialog's title */
	public static final String	TITLE	= "title";
	/** The string defining the key for the dialog's message */
	public static final String	MESSAGE	= "message";

	/**
	 * The title of the dialog.
	 * @return the translation of the dialog's title.
	 */
	public String title () {
		return toString() + Translator.HIERARCHY_SEPARATOR + TITLE;
	}
	
	/**
	 * The message of the dialog.
	 * @return the translation of the dialog's message.
	 */
	public String message () {
		return toString() + Translator.HIERARCHY_SEPARATOR + MESSAGE;
	}

	/**
	 * Provide the key to the dialog, which allow to define the {@link #title()} and
	 * {@link #message()} method at this level.
	 */
	@Override
	public abstract String toString ();

	/**
	 * By default, all dialogs are configurable but return no parameters.<br />
	 * This allow to define generic behavior for displaying dialogs, override this if the dialog
	 * requires parameters.
	 */
	@Override
	public Object[] getParameters () {
		return new Object[0];
	}
}
