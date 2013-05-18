package com.alexrnl.commons.database;

import java.util.EnumMap;
import java.util.Map;

import com.alexrnl.commons.utils.object.AutoCompare;
import com.alexrnl.commons.utils.object.AutoHashCode;
import com.alexrnl.commons.utils.object.Field;

/**
 * Dummy entity for test purposes.
 * @author Alex
 */
public class Dummy extends Entity {
	/** Serial version UID */
	private static final long	serialVersionUID	= 1L;
	
	/** Map with the columns */
	private static EnumMap<Dummy.DummyColumn, Column> map;
	/** Id of the class */
	private Integer id;
	/** Name of the dummy */
	private String name;

	/**
	 * Constructor #1.<br />
	 */
	public Dummy () {
		this((String) null);
	}
	
	/**
	 * Constructor #2.<br />
	 * @param id
	 *        the id.
	 */
	public Dummy (final Integer id) {
		super();
		this.id = id;
	}

	/**
	 * Constructor #3.<br />
	 * @param name
	 *        the name.
	 */
	public Dummy (final String name) {
		super();
		this.name = name;
	}

	@Override
	public String getEntityName () {
		return "Dummy";
	}
	
	/** Dummy columns enumeration */
	public enum DummyColumn implements EntityColumn {
		/** The id column */
		ID ("Id"),
		/** The name column */
		NAME ("Name");
		
		/** The name of the attribute */
		private final String attributeName;
		
		/**
		 * Constructor #1.<br />
		 * Unique constructor.
		 * @param attributeName
		 *        the name of the attribute.
		 */
		private DummyColumn (final String attributeName) {
			this.attributeName = attributeName;
		}
		
		@Override
		public String getFieldName () {
			return attributeName;
		}
		
	}
	
	@Override
	protected void setEntityColumns () {
		// No synchronization for the tests
		map = new EnumMap<>(Dummy.DummyColumn.class);
		map.put(DummyColumn.ID, new Column(Integer.class, "id", true));
		map.put(DummyColumn.NAME, new Column(String.class, "name"));
	}
	
	@Override
	public Map<? extends Enum<? extends EntityColumn>, Column> getEntityColumns () {
		return map;
	}
	
	/**
	 * Return the columns of the map.
	 * @return the columns.
	 */
	public static Map<? extends Enum<? extends EntityColumn>, Column> getColumns () {
		return map;
	}

	@Override
	public String getID () {
		return String.valueOf(getId());
	}
	
	/**
	 * Return the attribute id.
	 * @return the attribute id.
	 */
	@Field
	public Integer getId () {
		return id;
	}
	
	/**
	 * Set the attribute id.
	 * @param id the attribute id.
	 */
	public void setId (final Integer id) {
		this.id = id;
	}
	
	/**
	 * Return the attribute name.
	 * @return the attribute name.
	 */
	@Field
	public String getName () {
		return name;
	}
	
	/**
	 * Set the attribute name.
	 * @param name the attribute name.
	 */
	public void setName (final String name) {
		this.name = name;
	}

	@Override
	public Dummy clone () throws CloneNotSupportedException {
		final Dummy clone = new Dummy();
		clone.setId(getId());
		clone.setName(getName());
		return clone;
	}

	@Override
	public String toString () {
		return "Dummy[ id=" + id + "; name=" + name + "]";
	}

	@Override
	public int hashCode () {
		return AutoHashCode.getInstance().hashCode(this);
	}

	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof Dummy)) {
			return false;
		}
		return AutoCompare.getInstance().compare(this, (Dummy) obj);
	}
	
}