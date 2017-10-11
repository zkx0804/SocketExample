package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class BackupServer extends Thread {
	private ServerSocket serverSocket;

	public BackupServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(100000);
	}

	// mvn exec:java -Dexec.mainClass="main.SeverExample" -Dexec.args="6066"
	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();

				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				InputStream infromclient = server.getInputStream();

				System.out.println("File received by server.");
				OutputStream outToClient = server.getOutputStream();
				// DataOutputStream out = new
				// DataOutputStream(server.getOutputStream());

				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");

				server.close();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		try {
			Thread t = new BackupServer(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}