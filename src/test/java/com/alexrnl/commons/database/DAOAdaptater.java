package com.alexrnl.commons.database;

import java.io.IOException;
import java.util.Set;

import com.alexrnl.commons.database.dao.DAO;
import com.alexrnl.commons.database.structure.Column;
import com.alexrnl.commons.database.structure.Entity;

/**
 * Simple adapter for DAO test purposes.
 * @author Alex
 * @param <T>
 *        the entity to use in the DAO.
 */
public class DAOAdaptater<T extends Entity> implements DAO<T> {
	
	@Override
	public void close () throws IOException {
	}
	
	@Override
	public T create (final T obj) {
		return null;
	}
	
	@Override
	public T find (final int id) {
		return null;
	}
	
	@Override
	public boolean update (final T obj) {
		return false;
	}
	
	@Override
	public boolean delete (final T obj) {
		return false;
	}
	
	@Override
	public Set<T> retrieveAll () {
		return null;
	}
	
	@Override
	public Set<T> search (final Column field, final String value) {
		return null;
	}
	
}
