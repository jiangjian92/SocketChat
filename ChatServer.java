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
		// �Ƿ�ɹ����������
		boolean isStart = false;
		// �����socket
		ServerSocket ss = null;
		// �ͻ���socket
		Socket socket = null;
		// ����˶�ȡ�ͻ�������������
		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		try {
			// ����������
			ss = new ServerSocket(8189);
		} catch (BindException e) {
			System.out.println("�˿�����ʹ����");
			// �رճ���
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			isStart = true;
			while (isStart) {
				boolean isConnect = false;
				// ��������
				socket = ss.accept();
				System.out.println("һ���ͻ���������");
				isConnect = true;
				while (isConnect) {
					// ��ȡ�ͻ���������
					dataInputStream = new DataInputStream(socket.getInputStream());
					// ��ȡ�ͻ��˴��ݵ�����
					String message = dataInputStream.readUTF();
					System.out.println("�ͻ���˵��" + message);
					
					// ��ͻ��˷�����Ϣ��DataOutputStream  
	                DataOutputStream out = new DataOutputStream(socket  
	                        .getOutputStream());  
	                // ��ȡ����̨�����Scanner  
	                Scanner scanner = new Scanner(System.in);
	                System.out.println("�����������룺");
	                String send = scanner.nextLine();  
                    // �ѷ������˵����뷢���ͻ���  
                    out.writeUTF("��������" + send);
                    out.flush();

				}
				
			}
		} catch (EOFException e) {
			System.out.println("client closed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ر������Դ
			try {
				dataInputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}