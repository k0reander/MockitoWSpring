package com.packt.trading;

import com.packt.trading.dto.Stock;

import java.math.BigDecimal;

public class StockBroker {

    private final static BigDecimal LIMIT = new BigDecimal(0.10);

    private MarketWatcher watcher;

    public StockBroker(MarketWatcher watcher) {
        this.watcher = watcher;
    }

    public void perform(Portfolio portfolio, Stock stock){
        //if percetage gained of live stock is higher than a limit, sell 10
        //if negative, than buy 1
        Stock liveStock = watcher.getQuote(stock.getSymbol());
        BigDecimal avg = portfolio.getAvgPrice(stock);
        BigDecimal priceGained = liveStock.getPrice().subtract(avg);
        BigDecimal percentGained = priceGained.divide(avg);
        if( percentGained.compareTo(LIMIT) > 0 )
            portfolio.sell(stock, 10);
        if ( percentGained.compareTo(LIMIT) < 0)
            portfolio.buy(stock);
    }
}
