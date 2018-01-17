package com.packt.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PhoneNumbers {
	private static final String CREATE_SQL = "CREATE TABLE phonenumbers(number VARCHAR(256) NOT NULL , first_name VARCHAR(256), last_name VARCHAR(256))";
	private static final String INSERT_SQL = "INSERT INTO phonenumbers VALUES(?, ?, ?)";
	private static final String SELECT_SQL = "SELECT * FROM phonenumbers";
	
	public static void main(String[] args) throws SQLException {
		setUpDatabase();
		tryInsert("0478975011", "Frank", "Dupont");
		trySelect();
		DerbyInMemoryDB.getInstance().shutdown();
	}
	
	private static void setUpDatabase() throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = DerbyInMemoryDB.getInstance().getConnection(true);
			stmt = connection.createStatement();
			stmt.execute(CREATE_SQL);
			System.out.println("TABLE phonenumbers created");
		}
		catch(SQLException e) {
			throw e;
		}
		finally{
			if(stmt != null)
				stmt.close();
			stmt = null;
			if(connection != null)
				connection.close();
			connection = null;
		}
	}
		
	private static void trySelect() throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = DerbyInMemoryDB.getInstance().getConnection(true);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SELECT_SQL);
			while(rs.next()) {
				String phoneNumber = rs.getString("number");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				System.out.println(firstName + " " + lastName + ":" + phoneNumber);
			}
		}
		 catch (SQLException e) {
				throw e;
			}
		finally {
			if(rs != null)
				rs.close();
			rs = null;
			if(stmt != null)
				stmt.close();
			stmt = null;
			if(connection != null)
				connection.close();
			connection = null;
		}
	}
	
	private static void tryInsert(String number, String firstName, String lastName) throws SQLException {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DerbyInMemoryDB.getInstance().getConnection(false);
			stmt = connection.prepareStatement(INSERT_SQL);
			stmt.setString(1, number);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName);
			int rows = stmt.executeUpdate();
			System.out.println("Created " + rows + " row(s)");
			connection.commit();
		} catch (SQLException e) {
			throw e;
		}
		finally {
			if(stmt != null)
				stmt.close();
			stmt = null;			
			if(connection != null)
				connection.close();
			connection = null;
		}
		
	}
	
}
