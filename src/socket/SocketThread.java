package socket;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import entity.*;
import entity.File;

import service.*;
import util.CommendTranser;

//作用是针对每个客户端进行单独处理

public class SocketThread extends Thread{
	private Socket socket=null;
	private ObjectInputStream ois=null;//对象输入流
	private ObjectOutputStream oos=null;//对象输出流
	private UsersService us=new UsersService();//用户业务对象
	private FileService fs=new FileService();//文件业务对象
	//通过构造方法,初始化socket
	public SocketThread(Socket socket){
		this.socket=socket;
	}
	
	public void run(){
		try {
			ois=new ObjectInputStream(socket.getInputStream());//客户端传进来的流
			oos=new ObjectOutputStream(socket.getOutputStream());//向客户端传出去的流
			
			//执行客户端要求的操作转换成CommendTranser类型对象
			CommendTranser transer=(CommendTranser)ois.readObject();
			//将请求操作执行完了的信息返回给客户端
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
		String cmd=transer.getCmd();//得到要进行的命令
		if(cmd.equals("login")){
			//客户端请求登录
			Users user=(Users)transer.getData();//新建一个用户
			boolean flag=us.Denglu(user);
			transer.setFlag(flag);
			if(flag){
				transer.setResult("登录成功！");
			}else{
				transer.setResult("用户名或密码不正确！");
			}
		}
		
		else if(cmd.equals("zhuce")){
			//客户端请求注册
			Users user=(Users)transer.getData();//新建一个用户
			us.zhuce(user);//传给UserSeriver进行注册
			boolean flag=us.Denglu(user);//将注册了的试一下能不能登录，能的话就是注册成功
			transer.setFlag(flag);
			if(flag){
				transer.setResult("注册成功！");
			}else{
				transer.setResult("注册失败！未知原因。抱歉");
			}
		}
		
		else if(cmd.equals("uploadFile")){
			//用户请求上传文件
			File file=(File)transer.getData();//将客户端传进来的对象数据进行新建一个文件对象
			fs.saveFile(file);
			transer.setResult("上传成功！");
		}
		
		return transer;
	}
	
}
