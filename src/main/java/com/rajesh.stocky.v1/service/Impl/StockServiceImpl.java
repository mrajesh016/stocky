package com.rajesh.stocky.v1.service.Impl;


import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.repository.StockRepository;
import com.rajesh.stocky.v1.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
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
    public Stock getStockById(Long stockId) {
        return stockRepository.findById(stockId);
    }

    @Override
    public Page<Stock> findAll(Pageable pageableRequest) {
        return stockRepository.findAll(pageableRequest);
    }

}
