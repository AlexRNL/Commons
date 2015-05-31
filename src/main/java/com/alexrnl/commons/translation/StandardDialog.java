package com.alexrnl.commons.translation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * This class is defining the translation for a dialog.<br />
 * A standard dialog is the default implementation for the {@link Dialog} interface, this class
 * factorise the definition of the keys for these data.<br />
 * This class also implements the {@link ParametrableTranslation} interface which returns an empty
 * parameter array by default (i.e. no parameter are accepted by this dialog); specify the parameter
 * at instantiation if necessary.<br />
 * Example of implementation (with the use of the parameters):
 * <pre>
 * helloDialog = new StandardDialog(&quot;commons.dialogs.hello&quot, name);
 * </pre>
 * When using this class, the translation key for the title will be
 * <code>commons.dialogs.hello.title</code> and the translation key for the message will be
 * <code>commons.dialogs.hello.message</code>.
 * @author Alex
 */
public class StandardDialog implements Dialog {
	/** The string defining the key for the dialog's title */
	public static final String	TITLE	= "title";
	/** The string defining the key for the dialog's message */
	public static final String	MESSAGE	= "message";
	
	/** The translation key of the dialog */
	private final String		dialogKey;
	/** The parameters of the dialog */
	private final List<Object>	parameters;

	/**
	 * Constructor #1.<br />
	 * @param dialogKey
	 *        the translation key of the dialog.
	 * @param parameters
	 *        the parameters of the dialog.
	 */
	public StandardDialog (final String dialogKey, final Object... parameters) {
		super();
		this.dialogKey = dialogKey;
		this.parameters = Collections.unmodifiableList(Arrays.asList(parameters));
	}
	
	@Override
	public String title () {
		return dialogKey + Translator.HIERARCHY_SEPARATOR + TITLE;
	}
	
	@Override
	public String message () {
		return dialogKey + Translator.HIERARCHY_SEPARATOR + MESSAGE;
	}
	
	@Override
	public String getTranslationKey () {
		return message();
	}
	
	@Override
	public Collection<Object> getParameters () {
		return parameters;
	}
}
