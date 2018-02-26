package com.teng.chat13;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * @author sunboteng
 */
public class Window {

	public static void main(String[] args) {

		final JFrame jframe = new JFrame("WeChat");
		// 关闭窗口同时结束程序
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 窗口大小
		jframe.setSize(600, 400);
		// 设置窗口的位置
		jframe.setLocation(100, 100);
		// 上半部分
		JPanel north = new JPanel();

		north.setLayout(new GridLayout(1, 8));

		north.add(new JLabel("服务器IP:"));

		final JTextField serverIPTextField = new JTextField("127.0.0.1");

		north.add(serverIPTextField);

		north.add(new JLabel("端口:"));

		final JTextField serverPortTextField = new JTextField("100");

		north.add(serverPortTextField);

		north.add(new JLabel("用户名:"));

		final JTextField userNameTextField = new JTextField("张三");

		north.add(userNameTextField);

		final JButton connectButton = new JButton("连接");

		north.add(connectButton);

		final JButton CutoffButton = new JButton("断开");

		north.add(CutoffButton);

		north.setBorder(new TitledBorder("连接信息"));

		jframe.add(north, BorderLayout.NORTH);

		// 中间部分
		JSplitPane center = new JSplitPane();
		center.setDividerLocation(100);

		// 中左部分
		final DefaultListModel<String> defaultListModel = new DefaultListModel<>();

		final JList<String> jList = new JList<>(defaultListModel);

		JScrollPane left = new JScrollPane(jList);

		left.setBorder(new TitledBorder("在线用户"));

		center.setLeftComponent(left);

		jframe.add(center, BorderLayout.CENTER);
		// 中右部分
		final JTextArea jTextArea = new JTextArea();

		jTextArea.setEditable(false);

		jTextArea.setForeground(Color.blue);

		JScrollPane right = new JScrollPane(jTextArea);

		right.setBorder(new TitledBorder("聊天记录"));

		center.setRightComponent(right);

		jframe.add(center, BorderLayout.CENTER);

		// 下部分
		JPanel south = new JPanel();

		south.setLayout(new BorderLayout());

		final JTextField sendTextField = new JTextField();

		south.add(sendTextField, BorderLayout.CENTER);

		final JButton sendButton = new JButton("发送");

		south.add(sendButton, BorderLayout.EAST);

		south.setBorder(new TitledBorder("发送信息"));

		jframe.add(south, BorderLayout.SOUTH);

		final Client13 clientSocket = new Client13(jTextArea, defaultListModel);

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						int serverPort = Integer.parseInt(serverPortTextField
								.getText().trim());
						if (65535 < serverPort || serverPort < 0) {
							throw new Exception("端口号必须为0~65535");
						}
					} catch (NumberFormatException e2) {
						throw new Exception("请输入正确的端口号");
					}
					String serverIP = serverIPTextField.getText().trim();
					if (!serverIP.matches("^[0-9\\.]+$")) {
						throw new Exception("请输入正确的服务器地址");
					}
					String userName = userNameTextField.getText().trim();
					if (!userName.matches("^[\u4e00-\u9fa5_a-zA-Z0-9]+$")) {
						throw new Exception("请输入正确的用户名");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(jframe, e1.getMessage(),
							"错误", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					CutoffButton.setEnabled(false);
					clientSocket.connect(serverIPTextField.getText().trim(),
							Integer.parseInt(serverPortTextField.getText()));
					JOptionPane.showMessageDialog(jframe, "连接成功");
					sendButton.setEnabled(true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(jframe, "连接失败");
					return;
				}
				connectButton.setEnabled(false);
				CutoffButton.setEnabled(true);
				Thread threads = new Thread(clientSocket);
				threads.start();
				try {
					clientSocket.login(userNameTextField.getText().trim());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String message = sendTextField.getText().trim();

				ArrayList<String> list = (ArrayList<String>) jList
						.getSelectedValuesList();
				if (list.size() == defaultListModel.size() || list.size() == 0) {
					try {
						clientSocket.sendMessage("All:" + message);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					for (int i = 0; i < list.size(); i++) {
						try {
							clientSocket.sendMessage(list.get(i) + ":"
									+ message + "\n");
							jTextArea.append("你对" + list.get(i) + message);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});
		CutoffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clientSocket.sendMessage("断开：" + userNameTextField);
					connectButton.setEnabled(true);
					CutoffButton.setEnabled(false);
					sendButton.setEnabled(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		jframe.setVisible(true);

	}
}
