package mrhi.adventure.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {
	
	private static ConnectionPool theInstance = new ConnectionPool(20);
	
	private Queue<Connection> connQueue = new LinkedList<>();
	private String url = "jdbc:postgresql://localhost:5432/adventure";
	private String id = "postgres";
	private String pw = "root";
	
	public static ConnectionPool getInstance() {
		return theInstance;
	}

	private ConnectionPool(int maxConn) {
		for(int i=0; i<maxConn; i++) {
			connQueue.offer(makeConnection());
		}
	}

	public Connection getConnection() {
		return connQueue.poll();
	}
	
	public void returnConnection(Connection conn) {
		connQueue.offer(conn);		
	}
	
	public Connection makeConnection() {
		try {
			return DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
