package com.rajesh.stocky.v1.converter;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.model.CreateStockRequestDTO;
import com.rajesh.stocky.v1.model.StockDetailResponse;
import com.rajesh.stocky.v1.model.UpdateStockRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    private ModelMapper modelMapper = new ModelMapper();

    public Stock convertToEntity(CreateStockRequestDTO stockRequestDTO) throws ParseException {
        Stock stock = modelMapper.map(stockRequestDTO, Stock.class);
        return stock;
    }

    public StockDetailResponse convertToResponseDTO(Stock stock) {
        StockDetailResponse stockInfoDTO = modelMapper.map(stock, StockDetailResponse.class);
        return stockInfoDTO;
    }

    public Stock updateStock(Stock stock, UpdateStockRequestDTO stockRequestDTO) {
        stock.setCurrentPrice(stockRequestDTO.getCurrentPrice());
        return stock;
    }
}
