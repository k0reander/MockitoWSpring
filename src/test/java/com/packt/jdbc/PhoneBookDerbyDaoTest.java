package com.packt.jdbc;

import org.junit.*;

import javax.sql.DataSource;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PhoneBookDerbyDaoTest extends AbstractPhoneBookDaoTest {

    @Test(expected = NullPointerException.class)
    public void usingNullDataSourceThrowsException(){
        DataSource nullDS = null;
        PhoneBookDerbyDao dao = new PhoneBookDerbyDao(nullDS);
        PhoneEntry entry = new PhoneEntry();
        dao.create(entry);
    }

    @Test
    public void testCreateEntry(){
        PhoneEntry entry = createPhoneEntryFor("Frank Dupont", "0478975011");
        boolean committed = dao.create(entry);
        assertTrue(committed);

        PhoneEntry retrievedEntry = dao.searchByNumber(entry.getPhoneNumber());
        assertNotNull("Could not retrieve " + entry, retrievedEntry);

        assertEquals("Phonenumbers are not equal", entry.getPhoneNumber(), retrievedEntry.getPhoneNumber());
        assertEquals("First names are not equal", entry.getFirstName(), retrievedEntry.getFirstName());
        assertEquals("Last names are not equal", entry.getLastName(), retrievedEntry.getLastName());
    }

    @Test
    public void testDeleteEntry(){
        PhoneEntry entry = createPhoneEntryFor("Frank Dupont", "0478975011");
        dao.create(entry);

        boolean deleted = dao.delete(entry.getPhoneNumber());
        assertTrue("Delete failed of" + entry,deleted);

        assertNull(entry + " was not deleted", dao.searchByNumber(entry.getPhoneNumber()));

    }

}
