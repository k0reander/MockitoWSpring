package com.packt.jdbc;

import java.sql.SQLException;
import java.util.List;

public interface PhoneBookDao {

	boolean create (PhoneEntry entry) throws SQLException;
	
	boolean update (PhoneEntry entry) throws SQLException;
	
	boolean delete (String number) throws SQLException;
	
	List<PhoneEntry> searchByNumber(String number) throws SQLException;
	
	List<PhoneEntry> searchByFirstName(String firstName) throws SQLException;
	
	List<PhoneEntry> searchByLastName(String lastName) throws SQLException;
	
}
