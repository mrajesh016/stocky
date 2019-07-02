package com.rajesh.stocky.v1.service;

import com.rajesh.stocky.v1.dto.StocksDetailListDTO;
import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.dto.StockDetailDTO;
import org.springframework.data.domain.Pageable;

public interface IStockService{
    StockDetailDTO saveOrUpdateStock(Stock stock);
    void deleteStockById(Long stockId);
    StockDetailDTO getStockById(Long stockId);
    StocksDetailListDTO getAllStocksPaginated(Pageable pageableRequest);
}
