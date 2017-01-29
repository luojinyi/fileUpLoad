package util;

import java.sql.*;

//工具类，建立数据库连接和关闭资源

public class Util {
	//建立数据库连接
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
	//关闭资源的操作
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
