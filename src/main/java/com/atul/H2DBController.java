package com.atul;

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
public class H2DBController implements DBController {

	private Connection conn;
	private File data;
	
	public H2DBController(File data){
		this.data=data;
	}
	
	public void initConnection() throws SQLException {
		try{
			Class.forName("org.h2.Driver");
		}catch(Exception e){
			System.out.println("No suitable driver");
			e.printStackTrace();
			System.exit(0);
		}
        conn = DriverManager.getConnection("jdbc:h2:"+data.getAbsolutePath()+ ";LOG=0;UNDO_LOG=0;LOCK_MODE=0;");
	}

	public void execute(String query) throws SQLException {
		Statement st = conn.createStatement();
		st.execute(query);
		st.close();
	}

	public int executeQuery(String query) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from users");
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

}