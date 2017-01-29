package socket;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import entity.*;
import entity.File;

import service.*;
import util.CommendTranser;

//���������ÿ���ͻ��˽��е�������

public class SocketThread extends Thread{
	private Socket socket=null;
	private ObjectInputStream ois=null;//����������
	private ObjectOutputStream oos=null;//���������
	private UsersService us=new UsersService();//�û�ҵ�����
	private FileService fs=new FileService();//�ļ�ҵ�����
	//ͨ�����췽��,��ʼ��socket
	public SocketThread(Socket socket){
		this.socket=socket;
	}
	
	public void run(){
		try {
			ois=new ObjectInputStream(socket.getInputStream());//�ͻ��˴���������
			oos=new ObjectOutputStream(socket.getOutputStream());//��ͻ��˴���ȥ����
			
			//ִ�пͻ���Ҫ��Ĳ���ת����CommendTranser���Ͷ���
			CommendTranser transer=(CommendTranser)ois.readObject();
			//���������ִ�����˵���Ϣ���ظ��ͻ���
			transer=execute(transer);
			
			oos.writeObject(transer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public CommendTranser execute(CommendTranser transer) throws SQLException{
		String cmd=transer.getCmd();//�õ�Ҫ���е�����
		if(cmd.equals("login")){
			//�ͻ��������¼
			Users user=(Users)transer.getData();//�½�һ���û�
			boolean flag=us.Denglu(user);
			transer.setFlag(flag);
			if(flag){
				transer.setResult("��¼�ɹ���");
			}else{
				transer.setResult("�û��������벻��ȷ��");
			}
		}
		
		else if(cmd.equals("zhuce")){
			//�ͻ�������ע��
			Users user=(Users)transer.getData();//�½�һ���û�
			us.zhuce(user);//����UserSeriver����ע��
			boolean flag=us.Denglu(user);//��ע���˵���һ���ܲ��ܵ�¼���ܵĻ�����ע��ɹ�
			transer.setFlag(flag);
			if(flag){
				transer.setResult("ע��ɹ���");
			}else{
				transer.setResult("ע��ʧ�ܣ�δ֪ԭ�򡣱�Ǹ");
			}
		}
		
		else if(cmd.equals("uploadFile")){
			//�û������ϴ��ļ�
			File file=(File)transer.getData();//���ͻ��˴������Ķ������ݽ����½�һ���ļ�����
			fs.saveFile(file);
			transer.setResult("�ϴ��ɹ���");
		}
		
		return transer;
	}
	
}
