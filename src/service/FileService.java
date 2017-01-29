package service;

import java.sql.*;
import util.Util;
import entity.File;
/*
 * ���þ����������ݿ�    �ϴ��ļ�
 */
public class FileService {
	
	private Connection con=null;
	private PreparedStatement ptmt=null;
	//�����������ļ������ϴ����ݿ��file��
	public void saveFile(File file) throws SQLException{
		String sql="insert into file (fname,fcontent) values(?,?)";
		con=Util.getConnection();
		ptmt=con.prepareStatement(sql);
		ptmt.setString(1,file.getFname());
		ptmt.setBytes(2, file.getFcontent());
		ptmt.executeUpdate();//�ϴ�����
		
		Util.closeAll(con, ptmt, null);
	}
	
	
}
