package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientExample {
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		String host = "localhost";

		socket = new Socket(host, 4444);

		File file = new File("./input/1MB.txt");
		// Get the size of the file
		long length = file.length();
		long startTime = System.currentTimeMillis();
		byte[] bytes = new byte[16 * 1024];
		InputStream in = new FileInputStream(file);
		OutputStream out = socket.getOutputStream();

		int count;
		while ((count = in.read(bytes)) > 0) {
			out.write(bytes, 0, count);
		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println(time);
		out.close();
		in.close();
		socket.close();
	}
}