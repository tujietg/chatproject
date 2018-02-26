package com.teng.chat10;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * @version 1.0
 * @author sunboteng
 */
public class Server10 {

	private static Logger logger = Logger.getLogger(Server10.class);

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(80);

		logger.info("正在等待连接。。。。。。。。");
		Socket socket = serverSocket.accept();
		
		logger.info("已经连接：");

		InputStream inputStream = socket.getInputStream();

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		logger.info("接收到的数据：");
		System.out.println(bufferedReader.readLine());

	}

}
