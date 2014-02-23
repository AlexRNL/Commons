package com.alexrnl.commons.database.structure;

import java.io.Serializable;

import com.alexrnl.commons.utils.object.AutoEquals;
import com.alexrnl.commons.utils.object.AutoHashCode;
import com.alexrnl.commons.utils.object.Field;

/**
 * Class representing a column in a entity.<br />
 * It is defined by a name and a type. The columns should be load once and for all to avoid
 * unnecessary duplicates.<br />
 * Instances of this class are immutable.
 * @author Alex
 */
public class Column implements Serializable {
	/** Serial Version UID */
	private static final long	serialVersionUID	= 7764202331118428284L;
	
	/** The Java type associated to the column */
	private final Class<?>		type;
	/** The name (identifier) of the column in the data base */
	private final String		name;
	/** <code>true</code> if the column is an index of the table */
	private final boolean		isID;
	
	/**
	 * Constructor #1.<br />
	 * @param type
	 *            the type of the column.
	 * @param name
	 *            the name of the column.
	 * @param isID
	 *            <code>true</code> if the column is an index.
	 */
	public Column (final Class<?> type, final String name, final boolean isID) {
		this.type = type;
		this.name = name;
		this.isID = isID;
	}
	
	/**
	 * Constructor #2.<br />
	 * Build a column which is not an index of the table.
	 * @param type
	 *            the type of the column.
	 * @param name
	 *            the name of the column.
	 */
	public Column (final Class<?> type, final String name) {
		this(type, name, false);
	}
	
	/**
	 * Return the type.
	 * @return the type
	 */
	@Field
	public Class<?> getType () {
		return type;
	}
	
	/**
	 * Return the name.
	 * @return the name.
	 */
	@Field
	public String getName () {
		return name;
	}
	
	/**
	 * Return the isID.
	 * @return <code>true</code> if the column is an index of the table.
	 */
	@Field
	public boolean isID () {
		return isID;
	}
	
	@Override
	public String toString () {
		return "name: " + name + ", type: " + type.getSimpleName() + ", is id: " + isID;
	}
	
	@Override
	public int hashCode () {
		return AutoHashCode.getInstance().hashCode(this);
	}
	
	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof Column)) {
			return false;
		}
		return AutoEquals.getInstance().compare(this, (Column) obj);
	}
	
}
