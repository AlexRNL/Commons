package com.alexrnl.commons.database.structure;

/**
 * Interface for the entity columns.<br />
 * This interface should be implemented by the {@link java.util.Enumeration} which represent the
 * column of the
 * entity in the database.<br />
 * @author Alex
 */
public interface EntityColumn {
	
	/**
	 * Return the name of the column field, in the Java code.<br />
	 * This will later be used when dynamically accessing properties.
	 * @return the name of the property in the Java code.
	 */
	String getFieldName ();
}
