package com.rajesh.stocky.v1.service;

import com.rajesh.stocky.v1.entity.Stock;

import java.util.List;

public interface IStockService{
    Stock saveStock(Stock stock);
    void deleteStock(Stock stock);
    Stock getStockById(int stockId);
    List<Stock> getStocksList(Integer page, Integer size);

}
