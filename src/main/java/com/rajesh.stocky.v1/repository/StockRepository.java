package com.rajesh.stocky.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rajesh.stocky.v1.entity.Stock;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
   Optional<Stock> findByStockId(Long id);
   void deleteByStockId(Long id);
}
