package com.packt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;

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
		Connection connection = getDataSource().getConnection();
		connection.setAutoCommit(autoCommit);
		return connection;
	}
	
	public DataSource getDataSource() {
		EmbeddedDataSource ds = new EmbeddedDataSource(); 
		ds.setDatabaseName("memory:Mockito");
		return ds;
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
