package com.alexrnl.commons.database.sql;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.alexrnl.commons.database.DataBaseConfigurationError;
import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.Dummy.DummyColumn;
import com.alexrnl.commons.database.Fake;
import com.alexrnl.commons.database.structure.Column;
import com.alexrnl.commons.database.structure.Entity;
import com.alexrnl.commons.database.structure.EntityColumn;
import com.alexrnl.commons.database.structure.NoIdError;
import com.alexrnl.commons.utils.object.AutoEquals;
import com.alexrnl.commons.utils.object.AutoHashCode;
import com.alexrnl.commons.utils.object.Field;

/**
 * Test suite for the {@link QueryGenerator} class.
 * @author Alex
 */
public class QueryGeneratorTest {
	
	/**
	 * Enumeration for the columns of a new entity which is not declared with the ID
	 * as the first element.
	 * @author Alex
	 */
	public enum ReverseEnum implements EntityColumn {
		/** The name column */
		NAME ("Name"),
		/** The id column */
		ID ("Id"),
		/** The value column */
		VALUE ("Value");
		
		/** The name of the attribute */
		private final String attributeName;
		
		/**
		 * Constructor #1.<br />
		 * Unique constructor.
		 * @param attributeName
		 *        the name of the attribute.
		 */
		private ReverseEnum (final String attributeName) {
			this.attributeName = attributeName;
		}
		
		@Override
		public String getFieldName () {
			return attributeName;
		}
		
	}
	
	/**
	 * Reverse dummy entity class.
	 * @author Alex
	 */
	private class ReverseDummy extends Dummy {
		/** Serial version UID */
		private static final long	serialVersionUID	= 2118152877617057020L;
		
		/** The value of the dummy */
		private int value;
		
		@Override
		public Map<? extends Enum<? extends EntityColumn>, Column> getEntityColumns () {
			final Map<ReverseEnum, Column> map = new EnumMap<>(ReverseEnum.class);
			map.put(ReverseEnum.ID, Dummy.getColumns().get(DummyColumn.ID));
			map.put(ReverseEnum.NAME, Dummy.getColumns().get(DummyColumn.NAME));
			map.put(ReverseEnum.VALUE, new Column(Integer.class, "value"));
			return map;
		}
		
		@Override
		public String getEntityName () {
			return "ReverseDummy";
		}

		/**
		 * Return the attribute value.
		 * @return the attribute value.
		 */
		@Field
		public int getValue () {
			return value;
		}
		
		/**
		 * Set the attribute value.
		 * @param value
		 *        the attribute value.
		 */
		public void setValue (final int value) {
			this.value = value;
		}

		@Override
		public ReverseDummy clone () throws CloneNotSupportedException {
			final ReverseDummy clone = (ReverseDummy) super.clone();
			clone.setValue(value);
			return clone;
		}

		@Override
		public String toString () {
			return "ReverseDummy[ id=" + getId() + "; name=" + getName() + "; value=" + getValue() + "]";
		}

		@Override
		public int hashCode () {
			return AutoHashCode.getInstance().hashCode(this);
		}

		@Override
		public boolean equals (final Object obj) {
			if (!(obj instanceof ReverseDummy)) {
				return false;
			}
			return AutoEquals.getInstance().compare(this, (Dummy) obj);
		}
		
	}
	
	/**
	 * Check that no instance can be created.
	 * @throws Exception
	 *         if there is a reflection exception thrown.
	 */
	@Test(expected = InvocationTargetException.class)
	public void testForbiddenInstance () throws Exception {
		final Constructor<?> defaultConstructor = QueryGenerator.class.getDeclaredConstructors()[0];
		defaultConstructor.setAccessible(true);
		defaultConstructor.newInstance();
	}
	
	/**
	 * Test method for {@link QueryGenerator#getIDColumn(Entity)}.
	 */
	@Test
	public void testGetIDColumn () {
		Logger.getLogger(QueryGenerator.class.getName()).setLevel(Level.FINE);
		final Column idColumn = Dummy.getColumns().get(DummyColumn.ID);
		assertEquals(idColumn, QueryGenerator.getIDColumn(new Dummy()));
		assertEquals(idColumn, QueryGenerator.getIDColumn(new ReverseDummy()));
		Logger.getLogger(QueryGenerator.class.getName()).setLevel(Level.INFO);
	}
	
	/**
	 * Test method for {@link QueryGenerator#getIDColumn(Entity)}.<br />
	 * This expect either an {@link AssertionError} or a {@link NoIdError}.
	 */
	@Test(expected = Error.class)
	public void testGetIDColumnNoIdError () {
		QueryGenerator.getIDColumn(new Fake());
	}
	
	/**
	 * Test method for {@link QueryGenerator#escapeSpecialChars(String)}.
	 */
	@Test
	public void testEscapeSpecialChars () {
		assertEquals("test", QueryGenerator.escapeSpecialChars("test"));
		assertEquals("te''st", QueryGenerator.escapeSpecialChars("te'st"));
		assertEquals("te\\\"st", QueryGenerator.escapeSpecialChars("te\"st"));
	}
	
	/**
	 * Test method for {@link QueryGenerator#insert(Entity)}.
	 */
	@Test
	public void testInsertEntity () {
		assertEquals("INSERT INTO Dummy(`id`,`name`) VALUES ", QueryGenerator.insert(new Dummy()));
	}
	
	/**
	 * Test method for {@link QueryGenerator#insert(Entity, boolean)}.
	 */
	@Test
	public void testInsertEntityBoolean () {
		assertEquals("INSERT INTO Dummy(`id`,`name`) VALUES ", QueryGenerator.insert(new Dummy(), true));
		assertEquals("INSERT INTO Dummy(`name`) VALUES ", QueryGenerator.insert(new Dummy(), false));
	}
	
	/**
	 * Test method for {@link QueryGenerator#delete(Entity)}.
	 */
	@Test
	public void testDeleteEntity () {
		assertEquals("DELETE FROM Dummy WHERE id = '0'", QueryGenerator.delete(new Dummy(0)));
	}
	
	/**
	 * Test method for {@link QueryGenerator#delete(Entity, boolean)}.
	 */
	@Test
	public void testDeleteEntityBoolean () {
		assertEquals("DELETE FROM Dummy WHERE id = '0'", QueryGenerator.delete(new Dummy(0), false));
		assertEquals("DELETE FROM Dummy WHERE id = ?", QueryGenerator.delete(new Dummy(0), true));
	}
	
	/**
	 * Test method for {@link QueryGenerator#update(Entity)}.
	 */
	@Test
	public void testUpdate () {
		assertEquals("UPDATE Dummy SET ", QueryGenerator.update(new Dummy()));
	}
	
	/**
	 * Test method for {@link QueryGenerator#whereID(Entity)}.
	 */
	@Test
	public void testWhereIDEntity () {
		assertEquals(" WHERE id = '0'", QueryGenerator.whereID(new Dummy(0)));
	}
	
	/**
	 * Test method for {@link QueryGenerator#whereID(Entity, Object)}.
	 */
	@Test
	public void testWhereIDEntityObject () {
		assertEquals(" WHERE id = 'test'", QueryGenerator.whereID(new Dummy(), "test"));
		assertEquals(" WHERE id = '0'", QueryGenerator.whereID(new Dummy(), 0));
	}
	
	/**
	 * Test method for {@link QueryGenerator#where(Column, Object)}.
	 */
	@Test
	public void testWhereColumnObject () {
		assertEquals(" WHERE id = ?", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), null));
		assertEquals(" WHERE id = 'test'", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), "test"));
	}
	
	/**
	 * Test method for {@link QueryGenerator#where(Column, Object, boolean)}.
	 */
	@Test
	public void testWhereColumnObjectBoolean () {
		assertEquals(" WHERE id LIKE ?", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), null, true));
		assertEquals(" WHERE id LIKE 'test'", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), "test", true));
		assertEquals(" WHERE id LIKE '0'", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), 0, true));
		assertEquals(" WHERE id = ?", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), null, false));
		assertEquals(" WHERE id = 'test'", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), "test", false));
		assertEquals(" WHERE id = '0'", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.ID), 0, false));
		assertEquals(" WHERE name = 'test'", QueryGenerator.where(new Dummy().getEntityColumns().get(DummyColumn.NAME), "test", false));
	}
	
	/**
	 * Test method for {@link QueryGenerator#whereLike(Column, Object)}.
	 */
	@Test
	public void testWhereLike () {
		assertEquals(" WHERE id LIKE ?", QueryGenerator.whereLike(new Dummy().getEntityColumns().get(DummyColumn.ID), null));
		assertEquals(" WHERE id LIKE 'test'", QueryGenerator.whereLike(new Dummy().getEntityColumns().get(DummyColumn.ID), "test"));
	}
	
	/**
	 * Test method for {@link QueryGenerator#searchAll(Entity)}.
	 */
	@Test
	public void testSearchAll () {
		assertEquals("SELECT * FROM Dummy", QueryGenerator.searchAll(new Dummy()));
	}
	
	/**
	 * Test method for {@link QueryGenerator#insertPrepared(Entity)}.
	 */
	@Test
	public void testInsertPrepared () {
		assertEquals("INSERT INTO Dummy(`name`) VALUES (?)", QueryGenerator.insertPrepared(new Dummy()));
		assertEquals("INSERT INTO ReverseDummy(`name`,`value`) VALUES (?, ?)", QueryGenerator.insertPrepared(new ReverseDummy()));
	}
	
	/**
	 * Test method for single column entity.
	 */
	@Test(expected = DataBaseConfigurationError.class)
	public void testInsertPreparedWithSingleColumnEntity () {
		final Entity singleColumnEntity = mock(Entity.class);
		final Map singleColumns = mock(Map.class);
		when(singleColumnEntity.getEntityColumns()).thenReturn(singleColumns);
		when(singleColumns.size()).thenReturn(1);
		QueryGenerator.insertPrepared(singleColumnEntity);
	}
	
	/**
	 * Test method for {@link QueryGenerator#updatePrepared(Entity)}.
	 */
	@Test
	public void testUpdatePrepared () {
		assertEquals("UPDATE Dummy SET name = ? WHERE id = ?", QueryGenerator.updatePrepared(new Dummy()));
	}
}
