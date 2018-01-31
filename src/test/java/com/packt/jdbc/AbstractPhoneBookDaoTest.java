package com.packt.jdbc;

import org.junit.After;
import org.junit.Before;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class PhoneBookDaoTest {

    private static final String CREATE_PHONENUMBERS_TABLE_SQL = "CREATE TABLE phonenumbers(number VARCHAR(256) NOT NULL , first_name VARCHAR(256), last_name VARCHAR(256))";
    private static final String DROP_PHONENUMBERS_TABLE_SQL = "DROP TABLE phonenumbers";

    protected PhoneBookDerbyDao dao;

    @Before
    public void setupDataBase() throws SQLException {
        DataSource ds = DerbyInMemoryDB.getInstance().getDataSource();
        this.dao = new PhoneBookDerbyDao(ds);
        executeSQL(CREATE_PHONENUMBERS_TABLE_SQL);
    }

    @After
    public void resetDataBase() throws SQLException {
        executeSQL(DROP_PHONENUMBERS_TABLE_SQL);
    }

    //HELPER METHODS
    protected PhoneEntry createPhoneEntryFor(String name, String number){
        PhoneEntry entry = new PhoneEntry();
        String[] nameParts = name.split(" ");
        entry.setFirstName(nameParts[0]);
        entry.setLastName(nameParts[1]);
        entry.setPhoneNumber(number);
        return entry;
    }

    private static void executeSQL(String statement) throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DerbyInMemoryDB.getInstance().getConnection(true);
            stmt = connection.createStatement();
            stmt.execute(statement);
        }
        catch(SQLException e) {
            throw e;
        }
        finally{
            if(stmt != null)
                stmt.close();
            if(connection != null)
                connection.close();
        }
    }
}
