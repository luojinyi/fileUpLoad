package util;

import java.io.Serializable;
//客户端与服务器之间传来传去的就是这个家伙，要实现Serializable接口才能将其进行流的输入输出操作
public class CommendTranser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//这是为客户端向服务器发送的数据进行打包
	private String cmd;//客户端请求操作的命令
	private Object data;//发送的数据，可以是file对象也可以是user对象
	
	//这是为了服务器返回给客户端的反馈信息进行打包
	private boolean flag;//操作是否成功
	private String result;//返回的结果
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
