package com.mycompany.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketLiveServerWorker implements Runnable {

//	private static SocketLiveServerWorker instatnce = new SocketLiveServerWorker();

	private ServerSocket serverSocket;

	private List<Socket> clients;

	private int port;
//
//	public static SocketLiveServerWorker instatnce() {
//		return instatnce;
//	}

	public SocketLiveServerWorker(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		clients = new ArrayList<Socket>();
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started on port: "+ port);
			while(true) {
				Socket clientSocket = serverSocket.accept();
				addClient(clientSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void push(String message) {
		for(Socket client : clients) {
			try {
				PrintWriter pw = new PrintWriter(client.getOutputStream());
				pw.write(message);
				pw.write("\n");
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
	}

	private void addClient(Socket clientSocket) throws IOException {
		synchronized (clients) {
			for(Socket socket : clients) {
				if(socket.getInetAddress().getHostAddress().equals(clientSocket.getInetAddress().getHostAddress())) {
					System.out.println("Disconnect old client " + clientSocket.getInetAddress().toString());
					socket.close();
					clients.remove(socket);
					break;
				}
			}
			System.out.println("Client connected from " + clientSocket.getInetAddress().toString());
			clients.add(clientSocket);
		}

	}

	public void startWorker() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
