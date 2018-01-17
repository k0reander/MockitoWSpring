package com.packt.jdbc;

import java.sql.SQLException;
import java.util.List;

public class PhoneBookDerbySpringDao implements PhoneBookDao {

	@Override
	public boolean create(PhoneEntry entry) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PhoneEntry entry) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String number) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PhoneEntry> searchByNumber(String number) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PhoneEntry> searchByFirstName(String firstName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PhoneEntry> searchByLastName(String lastName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
