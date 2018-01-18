package com.packt.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class PhoneBookDerbyDao implements PhoneBookDao{
	
	private static final String INSERT_PHONE_ENTRY_SQL = "INSERT INTO phonenumbers VALUES(?, ?, ?)";
	private static final String UPDATE_PHONE_ENTRY_SQL = "UPDATE phonenumbers SET number = ?, first_name = ?, last_name = ? WHERE number = ?";
	private static final String DELETE_PHONE_ENTRY_SQL = "DELETE FROM phonenumbers WHERE number = ?";
	
	private static final String SEARCH_BY_NUMBER_SQL = "SELECT * FROM phonenumbers WHERE number = ?";
	private static final String SEARCH_FIRST_NAME_IS_NULL_SQL = "SELECT * FROM phonenumbers WHERE first_name_IS_NULL";
	private static final String SEARCH_LAST_NAME_IS_NULL_SQL = "SELECT * FROM phonenumbers last_name IS NULL";
	private static final String SEARCH_LIKE_FIRST_NAME_SQL = "SELECT * FROM phonenumbers WHERE LOWER(first_name) = ?";
	private static final String SEARCH_LIKE_LAST_NAME_SQL = "SELECT * FROM phonenumbers WHERE LOWER(last_name) = ?";
	
	private final DataSource ds;

	public PhoneBookDerbyDao(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public boolean create(PhoneEntry entry){
		Connection conn = null;
		PreparedStatement stmt = null;
	
		try {
			conn = getConnection(false);
			stmt = conn.prepareStatement(INSERT_PHONE_ENTRY_SQL);
			stmt.setString(1, entry.getPhoneNumber());
			stmt.setString(2, entry.getFirstName());
			stmt.setString(3, entry.getLastName());
			stmt.execute();
			conn.commit();
			return true;
		} catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					throw new RuntimeException("conn.rollback() threw SQLEXception and " + e.getMessage(), e);
				}
			throw new RuntimeException(e);
		}
		finally {
			if(stmt != null)
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}
	}

	@Override
	public boolean update(PhoneEntry entry){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(false);
			stmt = conn.prepareStatement(UPDATE_PHONE_ENTRY_SQL);
			stmt.setString(1, entry.getPhoneNumber());
			stmt.setString(2, entry.getFirstName());
			stmt.setString(3, entry.getLastName());
			stmt.setString(4, entry.getPhoneNumber());
			stmt.execute();
			conn.commit();
			return true;
		} catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					throw new RuntimeException("conn.rollback() threw SQLEXception and " + e.getMessage(), e);
				}
			throw new RuntimeException(e);
		}
		finally {
			if(stmt != null)
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}
	}

	@Override
	public boolean delete(String number){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection(false);
			stmt = conn.prepareStatement(DELETE_PHONE_ENTRY_SQL);
			stmt.setString(1, number);
			stmt.execute();
			conn.commit();
			return true;
		} catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					throw new RuntimeException("conn.rollback() threw SQLEXception and " + e.getMessage(), e);
				}
			throw new RuntimeException(e);
		}
		finally {
			if(stmt != null)
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}
	}

	@Override
	public List<PhoneEntry> searchByNumber(String number){
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
			throw new RuntimeException(e);
		}
		finally {
			if(rs != null)
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(stmt != null)
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}		
	}

	@Override
	public List<PhoneEntry> searchByFirstName(String firstName){
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
			throw new RuntimeException(e);
		}
		finally {
			if(rs != null)
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(stmt != null)
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}
	}

	@Override
	public List<PhoneEntry> searchByLastName(String lastName){
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
			throw new RuntimeException(e);
		}
		finally {
			if(rs != null)
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(stmt != null)
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			if(conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}
	}
	
	protected Connection getConnection(boolean autoCommit) throws SQLException {
		Connection conn = this.ds.getConnection();
		conn.setAutoCommit(autoCommit);
		return conn;
	}
	
	protected static PhoneEntry adaptResultSetToPhoneEntry(ResultSet rs) throws SQLException{
		PhoneEntry entry = new PhoneEntry();
		entry.setPhoneNumber( rs.getString("number") );
		entry.setFirstName( rs.getString("first_name") );
		entry.setLastName( rs.getString("last_name") );
		return entry;
	}

}
