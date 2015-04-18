package com.atul.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

/** 
 * @author Atul
 *
 */
public class PgsqlDBController implements DBController {

	private Connection conn;
	private Properties props;
	private String url;
	
	public PgsqlDBController(String url, Properties props){
		this.url=url;
		this.props=props;
	}
	
	public void initConnection() throws SQLException {
        conn = DriverManager.getConnection(url, props);
	}

	public void execute(String query) throws SQLException {
		Statement st = conn.createStatement();
		st.execute(query);
		st.close();
	}

	public int executeQuery(String query) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		int count = 0;
		while (rs.next()){
			count++;
		} rs.close();
		st.close();
		return count;
	}
	
	public void close() throws SQLException{	
		conn.close();
	}

	public PreparedStatement getPreparedStatement(String query)
			throws SQLException {
		return conn.prepareStatement(query);
	}

	public void upload(String remoteTable,String remoteUrl,String remoteProperties, String where) {
		// TODO
		
	}		

}