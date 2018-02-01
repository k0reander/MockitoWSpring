package com.packt.trading;

import com.packt.trading.dto.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockBrokerTest {

    @Mock
    MarketWatcher watcher;

    StockBroker broker;

    Stock myStock = new Stock("Stock", "A Comp", new BigDecimal(10) );
    Stock highStock = new Stock("Stock", "A Comp", new BigDecimal(11.20) );
    Stock lowStock = new Stock("Stock", "A Comp", new BigDecimal(9) );
    Stock equalStock = new Stock("Stock", "A Comp", new BigDecimal(10) );

    @Before
    public void initBroker(){
        broker = new StockBroker(watcher);
    }

    @Test
    public void testSanity(){
        assertNotNull(watcher);
        assertNotNull(broker);
    }

    @Test
    public void test10SoldWhenStockHigh(){
        Portfolio portfolio = mock(Portfolio.class);
        when( portfolio.getAvgPrice( any(Stock.class) )).thenReturn( new BigDecimal(10.0) );
        when( watcher.getQuote( anyString() )).thenReturn(highStock);
        broker.perform(portfolio, myStock);
        verify(portfolio).sell(myStock, 10);
    }

    @Test
    public void bought1WhenStockLow(){
        Portfolio portfolio = mock(Portfolio.class);
        when( portfolio.getAvgPrice( any(Stock.class)) ).thenReturn( new BigDecimal(10.0) );
        when( watcher.getQuote(anyString()) ).thenReturn(lowStock);
        broker.perform(portfolio, myStock);
        verify(portfolio).buy(myStock);
    }

    @Test
    public void nothingHappensWhenStockUnchanged(){
        Portfolio portfolio = mock(Portfolio.class);
        when( portfolio.getAvgPrice( any(Stock.class) )).thenReturn(new BigDecimal(10.0));
        when( watcher.getQuote(anyString()) ).thenReturn(equalStock);
        broker.perform(portfolio, myStock);

        //verifies that absolutely nothing else happens on portfolio after the initial call; fragile and depends on portfolio implementation
        verify(portfolio).getAvgPrice(myStock);
        verifyNoMoreInteractions(portfolio);

        //verifies instead clearly that nothing is sold or bought; better solution
        verify(portfolio, never()).buy( any(Stock.class) );
        verify(portfolio, never()).sell( any(Stock.class), anyInt() );
    }

}