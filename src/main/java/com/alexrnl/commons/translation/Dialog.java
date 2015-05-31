package com.alexrnl.commons.translation;

/**
 * Interface for a dialog to show a message to a user.<br />
 * The implementation of this interface should provide the title, message and type of the
 * represented dialog using the appropriate methods. Note that the parameters of the dialog must
 * apply to the message of the dialog.
 * @author Alex
 */
public interface Dialog extends ParametrableTranslation {
	/**
	 * The title of the dialog.
	 * @return the translation of the dialog's title.
	 */
	String title ();
	
	/**
	 * The message of the dialog.
	 * @return the translation of the dialog's message.
	 */
	String message ();
	
}
