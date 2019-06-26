package com.rajesh.stocky.v1.controller;

import com.rajesh.stocky.v1.StocksApi;
import com.rajesh.stocky.v1.converter.ConverterService;
import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajesh.stocky.v1.service.IStockService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<StockDTO> createStock(@ApiParam(value = "Stock to create"  )  @Valid @RequestBody CreateStockRequestDTO createStockRequest) {
        String accept = request.getHeader("Accept");
        Stock stock = null;
        StockDTO response;
        if (accept != null && accept.contains("application/json"))
            stock = stockService.saveStock(converterService.convertToEntity(createStockRequest));

        response = converterService.convertToDto(stock);
        response.setStatusCode(201);
        response.setMessage("Stock Successfully Created at /api/stock/"+stock.getId());
        return new ResponseEntity<StockDTO>(response, HttpStatus.CREATED );
    }

    @Transactional
    public ResponseEntity<StockDTO> deleteStockById(@ApiParam(value = "",required=true) @PathVariable("stockId") Integer stockId) {
        String accept = request.getHeader("Accept");
        Stock stock = null;
        StockDTO response = new StockDTO();

        if (accept != null && accept.contains("application/json")) {
            stock = stockService.getStockById(stockId);
            if (stock == null) {
                response.setStatusCode(404);
                response.setMessage("No Stock exists with the given ID");
                return new ResponseEntity<StockDTO>(response, HttpStatus.NOT_FOUND );
            }
            stockService.deleteStock(stock);
        }

        response = converterService.convertToDto(stock);
        response.setStatusCode(200);
        response.setMessage("Successfully deleted stock at /api/stock/"+stockId);
        return new ResponseEntity<StockDTO>(response, HttpStatus.OK );
    }

    public ResponseEntity<StockDTO> getStockById(@ApiParam(value = "",required=true) @PathVariable("stockId") Integer stockId) {
        String accept = request.getHeader("Accept");
        Stock stock = null;
        StockDTO response = new StockDTO();

        if (accept != null && accept.contains("application/json")) {
            stock = stockService.getStockById(stockId);
            if (stock == null) {
                response.setStatusCode(404);
                response.setMessage("No Stock exists with the given ID");
                return new ResponseEntity<StockDTO>(response, HttpStatus.NOT_FOUND );
            }
        }

        response = converterService.convertToDto(stock);
        response.setStatusCode(200);
        response.setMessage("Successfully found stock at /api/stock/"+stockId);
        return new ResponseEntity<StockDTO>(response, HttpStatus.OK);
    }

    public ResponseEntity<StockListResponseDTO> getStocksList(@Min(0)@ApiParam(value = "0 based page index.", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page, @Min(1)@ApiParam(value = "size of the page to be returned.", defaultValue = "25") @Valid @RequestParam(value = "size", required = false, defaultValue="25") Integer size) {
        String accept = request.getHeader("Accept");
        StockListResponseDTO response = new StockListResponseDTO();
        List<StockDTO> stocksDTOList = new ArrayList<StockDTO>();

        if (accept != null && accept.contains("application/json")) {
            Pageable pageableRequest = PageRequest.of(page, size);
            Page<Stock> stocks = stockService.findAll(pageableRequest);

            if (page > stocks.getTotalPages()) {
                response.setStatusCode(404);
                response.setMessage("The page asked for is out of Bounds.");
                return new ResponseEntity<StockListResponseDTO>(response, HttpStatus.NOT_FOUND);
            }

            List<Stock> stockList = stocks.getContent();
            for (Stock stock : stockList) {
                StockDTO stockDTO = converterService.convertToDto(stock);
                stocksDTOList.add(stockDTO);
            }
            response.setStatusCode(200);
            response.setMessage("Successful response of stocks list with pagination");
            response.setStocksList(stocksDTOList);
            response.setNumber(stocks.getNumber());
            response.setSize(stocks.getSize());
            response.setTotalPages(stocks.getTotalPages());
            response.setTotalElements(stocks.getTotalElements());
        }

        return new ResponseEntity<StockListResponseDTO>(response, HttpStatus.OK);
    }

    public ResponseEntity<StockDTO> updateStockById(@ApiParam(value = "",required=true) @PathVariable("stockId") Integer stockId, @ApiParam(value = "Update Stock Request"  ) @RequestBody UpdateStockRequestDTO stockRequestDTO) {
        String accept = request.getHeader("Accept");
        Stock stock = null;
        StockDTO response = new StockDTO();

        if (accept != null && accept.contains("application/json")) {
            if(stockRequestDTO.getCurrentPrice() == null && stockRequestDTO.getName()==null){
                response.setStatusCode(400);
                response.setMessage("Bad Request: Both price and name can't be null.");
                return new ResponseEntity<StockDTO>(response, HttpStatus.BAD_REQUEST);
            }

            stock = stockService.getStockById(stockId);
            if (stock == null){
                response.setStatusCode(404);
                response.setMessage("No Stock exists with the given ID");
                return new ResponseEntity<StockDTO>(response, HttpStatus.NOT_FOUND );
            }
            stock = stockService.saveStock(converterService.updateStock(stock, stockRequestDTO));
        }

        response = converterService.convertToDto(stock);
        response.setStatusCode(200);
        response.setMessage("Successfully updated stock at /api/stock/"+stockId);
        return new ResponseEntity<StockDTO>(response, HttpStatus.OK);
    }

}
