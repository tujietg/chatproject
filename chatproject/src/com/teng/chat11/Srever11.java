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
		// ����������
		ServerSocket serverSocket = new ServerSocket(80);

		System.out.println("���ڵȴ�����......");

		// �ȴ��ͻ��˵�����
		Socket socket = serverSocket.accept();
		System.out.println("�ͻ������ӳɹ�......");
		// ���ܿͷ��˵�����(���ֽ���תΪ�ַ���)
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));

		// �ظ����������
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				socket.getOutputStream());

		while (true) {
			System.out.println(bufferedReader.readLine());
			System.out.println("��������Ҫ�ظ��ͻ��˵���Ϣ��");
			String str = scanner.nextLine();
			outputStreamWriter.write(str + "\n");
			outputStreamWriter.flush();
		}
	}
}
