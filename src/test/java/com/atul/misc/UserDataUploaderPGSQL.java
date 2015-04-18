package com.atul.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

/**

table users { seqno, userid, fname, mname, lname, accountid, address1, pin, city, state, country, email, phone}
Test Query: select pin,fname,lname,mname,count(1) from users group by pin,fname,lname,mname order by count desc
		Took 26 seconds on 5 lack records.

dropTable took 48ms
createTable took 78ms
insertRecord took 3 seconds
readRecords took 300ms to read 50000 records
updateRecord took 685ms
Took 5030 milliseconds to complete
 
 * @author Atul
 *
 */
public class UserDataUploaderPGSQL {


	private static Connection conn;
	
	private static Connection getConnection() throws SQLException {
		String url = "jdbc:postgresql://localhost/pykh";
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","atul");
		return DriverManager.getConnection(url, props);
	}
	
	public static void main(String[] args) throws Exception {
		conn = getConnection();	
		long start = System.currentTimeMillis();
		try{
			dropTable();
			createTable(); 			
			//truncateTable();
			insertRecord(500000);
			readRecords();
			updateRecord();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		System.out.println("Took " + (System.currentTimeMillis() - start) + " milliseconds to complete");
	}
	
	private static void insertRecord(int count) throws SQLException {
		String sql = "insert into users values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		long start = System.currentTimeMillis();

		PreparedStatement stmt = conn.prepareStatement(sql);
		try{
			for (int i = 0; i < count; i++) {
				stmt.setInt(1, i);

				String fname = RandomUserGenerator.getName();
				String lname = RandomUserGenerator.getName();
				String userId = RandomUserGenerator.getName();
				
				stmt.setString(2, userId);
				stmt.setString(3, fname);
				stmt.setString(4, RandomUserGenerator.getName());
				stmt.setString(5, lname);
				stmt.setString(6, i+"");
				stmt.setString(7, "");
				stmt.setString(8, RandomUserGenerator.getPin());
				stmt.setString(9, "Bangalore");
				stmt.setString(10, "Karnataka");
				stmt.setString(11, "India");
				stmt.setString(12, userId+"@gmail.com");							
				stmt.setString(13, RandomUserGenerator.getPhone());

				stmt.addBatch();
	
				if (i % 10000 == 0) {
					stmt.executeBatch();
					System.out.println("At " + new Date()+ ", Inserted " + i + " records");
				}
			}
		} finally {
			if (stmt != null)
				stmt.executeBatch();
				stmt.close();
			if (conn != null) {
				if (!conn.getAutoCommit()) {
					conn.commit();
				}
				//conn.close();
			}
		}
		System.out.println("insertRecord took " + (System.currentTimeMillis() - start)/ 1000 + " seconds");
	}
		
	private static void updateRecord() throws SQLException {
		long start = System.currentTimeMillis();
		Statement st = conn.createStatement();
		st.execute("update users set mname='mname1' where mname='mname'");
		st.close();
		System.out.println("updateRecord took " + (System.currentTimeMillis() - start) + "ms");
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
	
	private static void truncateTable() throws SQLException {
		long start = System.currentTimeMillis();
		Statement st = conn.createStatement();
		st.execute("truncate table users");
		st.close();
		System.out.println("truncateTable took " + (System.currentTimeMillis() - start) + "ms");
	}

	
	private static void createTable() throws SQLException {
		long start = System.currentTimeMillis();
		Statement st = conn.createStatement();
		 
		st.execute("CREATE TABLE users (seqno integer PRIMARY KEY,userid text, fname text, mname text, lname text, accountid text, address1 text, pin text, city text, state text, country text, email text,phone text );");
		st.close();
		System.out.println("createTable took " + (System.currentTimeMillis() - start) + "ms");
	}
	
	private static void dropTable() throws SQLException {
		long start = System.currentTimeMillis();
		try{
			Statement st = conn.createStatement();
			st.execute("drop TABLE users");
			st.close();
		}catch(Exception e){
			System.out.println("Exception while dropping table. Maybe first time? :" + e.getLocalizedMessage());
		}
		System.out.println("dropTable took " + (System.currentTimeMillis() - start) + "ms");
	}

}