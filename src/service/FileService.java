package service;

import java.sql.*;
import util.Util;
import entity.File;
/*
 * 作用就是连接数据库    上传文件
 */
public class FileService {
	
	private Connection con=null;
	private PreparedStatement ptmt=null;
	//将传进来的文件进行上传数据库的file表
	public void saveFile(File file) throws SQLException{
		String sql="insert into file (fname,fcontent) values(?,?)";
		con=Util.getConnection();
		ptmt=con.prepareStatement(sql);
		ptmt.setString(1,file.getFname());
		ptmt.setBytes(2, file.getFcontent());
		ptmt.executeUpdate();//上传更新
		
		Util.closeAll(con, ptmt, null);
	}
	
	
}
