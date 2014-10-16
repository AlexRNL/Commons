package com.alexrnl.commons.translation;

/**
 * GUI component key structure.<br />
 * Define an element that may also be accessible via a shortcut and/or display a title on the
 * component.<br />
 * Use this element to factorise code while setting up GUI component.<br />
 * Example of use:<br />
 * Translation file:
 * <pre>
 * commons.menu.file	<=>	"Create a new file"
 * commons.menu.file.shortcut	<=>	"ctrl f"
 * commons.menu.file.tooltip	<=>	"A new file will be started"
 * </pre>
 * Accessing the keys, in Java code:
 * <pre>
 * GUIElement fileMenu = new GUIElement("commons.menu.file");
 * fileMenu.getShortcut(); // return the shortcut key associated
 * fileMenu.getToolTip(); // return the tool tip key
 * </pre>
 * @author Alex
 */
public class GUIElement implements Translatable {
	/** The string defining the key for the component's shortcut */
	private static final String	SHORTCUT	= "shortcut";
	/** The string defining the key for the component's tooltip */
	public static final String	TOOL_TIP		= "tooltip";
	
	/** The key to the property */
	private final String		propertyKey;
	
	/**
	 * Constructor #1.<br />
	 * @param propertyKey
	 *        the key of the property.
	 */
	public GUIElement (final String propertyKey) {
		super();
		this.propertyKey = propertyKey;
	}
	
	@Override
	public String getTranslationKey () {
		return propertyKey;
	}
	
	/**
	 * The text of the element.
	 * @return the translation for the main text of the GUI element.
	 */
	public String getText () {
		return getTranslationKey();
	}
	
	/**
	 * The shortcut to use for quick call to the element.
	 * @return the shortcut to use.
	 */
	public String getShortcut () {
		return propertyKey + Translator.HIERARCHY_SEPARATOR + SHORTCUT;
	}
	
	/**
	 * The tool tip to show on the element.
	 * @return the tool tip to show on the element.
	 */
	public String getToolTip () {
		return propertyKey + Translator.HIERARCHY_SEPARATOR + TOOL_TIP;
	}
}
