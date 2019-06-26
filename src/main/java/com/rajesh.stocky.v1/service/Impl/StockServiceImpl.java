package com.rajesh.stocky.v1.service.Impl;


import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.repository.StockRepository;
import com.rajesh.stocky.v1.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("stockService")
public class StockServiceImpl implements IStockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStock(Stock stock) {
        stockRepository.delete(stock);
    }

    @Override
    public Stock getStockById(int stockId) {
        return stockRepository.findById(stockId);
    }

    @Override
    public List<Stock> getStocksList(Integer page, Integer size) {
        return null;
    }

}
