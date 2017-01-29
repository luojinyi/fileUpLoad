package socket;

import java.io.IOException;
import java.net.*;

//服务器端
public class Server {
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket =new ServerSocket(8888);
		Socket socket=null;
		System.out.println("服务器端启动成功！\n等待客户端的连接......");
		while(true){
			socket =serverSocket.accept();
			System.out.println("客户端连入");
			//为这个客户端分配一个线程
			SocketThread thread =new SocketThread(socket);
			//启动线程
			thread.start();
		}
	}

}
