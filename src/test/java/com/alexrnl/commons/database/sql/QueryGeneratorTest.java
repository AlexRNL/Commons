package com.alexrnl.commons.database.sql;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.Dummy.DummyColumn;
import com.alexrnl.commons.database.Fake;
import com.alexrnl.commons.database.structure.Column;
import com.alexrnl.commons.database.structure.Entity;
import com.alexrnl.commons.database.structure.NoIdError;

/**
 * Test suite for the {@link QueryGenerator} class.
 * @author Alex
 */
public class QueryGeneratorTest {

	/**
	 * Test method for {@link QueryGenerator#getIDColumn(Entity)}.
	 */
	@Test
	public void testGetIDColumn () {
		assertEquals(new Dummy().getEntityColumns().get(DummyColumn.ID), QueryGenerator.getIDColumn(new Dummy()));
	}
	
	/**
	 * Test method for {@link QueryGenerator#getIDColumn(Entity)}.<br />
	 * This expect either an {@link AssertionError} or a {@link NoIdError}.
	 */
	@Test(expected=Error.class)
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
	}
	
	/**
	 * Test method for {@link QueryGenerator#updatePrepared(Entity)}.
	 */
	@Test
	public void testUpdatePrepared () {
		assertEquals("UPDATE Dummy SET name = ? WHERE id = ?", QueryGenerator.updatePrepared(new Dummy()));
	}
}
