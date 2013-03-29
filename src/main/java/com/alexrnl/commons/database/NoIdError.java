package com.alexrnl.commons.database;

import com.alexrnl.commons.error.TopLevelError;

/**
 * Error thrown when no Id column where found in an entity.<br />
 * @author Alex
 */
public class NoIdError extends TopLevelError {
	/** The serial version UID */
	private static final long				serialVersionUID	= 7697590367643799579L;
	
	/** The class which has no id column */
	private final Class<? extends Entity>	entityClass;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor. Build a NoIdExcetpion with no entity class.
	 */
	public NoIdError () {
		this(Entity.class);
	}
	
	/**
	 * Constructor #2.<br />
	 * @param entityClass
	 *        the class which do not own a column which is marked as an Id.
	 */
	public NoIdError (final Class<? extends Entity> entityClass) {
		super("Could not find id column in entity " + entityClass.getSimpleName());
		this.entityClass = entityClass;
	}
	
	/**
	 * Return the attribute entityClass.
	 * @return the attribute entityClass.
	 */
	public Class<? extends Entity> getEntityClass () {
		return entityClass;
	}
	
}
