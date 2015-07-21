package com.alexrnl.commons.translation;

import static com.alexrnl.commons.translation.Translator.HIERARCHY_SEPARATOR;

import java.util.Collection;


/**
 * This class is defining a dialog which factorises the translation keys construction.<br />
 * Example of implementation (with the use of the parameters):
 * <pre>
 * helloDialog = new StandardDialog(&quot;commons.dialogs.hello&quot, name);
 * </pre>
 * When using this class, the translation key for the title will be
 * <code>commons.dialogs.hello.title</code> and the translation key for the message will be
 * <code>commons.dialogs.hello.message</code>.
 * @see SimpleDialog
 * @author Alex
 */
public class StandardDialog implements Dialog {
	/** The string defining the key for the dialog's title */
	public static final String	TITLE	= "title";
	/** The string defining the key for the dialog's message */
	public static final String	MESSAGE	= "message";
	
	/** The underlying dialog */
	private final Dialog		dialog;
	
	/**
	 * Constructor #1.<br />
	 * @param dialogKey
	 *        the translation key of the dialog.
	 * @param parameters
	 *        the parameters of the dialog.
	 */
	public StandardDialog (final String dialogKey, final Object... parameters) {
		super();
		this.dialog = new SimpleDialog(dialogKey + HIERARCHY_SEPARATOR + TITLE,
				dialogKey + HIERARCHY_SEPARATOR + MESSAGE, parameters);
	}
	
	@Override
	public String title () {
		return dialog.title();
	}
	
	@Override
	public String message () {
		return dialog.message();
	}
	
	@Override
	public String getTranslationKey () {
		return dialog.getTranslationKey();
	}
	
	@Override
	public Collection<Object> getParameters () {
		return dialog.getParameters();
	}
}
