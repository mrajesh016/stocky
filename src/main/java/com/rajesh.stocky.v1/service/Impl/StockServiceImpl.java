package com.rajesh.stocky.v1.service.Impl;

import com.rajesh.stocky.v1.converter.ConverterService;
import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.sro.StockDetailSRO;
import com.rajesh.stocky.v1.sro.StocksDetailListSRO;
import com.rajesh.stocky.v1.repository.StockRepository;
import com.rajesh.stocky.v1.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service("stockService")
public class StockServiceImpl implements IStockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ConverterService converterService;

    @Override
    public StockDetailSRO saveStock(Stock stock) {
        Stock stockEntity = stockRepository.save(stock);
        return converterService.convertToSRO(stockEntity);
    }

    @Override
    @Transactional
    public void deleteStockById(Long stockId) {
        stockRepository.deleteByStockId(stockId);
    }

    @Override
    public StockDetailSRO getStockById(Long stockId) {
        Optional<Stock> stockEntity = stockRepository.findByStockId(stockId);
        return stockEntity.map(stock -> converterService.convertToSRO(stock)).orElse(null);
    }

    @Override
    public StocksDetailListSRO getAllStocksPaginated(Pageable pageableRequest) {
        Page<Stock> stockentities = stockRepository.findAll(pageableRequest);
        return converterService.convertToSRO(stockentities);
    }

}
