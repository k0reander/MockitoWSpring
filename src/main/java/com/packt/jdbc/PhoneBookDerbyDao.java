package com.packt.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookDerbyDao implements PhoneBookDao{
	
	private static final String INSERT_PHONE_ENTRY_SQL = "INSERT INTO phonenumbers VALUES(?, ?, ?)";
	private static final String UPDATE_PHONE_ENTRY_SQL = "UPDATE phonenumbers SET number = ?, first_name = ?, last_name = ? WHERE number = ?";
	private static final String DELETE_PHONE_ENTRY_SQL = "DELETE FROM phonenumbers WHERE number = ?";
	
	private static final String SEARCH_BY_NUMBER_SQL = "SELECT FROM phonenumbers WHERE number = ?";
	private static final String SEARCH_FIRST_NAME_IS_NULL_SQL = "SELECT FROM phonenumbers WHERE first_name_IS_NULL";
	private static final String SEARCH_LAST_NAME_IS_NULL_SQL = "SELECT FROM phonenumbers last_name IS NULL";
	private static final String SEARCH_LIKE_FIRST_NAME_SQL = "SELECT FROM phonenumbers WHERE LOWER(first_name) = ?";
	private static final String SEARCH_LIKE_LAST_NAME_SQL = "SELECT FROM phonenumbers WHERE LOWER(last_name) = ?";
	
	private final DerbyInMemoryDB db;

	public PhoneBookDerbyDao(DerbyInMemoryDB db) {
		this.db = db;
	}

	@Override
	public boolean create(PhoneEntry entry) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean result = false;
		try {
			conn = db.getConnection(false);
			stmt = conn.prepareStatement(INSERT_PHONE_ENTRY_SQL);
			stmt.setString(1, entry.getPhoneNumber());
			stmt.setString(2, entry.getFirstName());
			stmt.setString(3, entry.getLastName());
			result = stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}
		finally {
			if(stmt != null)
				stmt.close();
			stmt = null;
			if(conn != null)
				conn.close();
			conn = null;
		}
		return result;
	}

	@Override
	public boolean update(PhoneEntry entry) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean result = false;
		try {
			conn = db.getConnection(false);
			stmt = conn.prepareStatement(UPDATE_PHONE_ENTRY_SQL);
			stmt.setString(1, entry.getPhoneNumber());
			stmt.setString(2, entry.getFirstName());
			stmt.setString(3, entry.getLastName());
			stmt.setString(4, entry.getPhoneNumber());
			result = stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}
		finally {
			if(stmt != null)
				stmt.close();
			stmt = null;
			if(conn != null)
				conn.close();
			conn = null;
		}
		return result;	
	}

	@Override
	public boolean delete(String number) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean result = false;
		try {
			conn = db.getConnection(false);
			stmt = conn.prepareStatement(DELETE_PHONE_ENTRY_SQL);
			stmt.setString(1, number);
			result = stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}
		finally {
			if(stmt != null)
				stmt.close();
			stmt = null;
			if(conn != null)
				conn.close();
			conn = null;
		}
		return result;	
	}

	@Override
	public List<PhoneEntry> searchByNumber(String number) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			List<PhoneEntry> entries = new ArrayList<PhoneEntry>();
			conn = DerbyInMemoryDB.getInstance().getConnection(true);
			stmt = conn.prepareStatement(SEARCH_BY_NUMBER_SQL);
			stmt.setString(1, number);
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				PhoneEntry entry = adaptResultSetToPhoneEntry(rs);
				entries.add(entry);
			}
			
			return entries;
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
			if(conn != null)
				conn.close();
			conn = null;
		}		
	}

	@Override
	public List<PhoneEntry> searchByFirstName(String firstName) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			List<PhoneEntry> entries = new ArrayList<PhoneEntry>();
			conn = DerbyInMemoryDB.getInstance().getConnection(true);
			
			if(firstName == null) {
				stmt = conn.prepareStatement(SEARCH_FIRST_NAME_IS_NULL_SQL);
			}
			else {
				stmt = conn.prepareStatement(SEARCH_LIKE_FIRST_NAME_SQL);
				stmt.setString(1, firstName);				
			}
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				PhoneEntry entry = adaptResultSetToPhoneEntry(rs);
				entries.add(entry);
			}
			
			return entries;
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
			if(conn != null)
				conn.close();
			conn = null;
		}
	}

	@Override
	public List<PhoneEntry> searchByLastName(String lastName) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			List<PhoneEntry> entries = new ArrayList<PhoneEntry>();
			conn = DerbyInMemoryDB.getInstance().getConnection(true);
			
			if(lastName == null) {
				stmt = conn.prepareStatement(SEARCH_LAST_NAME_IS_NULL_SQL);
			}
			else {
				stmt = conn.prepareStatement(SEARCH_LIKE_LAST_NAME_SQL);
				stmt.setString(1, lastName);				
			}
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				PhoneEntry entry = adaptResultSetToPhoneEntry(rs);
				entries.add(entry);
			}
			
			return entries;
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
			if(conn != null)
				conn.close();
			conn = null;
		}
	}
	
	protected static PhoneEntry adaptResultSetToPhoneEntry(ResultSet rs) throws SQLException {
		PhoneEntry entry = new PhoneEntry();
		entry.setPhoneNumber( rs.getString("number") );
		entry.setFirstName( rs.getString("first_name") );
		entry.setLastName( rs.getString("last_name") );
		return entry;
	}

}
