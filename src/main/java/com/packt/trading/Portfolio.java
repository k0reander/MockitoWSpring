package com.packt.trading;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.packt.trading.dto.Stock;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public class Portfolio {
    private Multimap<String, Stock> portfolio = ArrayListMultimap.create();

    public void buy(Stock stock){
        portfolio.put(stock.getSymbol(), stock);
    }


    public void sell(Stock stock, int quantity){
        if(quantity < 0)
            throw new IllegalArgumentException("Cannot sell negative amount fo stock");

        portfolio.get(stock.getSymbol()).stream()
                .limit(quantity)
                .forEach( s -> portfolio.remove(s.getSymbol(), s));
    }


    public BigDecimal getAvgPrice(Stock stock){
        Collection<Stock> stocks = portfolio.get(stock.getSymbol());

        Optional<BigDecimal> avgPriceOptional = stocks.stream()
                .map(Stock::getPrice)
                .reduce(BigDecimal::add);

        if(!avgPriceOptional.isPresent())
            throw new IllegalArgumentException("Cannot average price of stock not in portfolio : " + stock);
        else
            return avgPriceOptional.get().divide(new BigDecimal( stocks.size() ));
    }


    public BigDecimal getCurrentValue() {
        BigDecimal avgPrice = BigDecimal.ZERO;
        return portfolio.values().stream()
                .map(c -> c.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
