package com.atul.socket;

import java.io.PrintWriter;
import java.net.Socket;

public class SocketWriter {
	Socket sock;
	PrintWriter wr;
	public SocketWriter(Socket sock){
		this.sock = sock;
		try{
			wr = new PrintWriter(sock.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String data) {
		wr.write(data+"\r\n");
		wr.flush();		
	}
	
	
	public void close() {
		try{
			wr.close();
			sock.close();
		}catch(Exception e){}				
	}
}
