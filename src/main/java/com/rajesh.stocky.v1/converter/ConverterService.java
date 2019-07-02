package com.rajesh.stocky.v1.converter;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.dto.*;
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

    public Stock updateAndConvertToStock(StockDetailDTO stockDetailDTO, UpdateStockRequestDTO stockRequestDTO) {
        stockDetailDTO.setCurrentPrice(stockRequestDTO.getCurrentPrice());
        return modelMapper.map(stockDetailDTO, Stock.class);
    }

    public Stock convertToEntity(CreateStockRequestDTO stockRequestDTO) throws ParseException {
        return modelMapper.map(stockRequestDTO, Stock.class);
    }

    public StockDetailResponse convertToResponseDTO(StockDetailDTO stockDetailDTO) {
        return  modelMapper.map(stockDetailDTO, StockDetailResponse.class);
    }

    public StockListResponse convertToResponseDTO(StocksDetailListDTO listDetailDTO) {
        if (listDetailDTO ==null) return null;
        StockListResponse listResponse = new StockListResponse();
        List<StockDetailResponse> detailResponses = new ArrayList<>();
        listDetailDTO.getStockDetailDTOS().forEach(dto->detailResponses.add(modelMapper.map(dto, StockDetailResponse.class)));

        listResponse.setStocksDetailList(detailResponses);
        listResponse.setSize(listDetailDTO.getSize());
        listResponse.setNumber(listDetailDTO.getNumber());
        listResponse.setTotalPages(listDetailDTO.getTotalPages());
        listResponse.setTotalElements(listDetailDTO.getTotalElements());
        return listResponse;
    }

    public StockDetailDTO convertToDTO(Stock stock){
        return modelMapper.map(stock, StockDetailDTO.class);
    }

    public StocksDetailListDTO convertToDTO(Page<Stock> stocks){
        if (stocks == null) return null;
        List<StockDetailDTO> dtoList =  new ArrayList<>();
        List<Stock> stockEntities = stocks.getContent();
        stockEntities.forEach(stock->dtoList.add( modelMapper.map(stock, StockDetailDTO.class)));
        return StocksDetailListDTO.builder().size(stocks.getSize())
                                            .number(stocks.getNumber())
                                            .totalElements(stocks.getTotalElements())
                                            .totalPages(stocks.getTotalPages())
                                            .stockDetailDTOS(dtoList)
                                            .build();
    }
}
