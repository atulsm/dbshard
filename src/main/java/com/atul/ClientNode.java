package com.atul;
import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

import com.atul.db.DBController;
import com.atul.db.H2DBController;
import com.atul.socket.SocketReader;


public class ClientNode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Socket sock = new Socket("localhost",5555);
			DBController controller = new H2DBController(new File("target\\data","client"));
			controller.initConnection();
			SocketReader reader1 = new SocketReader(sock,new CommandAndControl(controller));
			reader1.start();		
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
