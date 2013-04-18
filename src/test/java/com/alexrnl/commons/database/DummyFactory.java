package com.alexrnl.commons.database;

import java.io.IOException;
import java.util.Set;

/**
 * Dummy factory, for test purposes.
 * @author Alex
 */
class DummyFactory extends AbstractDAOFactory {
	/**
	 * Constructor #1.<br />
	 */
	public DummyFactory () {
		super();
		addDAO(Dummy.class, new DAO<Dummy>() {
			
			@Override
			public void close () throws IOException {}
			
			@Override
			public Dummy create (final Dummy obj) {
				return null;
			}
			
			@Override
			public Dummy find (final int id) {
				return null;
			}
			
			@Override
			public boolean update (final Dummy obj) {
				return false;
			}
			
			@Override
			public boolean delete (final Dummy obj) {
				return false;
			}
			
			@Override
			public Set<Dummy> retrieveAll () {
				return null;
			}
			
			@Override
			public Set<Dummy> search (final Column field, final String value) {
				return null;
			}});
	}
	
}