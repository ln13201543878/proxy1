package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
	static String driver = "com.mysql.jdbc.Driver";
	static String url ="jdbc:mysql://localhost:3306/test?characterEncoding=utf8";
	static String user ="root";
	static String password ="";
	static Connection conn;
	static {

		
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	static public Connection getCon() throws SQLException {

		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	static public void close(ResultSet rs, Statement sta, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
				if (sta != null) {
					sta.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
