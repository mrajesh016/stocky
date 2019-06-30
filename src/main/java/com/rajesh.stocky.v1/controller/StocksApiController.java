package com.rajesh.stocky.v1.controller;

import com.rajesh.stocky.v1.swagger.api.StocksApi;
import com.rajesh.stocky.v1.converter.ConverterService;
import com.rajesh.stocky.v1.sro.*;
import com.rajesh.stocky.v1.service.IStockService;
import com.rajesh.stocky.v1.swagger.model.CreateStockRequestDTO;
import com.rajesh.stocky.v1.swagger.model.StockDetailResponse;
import com.rajesh.stocky.v1.swagger.model.StockListResponse;
import com.rajesh.stocky.v1.swagger.model.UpdateStockRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Validated
@Controller
public class StocksApiController implements StocksApi {

    private static final Logger logger = LoggerFactory.getLogger(StocksApiController.class);

    @Autowired
    private IStockService stockService;
    @Autowired
    private ConverterService converterService;

    @Validated
    @Override
    public ResponseEntity<StockDetailResponse> createStock(@Valid @RequestBody CreateStockRequestDTO createStockRequest) {

        StockDetailResponse response;
        StockDetailSRO stockDetailSRO = stockService.saveStock(converterService.convertToEntity(createStockRequest));
        response = converterService.convertToResponseDTO(stockDetailSRO);
        logger.info("Stock:{} successfully created", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteStockById(@Min(0) @PathVariable("stockId") Long stockId) {

        StockDetailSRO stock = stockService.getStockById(stockId);
        if (stock == null) {
            logger.info("No stock exists with Id: {}", stockId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stock entity exists for given stockId.");
        }
        stockService.deleteStockById(stockId);
        logger.info("Stock with Id: {}, successfully deleted",stockId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<StockDetailResponse> getStockById(@Min(0) @PathVariable("stockId") Long stockId) {

        StockDetailResponse response;
        StockDetailSRO stockDetailSRO = stockService.getStockById(stockId);
        if (stockDetailSRO == null) {
            logger.info("No stock exists with Id: {}", stockId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stock entity exists for given stockId.");
        }
        response = converterService.convertToResponseDTO(stockDetailSRO);
        logger.info("Stock Successfully found with Id: {}, Stock: {}", stockId, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StockListResponse> getStocksList(@NotNull @Min(0) @RequestParam(value = "page", required = true, defaultValue="0") Integer page, @NotNull @Min(1) @Max(100) @RequestParam(value = "size", required = true, defaultValue="10") Integer size) {

        StockListResponse response;
        Pageable pageableRequest = PageRequest.of(page, size);
        StocksDetailListSRO stocksListSRO = stockService.getAllStocksPaginated(pageableRequest);
        if (page >= stocksListSRO.getTotalPages() && page!=0){
            logger.info("Requested page: {} is out of bounds.", page);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested page is out of bounds.");
        }
        response = converterService.convertToResponseDTO(stocksListSRO);
        logger.info("Successful StockListResponse: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Validated
    @Override
    public ResponseEntity<StockDetailResponse> updateStockById(@Min(0) @PathVariable("stockId") Long stockId, @Valid @RequestBody UpdateStockRequestDTO updateStockRequest) {

        StockDetailResponse response;
        StockDetailSRO stockDetailSRO = stockService.getStockById(stockId);
        if (stockDetailSRO == null) {
            logger.info("No stock exists with Id: {} ", stockId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stock entity exists for given stockId.");
        }
        stockDetailSRO = stockService.saveStock(converterService.updateStock(stockDetailSRO, updateStockRequest));
        response = converterService.convertToResponseDTO(stockDetailSRO);
        logger.info("Stock Successfully updated with Id:{} to Stock: {}", stockId, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
