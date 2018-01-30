package com.packt.jdbc;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class PhoneBookDerbySpringDao implements PhoneBookDao {
	
	private static final String INSERT_PHONE_ENTRY_SQL = "INSERT INTO phonenumbers VALUES(?, ?, ?)";
	private static final String UPDATE_PHONE_ENTRY_SQL = "UPDATE phonenumbers SET number = ?, first_name = ?, last_name = ? WHERE number = ?";
	private static final String UPDATE_NULL_PHONE_ENTRY_SQL = "UPDATE phonenumbers SET number = ?, first_name = ?, last_name = ? WHERE number IS NULL";
	private static final String DELETE_PHONE_ENTRY_SQL = "DELETE FROM phonenumbers WHERE number = ?";
	private static final String DELETE_NULL_PHONE_ENTRY_SQL = "DELETE FROM phonenumbers WHERE number IS NULL";
	
	private static final String SEARCH_BY_NUMBER_SQL = "SELECT * FROM phonenumbers WHERE number = ?";
	private static final String SEARCH_FIRST_NAME_IS_NULL_SQL = "SELECT * FROM phonenumbers WHERE first_name_IS_NULL";
	private static final String SEARCH_LAST_NAME_IS_NULL_SQL = "SELECT * FROM phonenumbers last_name IS NULL";
	private static final String SEARCH_LIKE_FIRST_NAME_SQL = "SELECT * FROM phonenumbers WHERE LOWER(first_name) LIKE ?";
	private static final String SEARCH_LIKE_LAST_NAME_SQL = "SELECT * FROM phonenumbers WHERE LOWER(last_name) LIKE ?";
	
	
	private final JdbcTemplate jdbcTemplate;
	private final ResultSetExtractor<PhoneEntry> phoneEntryExtractor = (ResultSet rs) -> {	PhoneEntry entry = new PhoneEntry();
																										entry.setPhoneNumber(rs.getString("number"));
																										entry.setFirstName(rs.getString("first_name"));
																										entry.setLastName(rs.getString("last_name"));
																										return entry;
																									};
	
	private final RowMapper<PhoneEntry> phoneEntryMapper = (ResultSet rs, int rowNum) -> this.phoneEntryExtractor.extractData(rs);
	
	public PhoneBookDerbySpringDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource); 
	}

	@Override
	public boolean create(PhoneEntry entry){
		int rowcount = this.jdbcTemplate.update(INSERT_PHONE_ENTRY_SQL, entry.getPhoneNumber(), entry.getFirstName(), entry.getLastName());
		return rowcount == 1;
	}

	@Override
	public boolean update(PhoneEntry entry){
		String sql = entry.getPhoneNumber() == null ? UPDATE_NULL_PHONE_ENTRY_SQL : UPDATE_PHONE_ENTRY_SQL;
		Object[] parameters = entry.getPhoneNumber() == null ? 
				new String[] {entry.getPhoneNumber(), entry.getFirstName(), entry.getLastName()} 
				: new String[] {entry.getPhoneNumber(), entry.getFirstName(), entry.getLastName(), entry.getPhoneNumber()};
		int rowcount = this.jdbcTemplate.update(sql, parameters);
		return rowcount >= 1;
	}

	@Override
	public boolean delete(String number){
		String sql = ( number == null ? DELETE_NULL_PHONE_ENTRY_SQL : DELETE_PHONE_ENTRY_SQL );
		Object[] parameters = ( number == null ? null : new String[]{number} );
		int rowcount = this.jdbcTemplate.update(sql, parameters);
		return rowcount >= 1;
	}

	@Override
	public PhoneEntry searchByNumber(String number){
		return this.jdbcTemplate.query(SEARCH_BY_NUMBER_SQL, phoneEntryExtractor);
	}

	@Override
	public List<PhoneEntry> searchByFirstName(String firstName){
		String sql = ( firstName == null ? SEARCH_FIRST_NAME_IS_NULL_SQL : SEARCH_LIKE_FIRST_NAME_SQL );
		Object[] parameters = ( firstName == null ? null : new String[] {"%"+firstName+"%"} );
		return this.jdbcTemplate.query(sql, phoneEntryMapper, parameters);
	}

	@Override
	public List<PhoneEntry> searchByLastName(String lastName){
		String sql = ( lastName == null ? SEARCH_LAST_NAME_IS_NULL_SQL : SEARCH_LIKE_LAST_NAME_SQL );
		Object[] parameters = ( lastName == null ? null : new String[] {"%"+lastName+"%"} ) ;
		return this.jdbcTemplate.query(sql, phoneEntryMapper, parameters);
	}

}
