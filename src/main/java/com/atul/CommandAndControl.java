package com.atul;

import com.atul.db.DBController;

public class CommandAndControl {
	private static final int EXECUTE = 0;
	private static final int EXECUTE_QUERY=1;	
	private static final int UPLOAD=2;
	
	private static final String DELIM="##";
	
	private DBController controller;
	
	public CommandAndControl(DBController controller){
		this.controller=controller;
	}
	
	/**
	 * First char indicates the command.
	 * 
	 * @param command
	 */
	public void process(String command){
		try{
			switch(getCommand(command)){
				case EXECUTE:
					controller.execute(command.substring(1));
					break;
				case EXECUTE_QUERY:
					int count = controller.executeQuery(command.substring(1));
					System.out.println("Collected records : " + count);
					break;
				default:
					System.out.println("Failed to identify command " + command);
					break;				
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
	}

	private int getCommand(String command){
		try{
			return Integer.parseInt(command.substring(0,1));
		}catch(Exception e){
			System.out.println("Unknown command received");
		}
		return -1;
	}
	
	public static String execute(String sql){
		return EXECUTE+sql;
	}
	
	public static String executeQuery(String sql){
		return EXECUTE_QUERY+sql;
	}
	
	public static String upload(String remoteTable,String remoteUrl,String remoteProperties, String where){
		StringBuilder b = new StringBuilder();
		return b.append(UPLOAD).append(DELIM).append(remoteTable).append(DELIM).append(remoteUrl)
				.append(remoteProperties).append(DELIM).append(where).toString();
	}
}
