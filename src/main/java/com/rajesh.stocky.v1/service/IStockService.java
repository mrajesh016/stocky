package com.rajesh.stocky.v1.service;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.model.StockDetailSRO;
import com.rajesh.stocky.v1.model.StocksDetailListSRO;
import org.springframework.data.domain.Pageable;

public interface IStockService{
    StockDetailSRO saveStock(Stock stock);
    void deleteStockById(Long stockId);
    StockDetailSRO getStockById(Long stockId);
    StocksDetailListSRO getAllStocksPaginated(Pageable pageableRequest);
}
