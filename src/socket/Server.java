package socket;

import java.io.IOException;
import java.net.*;

//��������
public class Server {
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket =new ServerSocket(8888);
		Socket socket=null;
		System.out.println("�������������ɹ���\n�ȴ��ͻ��˵�����......");
		while(true){
			socket =serverSocket.accept();
			System.out.println("�ͻ�������");
			//Ϊ����ͻ��˷���һ���߳�
			SocketThread thread =new SocketThread(socket);
			//�����߳�
			thread.start();
		}
	}

}
