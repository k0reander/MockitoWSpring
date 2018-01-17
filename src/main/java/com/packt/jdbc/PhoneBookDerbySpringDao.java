package com.packt.jdbc;

import java.util.List;

public class PhoneBookDerbySpringDao implements PhoneBookDao {

	@Override
	public boolean create(PhoneEntry entry){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PhoneEntry entry){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String number){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PhoneEntry> searchByNumber(String number){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PhoneEntry> searchByFirstName(String firstName){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PhoneEntry> searchByLastName(String lastName){
		// TODO Auto-generated method stub
		return null;
	}

}
