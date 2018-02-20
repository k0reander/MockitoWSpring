package com.packt.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PhoneNumbers {
	private static final String CREATE_PHONENUMBERS_TABLE_SQL = "CREATE TABLE phonenumbers(number VARCHAR(256) PRIMARY KEY , first_name VARCHAR(256), last_name VARCHAR(256))";
	
	public static void main(String[] args) throws SQLException {		
		setUpDatabase();
		PhoneBookDao phoneEntryDao = new PhoneBookDerbySpringDao(DerbyInMemoryDB.getInstance().getDataSource());
		tryInsert(phoneEntryDao, "0478975011", "Frank", "Dupont");
		trySelect(phoneEntryDao);
		tryFind(phoneEntryDao);
		DerbyInMemoryDB.getInstance().shutdown();
	}
	
	private static void tryFind(PhoneBookDao phoneEntryDao) {
		List<PhoneEntry> entries = phoneEntryDao.searchByFirstName("frank");
		for (PhoneEntry phoneEntry : entries) {
			System.out.println("Found:" + phoneEntry);
		}
		
	}

	private static void setUpDatabase() throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = DerbyInMemoryDB.getInstance().getConnection(true);
			stmt = connection.createStatement();
			stmt.execute(CREATE_PHONENUMBERS_TABLE_SQL);
			System.out.println("TABLE phonenumbers created");
		}
		catch(SQLException e) {
			throw e;
		}
		finally{
			if(stmt != null)
				stmt.close();
			if(connection != null)
				connection.close();
		}
	}
		
	private static void trySelect(PhoneBookDao phoneEntryDao) throws SQLException {
		PhoneEntry entry = phoneEntryDao.searchByNumber("0478975011");
		System.out.println(entry);
	}
	
	private static void tryInsert(PhoneBookDao phoneEntryDao, String number, String firstName, String lastName) {
		PhoneEntry entry = new PhoneEntry();
		entry.setPhoneNumber(number);
		entry.setFirstName(firstName);
		entry.setLastName(lastName);
		
		try {
			phoneEntryDao.create(entry);
			System.out.println("Succesfully created entry for " + entry);
		} catch (RuntimeException e) {
			System.out.println("Failed to create entry for " + entry);
			e.printStackTrace();
		}
	}
	
}
