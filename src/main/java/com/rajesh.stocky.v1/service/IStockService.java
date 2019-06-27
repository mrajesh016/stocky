package com.rajesh.stocky.v1.service;

import com.rajesh.stocky.v1.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IStockService{
    Stock saveStock(Stock stock);
    void deleteStock(Stock stock);
    Stock getStockById(Long stockId);
    Page<Stock> findAll(Pageable pageableRequest);
}
