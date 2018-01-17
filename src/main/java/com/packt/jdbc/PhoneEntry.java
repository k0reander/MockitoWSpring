package com.packt.jdbc;

public class PhoneEntry {
	
	private String phoneNumber;
	private String firstName;
	private String lastName;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s : %s", getFirstName(), getLastName(), getPhoneNumber());
		
	}
	
}
