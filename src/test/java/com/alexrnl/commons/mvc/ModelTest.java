package com.alexrnl.commons.mvc;


/**
 * Basic model implementation, for tests purposes.
 * @author Alex
 */
public class ModelTest extends AbstractModel {
	/** The value attribute */
	private Integer	value;
	/** The attribute for the name */
	private String	name;
	
	/**
	 * Constructor #1.<br />
	 */
	public ModelTest () {
		super();
	}
	
	/**
	 * Initialize the model with default values.
	 */
	public void initDefault () {
		setValue(8);
		setName("Alex");
	}
	
	/**
	 * Return the attribute value.
	 * @return the attribute value.
	 */
	public Integer getValue () {
		return value;
	}

	/**
	 * Set the attribute value.
	 * @param value the attribute value.
	 */
	public void setValue (final Integer value) {
		final Integer oldValue = getValue();
		this.value = value;
		fireModelChange("Value", oldValue, value);
	}

	/**
	 * Return the attribute name.
	 * @return the attribute name.
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the attribute name.
	 * @param name the attribute name.
	 */
	public void setName (final String name) {
		final String oldName = getName();
		this.name = name;
		fireModelChange("Name", oldName, name);
	}

	@Override
	public boolean persist () {
		return true;
	}
	
	@Override
	public void reload () {}
}
