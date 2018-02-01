package com.packt.trading;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class StockBrokerTest {

    @Mock
    MarketWatcher watcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSanity(){
        assertNotNull(watcher);
    }

    @Test
    public void testPerform(){
        Portfolio portfolio = mock(Portfolio.class);
        throw new UnsupportedOperationException("To implement");
    }

}