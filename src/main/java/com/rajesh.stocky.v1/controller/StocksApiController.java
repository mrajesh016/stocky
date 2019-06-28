package com.rajesh.stocky.v1.controller;

import com.rajesh.stocky.v1.StocksApi;
import com.rajesh.stocky.v1.converter.ConverterService;
import com.rajesh.stocky.v1.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajesh.stocky.v1.service.IStockService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class StocksApiController implements StocksApi {

    private static final Logger log = LoggerFactory.getLogger(StocksApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @Autowired
    private IStockService stockService;
    @Autowired
    private ConverterService converterService;

    @Autowired
    public StocksApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<StockDetailResponse> createStock(@ApiParam(value = "Stock to create"  )  @Valid @RequestBody CreateStockRequestDTO createStockRequest) {

        StockDetailResponse response;
        StockDetailSRO stockDetailSRO = stockService.saveStock(converterService.convertToEntity(createStockRequest));
        response = converterService.convertToResponseDTO(stockDetailSRO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Void> deleteStockById(@ApiParam(value = "",required=true) @PathVariable("stockId") Long stockId) {

        StockDetailSRO stock = stockService.getStockById(stockId);
        if (stock == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stock entity exists for given stockId.");

        stockService.deleteStockById(stockId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<StockDetailResponse> getStockById(@ApiParam(value = "",required=true) @PathVariable("stockId") Long stockId) {

        StockDetailResponse response;
        StockDetailSRO stockDetailSRO = stockService.getStockById(stockId);
        if (stockDetailSRO == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stock entity exists for given stockId.");

        response = converterService.convertToResponseDTO(stockDetailSRO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<StockListResponse> getStocksList(@Min(0)@ApiParam(value = "0 based page index.", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page, @Min(1) @Max(100)@ApiParam(value = "size of the page to be returned.", defaultValue = "25") @Valid @RequestParam(value = "size", required = false, defaultValue="25") Integer size) {

        if (size > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page size should be between 1 to 100.");

        StockListResponse response;
        Pageable pageableRequest = PageRequest.of(page, size);
        StocksDetailListSRO stocksListSRO = stockService.getAllStocksPaginated(pageableRequest);
        if (page >= stocksListSRO.getTotalPages() && page!=0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested page is out of bounds.");

        response = converterService.convertToResponseDTO(stocksListSRO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<StockDetailResponse> updateStockById(@ApiParam(value = "",required=true) @PathVariable("stockId") Long stockId, @ApiParam(value = "Update Stock Request"  ) @RequestBody UpdateStockRequestDTO stockRequestDTO) {

        StockDetailResponse response;
        StockDetailSRO stockDetailSRO = stockService.getStockById(stockId);
        if (stockDetailSRO == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stock entity exists for given stockId.");

        stockDetailSRO = stockService.saveStock(converterService.updateStock(stockDetailSRO, stockRequestDTO));
        response = converterService.convertToResponseDTO(stockDetailSRO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
