package com.rajesh.stocky.v1.repository;

import org.springframework.data.repository.CrudRepository;
import com.rajesh.stocky.v1.entity.Stock;


public interface StockRepository extends CrudRepository<Stock, String> {
   Stock findById(Integer id);
}
