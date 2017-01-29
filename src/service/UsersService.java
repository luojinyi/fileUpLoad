package service;

import java.sql.*;

import util.Util;

import entity.Users;

/*
 * 用户服务类就是连接数据库    
 * 提供对用户的登录、注册服务
 * 将传进来的用户进行登录注册操作
 */
public class UsersService {
	private Connection con=null;
	private PreparedStatement ptmt=null;
	private ResultSet rs=null;
	//用户服务1：注册
	public void zhuce(Users user) throws SQLException{
		String sql="insert into user(username,password	) values(?,?)";
		con=Util.getConnection();
		ptmt=con.prepareStatement(sql);
		ptmt.setString(1, user.getUsername());
		ptmt.setString(2,user.getPassword());
		ptmt.execute();
		
		Util.closeAll(con, ptmt, null);
		
	}
	//用户服务2：登录验证
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
