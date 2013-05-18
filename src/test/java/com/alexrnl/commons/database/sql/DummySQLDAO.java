package com.alexrnl.commons.database.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.alexrnl.commons.database.Column;
import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.Dummy.DummyColumn;
import com.alexrnl.commons.database.EntityColumn;

/**
 * DAO for the Dummy entity.
 * @author Alex
 */
public class DummySQLDAO extends SQLDAO<Dummy> {
	/** The sample of entity */
	private Dummy													sample;
	/** Columns of the dummy table */
	private final Map<? extends Enum<? extends EntityColumn>, Column>	columns;
	
	/**
	 * Constructor #1.<br />
	 * @param connection
	 *        the connection to the SQL database.
	 * @throws SQLException
	 *         if there was an error while initializing the database.
	 */
	public DummySQLDAO (final Connection connection) throws SQLException {
		super(connection);
		columns = getEntitySample().getEntityColumns();
	}
	
	@Override
	protected Dummy getEntitySample () {
		if (sample == null) {
			sample = new Dummy();
		}
		return sample;
	}
	
	@Override
	protected Dummy createEntityFromResult (final ResultSet result) throws SQLException {
		final Dummy newDummy = new Dummy();
		newDummy.setId(result.getInt(columns.get(DummyColumn.ID).getName()));
		newDummy.setName(result.getString(columns.get(DummyColumn.NAME).getName()));
		return newDummy;
	}
	
	@Override
	protected void fillInsertStatement (final PreparedStatement statement, final Dummy obj)
			throws SQLException {
		statement.setString(1, obj.getName());
	}
	
	@Override
	protected void fillUpdateStatement (final PreparedStatement statement, final Dummy obj)
			throws SQLException {
		fillInsertStatement(statement, obj);
		statement.setInt(columns.size(), obj.getId());
	}
}
