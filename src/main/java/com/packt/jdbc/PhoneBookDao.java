package com.packt.jdbc;

import java.util.List;

public interface PhoneBookDao {

	boolean create (PhoneEntry entry);
	
	boolean update (PhoneEntry entry);
	
	boolean delete (String number);
	
	PhoneEntry searchByNumber(String number);
	
	List<PhoneEntry> searchByFirstName(String firstName);
	
	List<PhoneEntry> searchByLastName(String lastName);
	
}
