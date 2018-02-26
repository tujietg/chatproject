package com.teng.chat12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @version 1.2
 * @author sunboteng
 */
public class Server12 {

	private static Logger logger = Logger.getLogger(Server12.class);

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(80);

		logger.info("正在等待连接。。。。。。。。");

		// 保存当前连接到服务端的所有客户端，每个客户端通过线程来处理
		// 该Map主要用来让处理客户端消息的线程可以找到彼此
		HashMap<String, ServerThread> sockets = new HashMap<>();

		int i = 1;
		// 每有一个客户端连接，就启动一个线程去处理，而主线程只负责监听连接
		while (true) {
			Socket socket = serverSocket.accept();

			ServerThread serverThread = new ServerThread("" + i, socket,
					sockets);

			logger.info(i + "  客户端<" + socket.getInetAddress().getHostAddress()
					+ ">已经连接");

			sockets.put("" + i, serverThread);

			i = i + 1;

			serverThread.start();
		}
	}

}

// 在服务器端处理对应的客户端，每个客户端都会有一个该类的实例
class ServerThread extends Thread {

	private static Logger logger = Logger.getLogger(ServerThread.class);

	private Socket socket = null;

	private BufferedReader reader = null;

	private OutputStreamWriter writer = null;

	private HashMap<String, ServerThread> sockets = null;

	private String name = null;

	public ServerThread(String name, Socket socket,
			HashMap<String, ServerThread> sockets) {
		this.name = name;
		this.socket = socket;
		this.sockets = sockets;

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

				ServerThread serverThread = sockets.get(fields[0]);

				serverThread.sendMessage(fields[1]);

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info(name + " 接收到<" + remoteIP + ">数据：" + line);
		}

	}

}