package com.alexrnl.commons.database;

import static org.junit.Assert.assertEquals;

import java.util.EnumMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test suite for the {@link NoIdError} class.
 * @author Alex
 */
public class NoIdErrorTest {
	
	/**
	 * Dummy entity for test purposes.
	 * @author Alex
	 */
	private static class Dummy extends Entity {
		/** Serial version UID */
		private static final long	serialVersionUID	= 1L;
		
		/** Map with the columns */
		private EnumMap<DummyColumn, Column> map;
		
		@Override
		public String getEntityName () {
			return "Dummy";
		}
		
		/** Dummy columns enumeration */
		public enum DummyColumn implements EntityColumn {
			/** The id column */
			ID,
			/** The name column */
			NAME;
			
			@Override
			public String getFieldName () {
				return this.name();
			}
			
		}
		
		@Override
		protected void setEntityColumns () {
			// No synchronization for the tests
			map = new EnumMap<>(DummyColumn.class);
			map.put(DummyColumn.ID, new Column(Integer.class, "id", true));
			map.put(DummyColumn.NAME, new Column(String.class, "name"));
		}
		
		@Override
		public Map<? extends Enum<? extends EntityColumn>, Column> getEntityColumns () {
			return map;
		}
		
		@Override
		public String getID () {
			return null;
		}
		
	}
	/** Exception with no entity information */
	private final NoIdError	unknownException = new NoIdError();
	/** Exception with the entity that generated the problem */
	private final NoIdError	dummyException = new NoIdError(Dummy.class);
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.NoIdError#getEntityClass()}.
	 */
	@Test
	public void testGetEntityClass () {
		assertEquals(Entity.class, unknownException.getEntityClass());
		assertEquals(Dummy.class, dummyException.getEntityClass());
	}
	
	/**
	 * Test method for {@link java.lang.Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("Could not find id column in entity Entity", unknownException.getMessage());
		assertEquals("Could not find id column in entity Dummy", dummyException.getMessage());
	}
}
