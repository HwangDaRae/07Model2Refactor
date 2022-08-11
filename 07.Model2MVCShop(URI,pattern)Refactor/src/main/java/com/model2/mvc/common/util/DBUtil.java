package com.model2.mvc.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtil {
	
	private final static String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final static String JDBC_URL = "jdbc:oracle:thin:scott/tiger@localhost:1521:xe";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// Connection Statement ResultSet close()
	public static void close(Connection con, PreparedStatement pStmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		close(con, pStmt);
	}

	//Connection Statement close()
	public static void close(Connection con, PreparedStatement pStmt) {
		if(pStmt != null) {
			try {
				pStmt.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		if(con != null) {
			try {
				con.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}//end of close method

}