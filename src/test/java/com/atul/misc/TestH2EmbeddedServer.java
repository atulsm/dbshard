package com.atul.misc;

import org.h2.tools.Server;

public class TestH2EmbeddedServer {

	public static void main(String[] args) throws Exception {
		// start the TCP Server
		Server server = Server.createTcpServer("-tcpPort", "9999", "-tcpAllowOthers","-baseDir","target/data").start();
		
		Thread.sleep(10000);
		
		// stop the TCP Server
		server.stop();

	}

}
