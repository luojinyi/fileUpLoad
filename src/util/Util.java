package util;

import java.sql.*;

//�����࣬�������ݿ����Ӻ͹ر���Դ

public class Util {
	//�������ݿ�����
	public  static Connection getConnection(){
		String driverClassName="com.mysql.jdbc.Driver";
		String URL ="jdbc:mysql://localhost:3306/socketdemo";
		String username="root";
		String password="b14718023573";
		Connection con=null;
		try {
			Class.forName(driverClassName);
			con=DriverManager.getConnection(URL,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	//�ر���Դ�Ĳ���
	public static void closeAll(Connection con,Statement stmt,ResultSet rs) throws SQLException{
		if(con!=null){
			con.close();
		}
		if(stmt!=null){
			stmt.close();
		}
		if(rs!=null){
			rs.close();
		}
	}
	
}
