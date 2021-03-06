package com.atul;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.atul.db.DBController;
import com.atul.db.H2DBController;
import com.atul.misc.RandomUserGenerator;

/**

table users { seqno, userid, fname, mname, lname, accountid, address1, pin, city, state, country, email, phone}
Test Query: select pin,fname,lname,mname,count(1) from users group by pin,fname,lname,mname order by count desc
		Took 26 seconds on 5 lack records.

dropTable took 316ms
createTable took 2ms
insertRecord took 11 seconds
readRecords took 15446ms to read 500000 records
updateRecord took 2516ms
Join query took 12701ms
Took 42334 milliseconds to complete

 
 * @author Atul
 *
 */
public class TestH2DBController {	
	public static void main(String[] args) throws Exception {
		DBController controller = new H2DBController(new File("target","h2temp"));
		controller.initConnection();
		
		long start = System.currentTimeMillis();
		try{
			dropTable(controller);
			createTable(controller); 			
			//truncateTable(controller);
			insertRecord(controller,500000);
			readRecords(controller);
			updateRecord(controller);
			joinQuery(controller);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Took " + (System.currentTimeMillis() - start) + " milliseconds to complete");
	}
	
	private static void insertRecord(DBController controller, int count) throws SQLException {
		String sql = "insert into users values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		long start = System.currentTimeMillis();

		PreparedStatement stmt = controller.getPreparedStatement(sql);
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
			if (stmt != null){
				stmt.executeBatch();
				stmt.close();
			}
		}
		System.out.println("insertRecord took " + (System.currentTimeMillis() - start)/ 1000 + " seconds");
	}
		
	private static void updateRecord(DBController controller) throws SQLException {
		long start = System.currentTimeMillis();
		controller.execute("update users set mname='mname1' where mname='mname'");
		System.out.println("updateRecord took " + (System.currentTimeMillis() - start) + "ms");
	}
	
	private static void readRecords(DBController controller) throws SQLException {
		long start = System.currentTimeMillis();
		int count = controller.executeQuery("select * from users");
		System.out.println("readRecords took " + (System.currentTimeMillis() - start) + "ms to read " + count + " records");
	}
	
	private static void truncateTable(DBController controller) throws SQLException {
		long start = System.currentTimeMillis();
		controller.execute("truncate table users");
		System.out.println("truncateTable took " + (System.currentTimeMillis() - start) + "ms");
	}

	
	private static void createTable(DBController controller) throws SQLException {
		long start = System.currentTimeMillis(); 
		controller.execute("CREATE TABLE users (seqno integer PRIMARY KEY,userid text, fname text, mname text, lname text, accountid text, address1 text, pin text, city text, state text, country text, email text,phone text );");
		System.out.println("createTable took " + (System.currentTimeMillis() - start) + "ms");
	}
	
	private static void dropTable(DBController controller) throws SQLException {
		long start = System.currentTimeMillis();
		try{
			controller.execute("drop TABLE users");
		}catch(Exception e){
			System.out.println("Exception while dropping table. Maybe first time? :" + e.getLocalizedMessage());
		}
		System.out.println("dropTable took " + (System.currentTimeMillis() - start) + "ms");
	}
	
	private static void joinQuery(DBController controller) throws SQLException {
		long start = System.currentTimeMillis();
		int count = controller.executeQuery("select pin,fname,lname,mname,count(*) count from users group by pin,fname,lname,mname order by count desc");
		System.out.println("Join query took  " + (System.currentTimeMillis() - start) + "ms to read " + count + " records");
	}

}