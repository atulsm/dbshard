package com.atul.misc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ClientNio {

	// The port we'll actually use
	private static int port = 8013;

	// Charset and decoder for US-ASCII
	private static Charset charset = Charset.forName("US-ASCII");
	private static CharsetDecoder decoder = charset.newDecoder();

	// Direct byte buffer for reading
	private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

	private static void query(String host) throws IOException {
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(host), port);
		SocketChannel sc = null;

		try {

			// Connect
			sc = SocketChannel.open();
			sc.connect(isa);
			System.out.print("Connected to " + isa);

			CharBuffer cb = read(sc);			
			
			System.out.print(cb);
		} finally {
			// Make sure we close the channel (and hence the socket)
			if (sc != null)
				sc.close();
		}
	}
	
	private static CharBuffer read(SocketChannel sc) throws IOException,
			CharacterCodingException {
		dbuf.clear();
		sc.read(dbuf);
		dbuf.flip();
		CharBuffer cb = decoder.decode(dbuf);
		return cb;
	}

	public static void main(String[] args) {
		try {
			query("127.0.0.1");
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

}