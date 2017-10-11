package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BackupClient {

	public static void main(String[] args) {
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		BufferedReader br = null;
		FileReader fr = null;

		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();

			// Read txt file

			byte[] bytes = new byte[4096];
			File file = new File("./input/1MB.txt");
			System.out.println(file.length());
			InputStream input = new FileInputStream(file);

			long startTime = System.currentTimeMillis();
			int count;
			while ((count = input.read(bytes)) > 0) {
				outToServer.write(bytes, 0, count);
			}

			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			long endTime = System.currentTimeMillis();

			long time = endTime - startTime;
			System.out.println("Server sent back file. Total time: " + (double) (time / 1000) + "seconds");

			outToServer.close();
			in.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
