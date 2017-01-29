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
		System.out.println("******欢迎使用imooc上传器*******");
		System.out.println("1 用户登录  \n2 用户注册 \n3 退出");
		System.out.println("***************************");
		System.out.println("请选择：");
		int choose=input.nextInt();
		switch(choose){
		case 1:
			showLogin();
			break;
		case 2:
			showZhuce();
			break;
		case 3:
			System.out.println("老哥再见！");
			System.exit(0);
		default:
			System.out.println("输入有误！");
			System.exit(0);
		}
		
	}
	public void showLogin(){
		/*
		 * 要登录了，就要新建一个用户对象，根据输入的用户名密码初始化这个用户对象，再拿去传输给服务器，服务器再连接数据库
		 * 判断是否有这个用户的存在
		 */
		Users user=new Users();
		System.out.println("欢迎使用登录：");
		CommendTranser transer =new CommendTranser();
		int count=0;
		while(true){
			//只能登录3次，多了不给直接退出
			if(count>3){
				System.out.println("您已经三次输入失败，再见");
				System.exit(0);
			}
			System.out.println("请输入用户名");
			user.setUsername(input.next());
			System.out.println("请输入密码");
			user.setPassword(input.next());
			transer.setCmd("login");
			transer.setData(user);
			count++;
			try {//连接服务器
				socket =new Socket("localhost",8888);
				sendData(transer);//向服务器传输transer
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
		//登录成功，可以进行上传文件了
		showUploadFile();
	}
	
	public void showZhuce(){
		System.out.println("欢迎使用注册");
		
		Users user=new Users();
		CommendTranser transer=new CommendTranser();
		
		while(true){
			System.out.println("请输入用户名：");
			user.setUsername(input.next());
			System.out.println("请输入密码：");
			String p1=input.next();
			System.out.println("请再次输入密码：");
			String p2=input.next();
			if(p1.equals(p2)){
				user.setPassword(p1);
			}else{
				System.out.println("两次输入的密码不一致！");
				System.out.println("**************");
				showZhuce();
			}
			transer.setCmd("zhuce");
			transer.setData(user);
			try {
				socket =new Socket("localhost",8888);
				sendData(transer);//向数据库发送请求信息
				transer=getData();//数据库执行完了请求    反馈信息
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
		//注册成功，可以进行上传文件了
		showUploadFile();
	}
	
	public void showUploadFile(){
		//客户端请求上传文件
		System.out.println("请输入上传的绝对路径 如： (e://imooc//dog.jpg)");
		String path=input.next();
		File file = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		String fname = path.substring(path.lastIndexOf("/") + 1);//获取文件名
		try {
			fis=new FileInputStream(path);//对给出的文件绝对路径建立输入流
			//新建一个字节数组，大小和文件字节数相同
			byte[] fcontent=new byte[fis.available()];//fis.available()返回的是int的fis流可以读进来的字节
			bis=new BufferedInputStream(fis);
			bis.read(fcontent);//将文件内容写入字节数组保存
			file=new File(fname,fcontent);//将读入的文件进行新建File对象
			
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
		//将新建的File对象打包成CommendTranser对象向服务器传输
		CommendTranser transer=new CommendTranser();
		transer.setCmd("uploadFile");
		transer.setData(file);
		
		try {
			socket=new Socket("localhost",8888);
			sendData(transer);//传输对象 
			transer=getData();//返回结果
			System.out.println(transer.getResult());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			clossAll();
		}
	}
	
	//客户端的输出流，将CommendTranse对象传输给服务器
	public void sendData(CommendTranser transer){
		try {
			ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(transer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//客户端的输入流，将服务器传输回来的CommendTranse对象读入客户端
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
