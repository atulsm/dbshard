package com.atul;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DBController {
		
	/**
	 * Once this is called the conn should be initialized.
	 * 
	 * @return
	 * @throws SQLException
	 */
	void initConnection() throws SQLException;
	
	/**
	 * For Query types: Inserts/Updates/Delete/Create
	 * 
	 * @param query
	 * @throws SQLException
	 */
	void execute(String query) throws SQLException;
	
	/**
	 * For select queries
	 * 
	 * @param query
	 * @return number of records
	 * @throws SQLException
	 */
	int executeQuery(String query) throws SQLException;
	
	/**
	 * Close the connection
	 */
	void close() throws SQLException;
	
	PreparedStatement getPreparedStatement(String query) throws SQLException;

}
