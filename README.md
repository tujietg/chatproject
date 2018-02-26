chat project
==
项目概要：
---
基于TCP/IP，java简单实现聊天工具。
整个项目采用GBK编码格式。

项目详解：
---
* version1.0：利用Socket 简单实现server and client。
* version1.1：实现server and client之间的实时通信。（按照客户端加入的先后顺序进行编号，server 采用"：" 分割来判断是发给哪个client的消息）。
* version1.2：实现多个client之间的通信（实现方式采用多线程）
每有一个client连接，就启动一个线程去处理，而server的主线程只负责监听连接。同时，client也用到多线程。client的主线程服务监听键盘的输入。client线程负责监听网卡的输入，把服务器端发送过来的数据打印出来。
* version1.3：实现群聊的功能，同时利用swing做了简易的聊天界面。
