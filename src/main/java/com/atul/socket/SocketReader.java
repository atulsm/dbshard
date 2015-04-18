package com.atul.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.atul.CommandAndControl;

public class SocketReader extends Thread {
	Socket sock;
	CommandAndControl cc;
	
	public SocketReader(Socket sock, CommandAndControl cc){
		this.sock = sock;
		this.cc = cc;
	}
	
	@Override
	public void run() {
		BufferedReader br=null;
		try{
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String in = br.readLine();
			while(in != null){
				System.out.println("Received: " + in);
				cc.process(in);
				in = br.readLine();				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				br.close();
			}catch(Exception e){}
		}
	}
	
	
	public void close() {
		try{
			sock.close();
		}catch(Exception e){}				
	}
}
