package com.atul.misc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestH2RemoteClient {

	private static Connection conn;
	
	private static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:tcp://localhost:9999/./test");
	}
	
	public static void main(String[] args) throws Exception {
		conn = getConnection();	
		long start = System.currentTimeMillis();
		try{
			readRecords();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		System.out.println("Took " + (System.currentTimeMillis() - start) + " milliseconds to complete");
	}
	
	private static void readRecords() throws SQLException {
		long start = System.currentTimeMillis();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from users");
		int count = 0;
		while (rs.next())
		{
			count++;
		   //System.out.print("Column 1 returned ");
		   //System.out.println(rs.getString(1));
		} rs.close();
		st.close();
		System.out.println("readRecords took " + (System.currentTimeMillis() - start) + "ms to read " + count + " records");
	}

}
