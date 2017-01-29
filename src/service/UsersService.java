package service;

import java.sql.*;

import util.Util;

import entity.Users;

/*
 * �û�����������������ݿ�    
 * �ṩ���û��ĵ�¼��ע�����
 * �����������û����е�¼ע�����
 */
public class UsersService {
	private Connection con=null;
	private PreparedStatement ptmt=null;
	private ResultSet rs=null;
	//�û�����1��ע��
	public void zhuce(Users user) throws SQLException{
		String sql="insert into user(username,password	) values(?,?)";
		con=Util.getConnection();
		ptmt=con.prepareStatement(sql);
		ptmt.setString(1, user.getUsername());
		ptmt.setString(2,user.getPassword());
		ptmt.execute();
		
		Util.closeAll(con, ptmt, null);
		
	}
	//�û�����2����¼��֤
	public boolean Denglu(Users user) throws SQLException{
		String sql="select username,password from user where username=? and password=?";
		con=Util.getConnection();
		ptmt=con.prepareStatement(sql);
		ptmt.setString (1,user.getUsername());
		ptmt.setString(2,user.getPassword());
		rs=ptmt.executeQuery();
		if(rs.next()){
			return true;
		}
		Util.closeAll(con, ptmt, rs);		
		
		return false;
	}
	
	
	
}
