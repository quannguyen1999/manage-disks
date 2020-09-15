package application.controller.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Connect {
	public static Connection conn;
	
	public static void connect() {
		try {
			
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=manageDisks;useUnicode=yes&characterEncoding=UTF-8","sa","123");
		
		} catch (Exception e) {
		
			e.printStackTrace();
		
		}
	
	}
	public static void disconnect() {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static Connection getConnection() {
		return conn;
	}
}
