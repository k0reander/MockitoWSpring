package com.packt.jdbc;

import org.junit.Test;

import static com.packt.jdbc.AlphabeticallyBeforeMatcher.alphabeticallyBefore;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlphabetTest {
    @Test
    public void testAlphabeticOrder(){
        PhoneEntry entry = new PhoneEntry();
        entry.setPhoneNumber("1");
        entry.setFirstName("frank");
        entry.setLastName("dupont");

        assertThat(entry, alphabeticallyBefore('g'));
    }
}
