package com.alexrnl.commons.mvc;

import java.beans.PropertyChangeEvent;

/**
 * Basic view implementation, for test purposes.
 * @author Alex
 */
public class ViewTest implements AbstractView {
	
	/** The controller for the view */
	private final ControllerTest	controller;
	/** The displayed value */
	private Integer					displayedValue;
	/** The displayed name */
	private String					displayedName;
	
	/**
	 * Constructor #1.<br />
	 * @param controller
	 *        the controller for the view.
	 */
	public ViewTest (final ControllerTest controller) {
		super();
		this.controller = controller;
	}
	
	/**
	 * Return the attribute displayedValue.
	 * @return the attribute displayedValue.
	 */
	public Integer getDisplayedValue () {
		return displayedValue;
	}
	
	/**
	 * Set the attribute displayedValue.
	 * @param value
	 *        the attribute value.
	 */
	public void setValue (final Integer value) {
		controller.changeValue(value);
	}
	
	/**
	 * Return the attribute displayedName.
	 * @return the attribute displayedName.
	 */
	public String getDisplayedName () {
		return displayedName;
	}
	
	/**
	 * Set the attribute displayedName.
	 * @param name
	 *        the attribute name.
	 */
	public void setDisplayedName (final String name) {
		controller.changeName(name);
	}
	
	@Override
	public void modelPropertyChange (final PropertyChangeEvent evt) {
		if (ControllerTest.MODEL_VALUE_PROPERTY.equals(evt.getPropertyName())) {
			displayedValue = (Integer) evt.getNewValue();
		} else if (ControllerTest.MODEL_NAME_PROPERTY.equals(evt.getPropertyName())) {
			displayedName = (String) evt.getNewValue();
		}
	}
}
