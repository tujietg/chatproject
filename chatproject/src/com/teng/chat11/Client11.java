package com.teng.chat11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * @version 1.1
 * @author sunboteng
 */
public class Client11 {
	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("192.168.4.10", 80);

		Scanner scanner = new Scanner(System.in);

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				socket.getOutputStream());

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));

		while (true) {
			System.out.println("请输入你要发送给服务器端的消息：");

			String str = scanner.nextLine();
			outputStreamWriter.write(str + "\n");
			// outputStreamWriter.close();
			outputStreamWriter.flush();
			System.out.println(bufferedReader.readLine());
		}

	}
}
