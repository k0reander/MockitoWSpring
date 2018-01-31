package com.packt.jdbc;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;

public class AlphabeticallyBeforeMatcher extends BaseMatcher<PhoneEntry> {

    private final Character c;

    public AlphabeticallyBeforeMatcher(Character c) {
        this.c = c;
    }


    @Override
    public boolean matches(Object item) {
        if (!(item instanceof PhoneEntry))
            return false;

        PhoneEntry entry = (PhoneEntry) item;
        Character startFirstName = entry.getFirstName().toLowerCase().charAt(0);
        Character startLastName = entry.getLastName().toLowerCase().charAt(0);

        Character caseIgnored = c.toString().toLowerCase().charAt(0);

        boolean firstOk = caseIgnored.compareTo(startFirstName) > 0;
        boolean lastOk = caseIgnored.compareTo(startLastName) > 0;

        return firstOk && lastOk;

    }

    @Override
    public void describeTo(Description description) {
        description.appendText("First and Last name appear alphebetically before " + c);
    }

    @Factory
    public static AlphabeticallyBeforeMatcher alphabeticallyBefore(Character c){
        return new AlphabeticallyBeforeMatcher(c);
    }
}
