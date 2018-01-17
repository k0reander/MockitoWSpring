package com.packt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyInMemoryDB {
	
	private static final DerbyInMemoryDB instance = new DerbyInMemoryDB();
	static {
		try {
			DriverManager.getConnection("jdbc:derby:memory:Mockito;create=true");
		} catch (SQLException e) {
			throw new RuntimeException("Could not create Derby in-memory database", e);
		}
	}
	private DerbyInMemoryDB() {}
	
	public Connection getConnection(boolean autoCommit) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:derby:memory:Mockito");
		connection.setAutoCommit(autoCommit);
		return connection;
		
	}
	
	public void shutdown() throws SQLException {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException e) {
			if ( e.getErrorCode() == 50000 && "XJ015".equals(e.getSQLState()) ){
				System.out.println("Derby shut down normally");
			}
			else
				throw e;
		}		
	}
	
	public static DerbyInMemoryDB getInstance() {
		return instance;
	}
}
