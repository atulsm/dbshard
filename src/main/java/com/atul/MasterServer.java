package com.atul;
import java.net.ServerSocket;
import java.net.Socket;

import com.atul.socket.SocketWriter;


public class MasterServer {
	
	private static final String remoteTable = "users";
	private static final String remoteUrl = "jdbc:postgresql://localhost/pykh";
	private static final String remoteProperties = "{user=postgres, password=atul}";

	/**
	 * Server will wait for client connections.
	 * Once connected server will issue commands to clients
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			ServerSocket serv = new ServerSocket(5555);
			Socket client = serv.accept();
			System.out.println("New connection from " + client);
			
			SocketWriter writer = new SocketWriter(client);
			init(writer);
			
			Thread.sleep(10000);
			serv.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}	
	
	
	/**
	 * First get the data from remote table and keep it ready
	 * 
	 * @param writer
	 */
	private static void init(SocketWriter writer){
		writer.write(CommandAndControl.upload(remoteTable,remoteUrl,remoteProperties, " where seqno < 50000"));
	}
}
