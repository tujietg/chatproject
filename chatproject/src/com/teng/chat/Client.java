package com.teng.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

/**
 * @author sunboteng
 */

public class Client implements Runnable {

	private Socket socket = null;

	private BufferedReader reader = null;

	private OutputStreamWriter writer = null;

	private JTextArea jTextArea = null;

	private DefaultListModel defaultListModel = null;

	public ClientThread_Socket(JTextArea jTextArea,
			DefaultListModel defaultListModel) {

		this.jTextArea = jTextArea;
		this.defaultListModel = defaultListModel;
	}

	public void connect(String ip, int port) {
		try {
			this.socket = new Socket(ip, port);
			this.writer = new OutputStreamWriter(socket.getOutputStream());
			this.reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void run() {
		String line = null;
		String[] fields = null;
		boolean flag = true;
		while (flag) {
			try {
				line = reader.readLine();
				System.out.println(line);
				fields = line.split(":");
				if ("login".equals(fields[0])) {
					jTextArea.append(fields[1]);
				} else if ("addUser".equals(fields[0])) {
					defaultListModel.addElement(fields[1]);
				} else {
					jTextArea.append(fields[0] + "∂‘Œ“Àµ:" + fields[1] + "\n");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String message) throws Exception {
		writer.write(message + "\n");
		writer.flush();
	}

	public void login(String userName) throws Exception {
		sendMessage("login:" + userName);
	}

	public void close() throws IOException {
		return;
	}
}