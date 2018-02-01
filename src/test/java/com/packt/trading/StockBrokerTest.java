package com.packt.trading;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class StockBrokerTest {

    @Mock
    MarketWatcher watcher;

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