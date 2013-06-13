package com.alexrnl.commons.mvc;


/**
 * Basic controller implementation, for test purposes.
 * @author Alex
 */
public class ControllerTest extends AbstractController {
	/** The name of the value property */
	public static final String	MODEL_VALUE_PROPERTY	= "Value";
	/** The name of the name property */
	public static final String	MODEL_NAME_PROPERTY		= "Name";
	
	
	/**
	 * Change the value of the model.
	 * @param value
	 *        the new value.
	 */
	public void changeValue (final int value) {
		setModelProperty(MODEL_VALUE_PROPERTY, value);
	}
	
	/**
	 * Change the name of the model.
	 * @param name
	 *        the new name.
	 */
	public void changeName (final String name) {
		setModelProperty(MODEL_NAME_PROPERTY, name);
	}
	
	@Override
	public void dispose () {
		
	}
	
	@Override
	public boolean persist () {
		return true;
	}
	
	@Override
	public void reload () {
	}
}
