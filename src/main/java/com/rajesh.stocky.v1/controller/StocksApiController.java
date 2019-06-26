package com.rajesh.stocky.v1.controller;

import com.rajesh.stocky.v1.StocksApi;
import com.rajesh.stocky.v1.converter.ConverterService;
import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.model.StockDTO;
import com.rajesh.stocky.v1.model.StockListResponseDTO;
import com.rajesh.stocky.v1.model.StockRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajesh.stocky.v1.service.IStockService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;

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

    public ResponseEntity<StockDTO> createStock(@ApiParam(value = "Stock to create"  )  @Valid @RequestBody StockRequestDTO createStockRequest) {
        String accept = request.getHeader("Accept");
        Stock stock = null;
        StockDTO response = new StockDTO();
        System.out.println(" Stock Request :"+ createStockRequest.toString());
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
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StockListResponseDTO>(objectMapper.readValue("\"\"", StockListResponseDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<StockListResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StockListResponseDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<StockDTO> updateStockById(@ApiParam(value = "",required=true) @PathVariable("stockId") Integer stockId, @ApiParam(value = "Update Stock Request"  ) @RequestBody StockRequestDTO stockRequestDTO) {
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
