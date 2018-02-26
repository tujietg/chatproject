	package com.teng.chat10;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @version 1.0
 * @author sunboteng
 */
public class Client10 {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("192.168.4.10", 80);

		OutputStream outputStream = socket.getOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

		outputStreamWriter.write("hello");

		outputStreamWriter.flush();

		outputStreamWriter.close();

		Thread.currentThread().sleep(6000);

	}

}
