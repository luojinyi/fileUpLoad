package entity;

import java.io.Serializable;

//�û�ʵ����
public class Users	implements Serializable {
	private static final long serialVersionUID = -7751437846681691870L;
	private int id;//�û����
	private String username;//�û���¼�˺�
	private String password;//�û���¼����
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Users(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public Users(){
		
	}
	
	
}
