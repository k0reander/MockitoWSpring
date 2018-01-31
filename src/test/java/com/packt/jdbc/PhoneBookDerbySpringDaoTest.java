package com.packt.jdbc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.sql.DataSource;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class PhoneBookDerbySpringDaoTest extends AbstractPhoneBookDaoTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void usingNullDataSourceThrowsException(){
        exception.expect(NullPointerException.class);

        DataSource ds = null;
        PhoneBookDerbyDao dao = new PhoneBookDerbyDao(ds);
        PhoneEntry entry = new PhoneEntry();
        dao.create(entry);
    }

    @Test
    public void testCreateEntry(){
        PhoneEntry entry = createPhoneEntryFor("Frank Dupont", "0478975011");
        boolean committed = dao.create(entry);
        assertThat(committed, is(true));

        PhoneEntry retrievedEntry = dao.searchByNumber(entry.getPhoneNumber());
        assertThat(retrievedEntry, is(not(nullValue())));
        assertThat(entry.getPhoneNumber(), is(equalTo(retrievedEntry.getPhoneNumber())));
        assertThat(entry.getFirstName(), is(equalTo(retrievedEntry.getFirstName())));
        assertThat(entry.getLastName(), is(equalTo(retrievedEntry.getLastName())));

    }

    @Test
    public void testDeleteEntry(){
        PhoneEntry entry = createPhoneEntryFor("Frank Dupont", "0478975011");
        dao.create(entry);

        boolean deleted = dao.delete(entry.getPhoneNumber());
        assertThat(deleted, is(true));
        PhoneEntry retrievedEntry = dao.searchByNumber(entry.getPhoneNumber());
        assertThat(retrievedEntry, is(nullValue()));
    }

}