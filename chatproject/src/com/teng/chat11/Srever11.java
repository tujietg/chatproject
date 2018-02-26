package com.teng.chat11;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @version 1.1
 * @author sunboteng
 */
public class Srever11 {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		// 启动服务器
		ServerSocket serverSocket = new ServerSocket(80);

		System.out.println("正在等待连接......");

		// 等待客户端的连接
		Socket socket = serverSocket.accept();
		System.out.println("客户端连接成功......");
		// 接受客服端的数据(从字节流转为字符流)
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));

		// 回复（输出流）
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				socket.getOutputStream());

		while (true) {
			System.out.println(bufferedReader.readLine());
			System.out.println("请输入你要回复客户端的信息：");
			String str = scanner.nextLine();
			outputStreamWriter.write(str + "\n");
			outputStreamWriter.flush();
		}
	}
}
