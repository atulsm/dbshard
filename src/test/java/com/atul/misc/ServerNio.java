package com.atul.misc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Server will read a line and close socket
 * 
 * @author Atul
 *
 */
public class ServerNio {
	private static int port = 8013;

	// Charset and encoder for US-ASCII
	private static Charset charset = Charset.forName("US-ASCII");
	private static CharsetEncoder encoder = charset.newEncoder();

	// Direct byte buffer for writing
	private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

	private static ServerSocketChannel setup() throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress(port);
		ssc.socket().bind(isa);
		return ssc;
	}

	// Service the next request to come in on the given channel
	//
	private static void serve(ServerSocketChannel ssc) throws IOException {
		SocketChannel sc = ssc.accept();
		try {
			String now = new Date().toString();
			sc.write(encoder.encode(CharBuffer.wrap(now + "\r\n")));
			System.out.println(sc.socket().getInetAddress() + " : " + now);
			sc.close();
		} finally {
			// Make sure we close the channel (and hence the socket)
			sc.close();
		}
	}

	public static void main(String[] args) throws IOException {

		ServerSocketChannel ssc = setup();
		for (;;)
			serve(ssc);

	}

}