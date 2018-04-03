package mrhi.adventure.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	
	public static Connection getConnection() {
		return ConnectionPool.getInstance().getConnection();
	}

	public static void close(Connection conn)
	{
		try {
			if(conn!=null && !conn.isClosed())
				ConnectionPool.getInstance().returnConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt)
	{
		close(conn);
		try {
			if(pstmt!=null && !pstmt.isClosed())
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
	{
		close(conn, pstmt);
		try {
			if(rs!=null && !rs.isClosed())
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
