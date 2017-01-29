package socket;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import entity.File;

import util.CommendTranser;

import entity.*;

public class Client {
	Scanner input=new Scanner(System.in);
	private Socket socket=null;
	
	public void showMenu(){
		System.out.println("******��ӭʹ��imooc�ϴ���*******");
		System.out.println("1 �û���¼  \n2 �û�ע�� \n3 �˳�");
		System.out.println("***************************");
		System.out.println("��ѡ��");
		int choose=input.nextInt();
		switch(choose){
		case 1:
			showLogin();
			break;
		case 2:
			showZhuce();
			break;
		case 3:
			System.out.println("�ϸ��ټ���");
			System.exit(0);
		default:
			System.out.println("��������");
			System.exit(0);
		}
		
	}
	public void showLogin(){
		/*
		 * Ҫ��¼�ˣ���Ҫ�½�һ���û����󣬸���������û��������ʼ������û���������ȥ����������������������������ݿ�
		 * �ж��Ƿ�������û��Ĵ���
		 */
		Users user=new Users();
		System.out.println("��ӭʹ�õ�¼��");
		CommendTranser transer =new CommendTranser();
		int count=0;
		while(true){
			//ֻ�ܵ�¼3�Σ����˲���ֱ���˳�
			if(count>3){
				System.out.println("���Ѿ���������ʧ�ܣ��ټ�");
				System.exit(0);
			}
			System.out.println("�������û���");
			user.setUsername(input.next());
			System.out.println("����������");
			user.setPassword(input.next());
			transer.setCmd("login");
			transer.setData(user);
			count++;
			try {//���ӷ�����
				socket =new Socket("localhost",8888);
				sendData(transer);//�����������transer
				transer=getData();
				System.out.println("   " + transer.getResult());
				System.out.println("***********************");
				if (transer.isFlag()) {
					break;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				clossAll();
			}
		}
		//��¼�ɹ������Խ����ϴ��ļ���
		showUploadFile();
	}
	
	public void showZhuce(){
		System.out.println("��ӭʹ��ע��");
		
		Users user=new Users();
		CommendTranser transer=new CommendTranser();
		
		while(true){
			System.out.println("�������û�����");
			user.setUsername(input.next());
			System.out.println("���������룺");
			String p1=input.next();
			System.out.println("���ٴ��������룺");
			String p2=input.next();
			if(p1.equals(p2)){
				user.setPassword(p1);
			}else{
				System.out.println("������������벻һ�£�");
				System.out.println("**************");
				showZhuce();
			}
			transer.setCmd("zhuce");
			transer.setData(user);
			try {
				socket =new Socket("localhost",8888);
				sendData(transer);//�����ݿⷢ��������Ϣ
				transer=getData();//���ݿ�ִ����������    ������Ϣ
				System.out.println("  " + transer.getResult());
				System.out.println("***********************");
				if (transer.isFlag()) {
					break;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				clossAll();
			}
		}
		//ע��ɹ������Խ����ϴ��ļ���
		showUploadFile();
	}
	
	public void showUploadFile(){
		//�ͻ��������ϴ��ļ�
		System.out.println("�������ϴ��ľ���·�� �磺 (e://imooc//dog.jpg)");
		String path=input.next();
		File file = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		String fname = path.substring(path.lastIndexOf("/") + 1);//��ȡ�ļ���
		try {
			fis=new FileInputStream(path);//�Ը������ļ�����·������������
			//�½�һ���ֽ����飬��С���ļ��ֽ�����ͬ
			byte[] fcontent=new byte[fis.available()];//fis.available()���ص���int��fis�����Զ��������ֽ�
			bis=new BufferedInputStream(fis);
			bis.read(fcontent);//���ļ�����д���ֽ����鱣��
			file=new File(fname,fcontent);//��������ļ������½�File����
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//���½���File��������CommendTranser���������������
		CommendTranser transer=new CommendTranser();
		transer.setCmd("uploadFile");
		transer.setData(file);
		
		try {
			socket=new Socket("localhost",8888);
			sendData(transer);//������� 
			transer=getData();//���ؽ��
			System.out.println(transer.getResult());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			clossAll();
		}
	}
	
	//�ͻ��˵����������CommendTranse�������������
	public void sendData(CommendTranser transer){
		try {
			ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(transer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�ͻ��˵��������������������������CommendTranse�������ͻ���
	public CommendTranser getData(){
		try {
			ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
			CommendTranser transer=(CommendTranser)ois.readObject();
			return transer;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void clossAll(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
