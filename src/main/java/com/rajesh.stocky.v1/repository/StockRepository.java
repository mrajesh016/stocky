package com.rajesh.stocky.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rajesh.stocky.v1.entity.Stock;


public interface StockRepository extends JpaRepository<Stock, String> {
   Stock findByStockId(Long id);
   void deleteByStockId(Long id);
}
