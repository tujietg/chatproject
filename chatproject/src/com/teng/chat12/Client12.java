package com.teng.chat12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @version 1.2
 * @author sunboteng
 */
public class Client12 {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("192.168.4.10", 80);

		ClientThread_Socket clientThread_Socket = new ClientThread_Socket(
				socket);

		clientThread_Socket.start();

		Scanner scanner = new Scanner(System.in);

		String line = null;
		while (true) {
			System.out.println("请输入发送的消息：");

			line = scanner.nextLine();
			clientThread_Socket.sendMessage(line);
		}

	}

}

// 该线程处理服务器端发送过的数据
class ClientThread_Socket extends Thread {

	private Socket socket = null;

	private BufferedReader reader = null;

	private OutputStreamWriter writer = null;

	public ClientThread_Socket(Socket socket) {
		try {
			this.writer = new OutputStreamWriter(socket.getOutputStream());
			this.reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.socket = socket;
	}

	public void run() {
		while (true) {
			try {
				System.out.println("\t" + reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String message) throws Exception {
		writer.write(message + "\n");
		writer.flush();
	}
}
