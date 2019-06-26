package com.rajesh.stocky.v1.converter;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.model.StockDTO;
import com.rajesh.stocky.v1.model.StockRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    private ModelMapper modelMapper = new ModelMapper();

    public Stock convertToEntity(StockRequestDTO stockRequestDTO) throws ParseException {
        Stock stock = modelMapper.map(stockRequestDTO, Stock.class);
        return stock;
    }

    public StockDTO convertToDto(Stock stock) {
        StockDTO stockInfoDTO = modelMapper.map(stock, StockDTO.class);
        return stockInfoDTO;
    }

    public Stock updateStock(Stock stock, StockRequestDTO stockRequestDTO) {
        if(stockRequestDTO.getName()!=null)
            stock.setStockName(stockRequestDTO.getName());
        if(stockRequestDTO.getCurrentPrice()!=null)
            stock.setCurrentPrice(stockRequestDTO.getCurrentPrice());
        return stock;
    }
}
