package com.rajesh.stocky.v1.converter;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.sro.*;
import com.rajesh.stocky.v1.swagger.model.CreateStockRequestDTO;
import com.rajesh.stocky.v1.swagger.model.StockDetailResponse;
import com.rajesh.stocky.v1.swagger.model.StockListResponse;
import com.rajesh.stocky.v1.swagger.model.UpdateStockRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConverterService {

    private ModelMapper modelMapper = new ModelMapper();

    public Stock updateStock(StockDetailSRO stockDetailSRO, UpdateStockRequestDTO stockRequestDTO) {
        stockDetailSRO.setCurrentPrice(stockRequestDTO.getCurrentPrice());
        return modelMapper.map(stockDetailSRO, Stock.class);
    }

    public Stock convertToEntity(CreateStockRequestDTO stockRequestDTO) throws ParseException {
        return modelMapper.map(stockRequestDTO, Stock.class);
    }

    public StockDetailResponse convertToResponseDTO(StockDetailSRO stockDetailSRO) {
        return  modelMapper.map(stockDetailSRO, StockDetailResponse.class);
    }

    public StockListResponse convertToResponseDTO(StocksDetailListSRO listDetailSRO) {
        if (listDetailSRO ==null) return null;
        StockListResponse listResponse = new StockListResponse();
        List<StockDetailResponse> detailResponses = new ArrayList<>();
        for (StockDetailSRO detailSRO: listDetailSRO.getStockDetailSROs()){
            detailResponses.add(modelMapper.map(detailSRO, StockDetailResponse.class));
        }
        listResponse.setSize(listDetailSRO.getSize());
        listResponse.setNumber(listDetailSRO.getNumber());
        listResponse.setTotalPages(listDetailSRO.getTotalPages());
        listResponse.setTotalElements(listDetailSRO.getTotalElements());
        listResponse.setStocksDetailList(detailResponses);
        return listResponse;
    }

    public StockDetailSRO convertToSRO(Stock stock){
        return modelMapper.map(stock, StockDetailSRO.class);
    }

    public StocksDetailListSRO convertToSRO(Page<Stock> stocks){
        if (stocks == null) return null;
        List<StockDetailSRO> sroList =  new ArrayList<>();
        List<Stock> stockEntities = stocks.getContent();
        for (Stock stock: stockEntities){
            StockDetailSRO stockDetailSRO = modelMapper.map(stock, StockDetailSRO.class);
            sroList.add(stockDetailSRO);
        }
        return StocksDetailListSRO.builder().size(stocks.getSize())
                                            .number(stocks.getNumber())
                                            .totalElements(stocks.getTotalElements())
                                            .totalPages(stocks.getTotalPages())
                                            .stockDetailSROs(sroList)
                                            .build();
    }
}
