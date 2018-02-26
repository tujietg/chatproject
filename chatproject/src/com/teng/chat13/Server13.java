package com.teng.chat13;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @version 1.3
 * @author sunboteng
 */
public class Server13 {

	private static Logger logger = Logger.getLogger(Server13.class);

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(100);

		logger.info("正在等待连接。。。。。。。。");
		HashMap<String, ServerThread> serverThreads = new HashMap<>();

		int i = 1;
		while (true) {
			Socket socket = serverSocket.accept();

			ServerThread serverThread = new ServerThread("" + i, socket,
					serverThreads);
			serverThreads.put("" + i, serverThread);

			i = i + 1;

			serverThread.start();
		}
	}

}

class ServerThread extends Thread {

	private static Logger logger = Logger.getLogger(ServerThread.class);

	private Socket socket = null;

	private BufferedReader reader = null;

	private OutputStreamWriter writer = null;

	private HashMap<String, ServerThread> serverThreads = null;

	private String name = null;

	public ServerThread(String name, Socket socket,
			HashMap<String, ServerThread> serverThreads) {
		this.name = name;

		this.socket = socket;

		this.serverThreads = serverThreads;

		try {
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new OutputStreamWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) throws Exception {
		writer.write(message + "\n");
		writer.flush();
	}

	public void run() {
		String remoteIP = socket.getInetAddress().getHostAddress();
		String line = null;
		String[] fields = null;
		while (true) {
			try {
				line = reader.readLine();

				fields = line.split(":");

				ServerThread serverThread = null;
				if ("login".equals(fields[0])) {
					serverThread = serverThreads.remove(name);
					serverThreads.put(fields[1], serverThread);
					name = fields[1];
					serverThread.sendMessage("login:登录成功");

					Collection<ServerThread> sockets = serverThreads.values();
					for (ServerThread serverThreadTemp : sockets) {
						if (serverThreadTemp.equals(Thread.currentThread())) {
							for (String userName1 : serverThreads.keySet()) {
								serverThreadTemp.sendMessage("addUser:"
										+ userName1);
							}
						} else {
							serverThreadTemp.sendMessage("addUser:" + name);
						}
					}
				} else if (fields[0].equals("断开")) {
					serverThreads.remove(fields[1]);
					for (ServerThread serverThreaddk : serverThreads.values()) {
						serverThreaddk.sendMessage("断开" + fields[1]);
					}

				} else if (fields[0].equals("All")) {

					for (ServerThread group : serverThreads.values()) {
						group.sendMessage(serverThreads.keySet() + ":"
								+ fields[1]);

					}
				} else {
					serverThread = serverThreads.get(fields[0]);
					serverThread.sendMessage(name + ":" + fields[1]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info(name + " 接收到<" + remoteIP + ">数据：" + line);
		}

	}

}
