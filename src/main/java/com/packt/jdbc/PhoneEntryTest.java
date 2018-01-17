package com.packt.jdbc;

import static org.junit.Assert.*;

import org.junit.Test;

public class PhoneEntryTest {

	@Test
	public void testtoString() {
		String expected = "Frank Dupont : 0478975011";
		PhoneEntry entry = new PhoneEntry();
		entry.setFirstName("Frank");
		entry.setLastName("Dupont");
		entry.setPhoneNumber("0478975011");
		assertEquals("toStirng() method incorrect", expected, entry.toString());
	}

}
