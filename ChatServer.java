package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	public static void main(String[] args) {
		// 是否成功启动服务端
		boolean isStart = false;
		// 服务端socket
		ServerSocket ss = null;
		// 客户端socket
		Socket socket = null;
		// 服务端读取客户端数据输入流
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		try {
			// 启动服务器
			ss = new ServerSocket(8189);
		} catch (BindException e) {
			System.out.println("端口已在使用中");
			// 关闭程序
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			isStart = true;
			while (isStart) {
				boolean isConnect = false;
				// 启动监听
				socket = ss.accept();
				System.out.println("一个客户端已连接");
				isConnect = true;
				while (isConnect) {
					// 获取客户端输入流
					dataInputStream = new DataInputStream(socket.getInputStream());
					// 读取客户端传递的数据
					String message = dataInputStream.readUTF();
					System.out.println("客户端说：" + message);
					
					// 向客户端发送信息的DataOutputStream  
	                DataOutputStream out = new DataOutputStream(socket  
	                        .getOutputStream());  
	                // 获取控制台输入的Scanner  
	                Scanner scanner = new Scanner(System.in);
	                System.out.println("服务器请输入：");
	                String send = scanner.nextLine();  
                    // 把服务器端的输入发给客户端  
                    out.writeUTF("服务器：" + send);
                    out.flush();

				}
				
			}
		} catch (EOFException e) {
			System.out.println("client closed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭相关资源
			try {
				dataInputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}