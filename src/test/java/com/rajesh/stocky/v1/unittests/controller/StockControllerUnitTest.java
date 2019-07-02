package com.rajesh.stocky.v1.unittests.controller;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.repository.StockRepository;
import com.rajesh.stocky.v1.swagger.api.StocksApi;
import com.rajesh.stocky.v1.swagger.model.CreateStockRequestDTO;
import com.rajesh.stocky.v1.swagger.model.StockDetailResponse;
import com.rajesh.stocky.v1.swagger.model.StockListResponse;
import com.rajesh.stocky.v1.swagger.model.UpdateStockRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockControllerUnitTest {

    @Autowired
    private StocksApi stocksApi;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void testCreateStockSuccess() {
        Stock stock = new Stock("testCreateStock",16.6);
        when(stockRepository.save(stock)).thenReturn(stock);

        CreateStockRequestDTO request = new CreateStockRequestDTO();
        request.setStockName("testCreateStock");
        request.setCurrentPrice(16.6);
        ResponseEntity<StockDetailResponse> response = stocksApi.createStock(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getStockName()).isEqualTo("testCreateStock");
        assertThat(response.getBody().getCurrentPrice()).isEqualTo(16.6);
    }

    @Test
    public void testCreateStockFailNegativePrice() {

        CreateStockRequestDTO request = new CreateStockRequestDTO();
        request.setStockName("testCreateStock");
        request.setCurrentPrice(-16.6);
        Throwable thrown = catchThrowable(() -> stocksApi.createStock(request));
        assertThat(thrown).isOfAnyClassIn(ConstraintViolationException.class);
        assertThat(thrown).hasMessageContaining("currentPrice: must be greater than or equal to 0.0");
    }

    @Test
    public void testCreateStockFailNullPrice() {

        CreateStockRequestDTO request = new CreateStockRequestDTO();
        request.setStockName("testCreateStock");
        Throwable thrown = catchThrowable(() -> stocksApi.createStock(request));
        assertThat(thrown).isOfAnyClassIn(ConstraintViolationException.class);
        assertThat(thrown).hasMessageContaining("currentPrice: must not be null");
    }

    @Test
    public void testCreateStockFailEmptyName() {

        CreateStockRequestDTO request = new CreateStockRequestDTO();
        request.setStockName("");
        request.setCurrentPrice(16.6);
        Throwable thrown = catchThrowable(() -> stocksApi.createStock(request));
        assertThat(thrown).isOfAnyClassIn(ConstraintViolationException.class);
        assertThat(thrown).hasMessageContaining("stockName: size must be between 1 and 100");
    }

    @Test
    public void testCreateStockFailNullName() {

        CreateStockRequestDTO request = new CreateStockRequestDTO();
        request.setCurrentPrice(16.6);
        Throwable thrown = catchThrowable(() -> stocksApi.createStock(request));
        assertThat(thrown).isOfAnyClassIn(ConstraintViolationException.class);
        assertThat(thrown).hasMessageContaining("stockName: must not be null");
    }

      @Test
    public void testDeleteStockWhenStockExists() {
        Stock stock = new Stock("testDeleteStock",16.6);
        stock.setStockId(6428L);
        when(stockRepository.findByStockId(6428L)).thenReturn(Optional.of(stock));

        ResponseEntity<Void> response = stocksApi.deleteStockById(6428L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testDeleteStockWhenStockNotExists() {
        when(stockRepository.findByStockId(6428L)).thenReturn(Optional.empty());
        Throwable thrown = catchThrowable(() -> stocksApi.deleteStockById(6428L));
        assertThat(thrown).hasMessageContaining("No stock entity exists for given stockId.");
    }

    @Test
    public void testDeleteStockWithInvalidId() {
        Throwable thrown = catchThrowable(() -> stocksApi.deleteStockById(-24L));
        assertThat(thrown).isOfAnyClassIn(ConstraintViolationException.class);
        assertThat(thrown).hasMessageContaining("stockId: must be greater than or equal to 0");
    }

    @Test
    public void testGetStockWhenStockExists() {
        Stock stock = new Stock("testGetStock",9.4);
        stock.setStockId(6428L);
        when(stockRepository.findByStockId(6428L)).thenReturn(Optional.of(stock));

        ResponseEntity<StockDetailResponse> response = stocksApi.getStockById(6428L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStockName()).isEqualTo("testGetStock");
        assertThat(response.getBody().getCurrentPrice()).isEqualTo(9.4);
    }

    @Test
    public void testGetStockWhenStockNotExists() {
        when(stockRepository.findByStockId(6428L)).thenReturn(Optional.empty());
        Throwable thrown = catchThrowable(() -> stocksApi.getStockById(6428L));
        assertThat(thrown).hasMessageContaining("No stock entity exists for given stockId.");
    }

    @Test
    public void testGetStockWithInvalidId() {
        Throwable thrown = catchThrowable(() -> stocksApi.getStockById(-72L));
        assertThat(thrown).hasMessageContaining("stockId: must be greater than or equal to 0");
    }

    @Test
    public void testGetStockListSuccess() {
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock("testGetListStock1",16.6);stock1.setStockId(4116L);
        Stock stock2 = new Stock("testGetListStock2",14.2);stock2.setStockId(8462L);
        stockList.add(stock1);stockList.add(stock2);
        Page<Stock> stocksPaginated = new PageImpl<>(stockList);

        Pageable pageableRequest = PageRequest.of(0, 4);
        when(stockRepository.findAll(pageableRequest)).thenReturn(stocksPaginated);

        ResponseEntity<StockListResponse> response = stocksApi.getStocksList(0,4);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTotalPages()).isEqualTo(1);
        assertThat(response.getBody().getTotalElements()).isEqualTo(2);
        assertThat(response.getBody().getStocksDetailList().get(0).getStockId()).isEqualTo(4116);
        assertThat(response.getBody().getStocksDetailList().get(0).getCurrentPrice()).isEqualTo(16.6);
        assertThat(response.getBody().getStocksDetailList().get(0).getStockName()).isEqualTo("testGetListStock1");
        assertThat(response.getBody().getStocksDetailList().get(1).getStockId()).isEqualTo(8462);
        assertThat(response.getBody().getStocksDetailList().get(1).getCurrentPrice()).isEqualTo(14.2);
        assertThat(response.getBody().getStocksDetailList().get(1).getStockName()).isEqualTo("testGetListStock2");
    }

    @Test
    public void testGetStockListPageOutOfBound() {
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock("testGetListStock1",16.6);stock1.setStockId(4116L);
        Stock stock2 = new Stock("testGetListStock2",14.2);stock2.setStockId(8462L);
        stockList.add(stock1);stockList.add(stock2);
        Page<Stock> stocksPaginated = new PageImpl<>(stockList);

        Pageable pageableRequest = PageRequest.of(2, 10);
        when(stockRepository.findAll(pageableRequest)).thenReturn(stocksPaginated);

        Throwable thrown = catchThrowable(() -> stocksApi.getStocksList(2,10));
        assertThat(thrown).hasMessageContaining("Requested page is out of bounds.");
    }

    @Test
    public void testGetStockListInvalidPageSize() {
        Throwable thrown = catchThrowable(() -> stocksApi.getStocksList(2,500));
        assertThat(thrown).hasMessageContaining("getStocksList.size: must be less than or equal to 100");
    }

    @Test
    public void testUpdateStockWhenStockExists() {
        Stock stock = new Stock("testUpdateStock",6.8);
        stock.setStockId(2694L);
        Stock updatedStock = new Stock("testUpdateStock",9.3);
        updatedStock.setStockId(2694L);
        when(stockRepository.findByStockId(2694L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(updatedStock)).thenReturn(updatedStock);

        UpdateStockRequestDTO request = new UpdateStockRequestDTO();
        request.setCurrentPrice(9.3);
        ResponseEntity<StockDetailResponse> response = stocksApi.updateStockById(2694L, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStockName()).isEqualTo("testUpdateStock");
        assertThat(response.getBody().getCurrentPrice()).isEqualTo(9.3);
    }

    @Test
    public void testUpdateStockWhenStockNotExists() {
        UpdateStockRequestDTO request = new UpdateStockRequestDTO();
        request.setCurrentPrice(9.3);
        when(stockRepository.findByStockId(2694L)).thenReturn(Optional.empty());
        Throwable thrown = catchThrowable(() -> stocksApi.updateStockById(2694L, request));
        assertThat(thrown).hasMessageContaining("No stock entity exists for given stockId.");
    }

    @Test
    public void testUpdateStockWithInvalidId() {
        UpdateStockRequestDTO request = new UpdateStockRequestDTO();
        request.setCurrentPrice(9.3);
        Throwable thrown = catchThrowable(() -> stocksApi.updateStockById(-29L,request));
        assertThat(thrown).hasMessageContaining("updateStockById.stockId: must be greater than or equal to 0");
    }

    @Test
    public void testUpdateStockWithInvalidUpdateRequest() {
        UpdateStockRequestDTO request = new UpdateStockRequestDTO();
        request.setCurrentPrice(-9.3);
        Throwable thrown = catchThrowable(() -> stocksApi.updateStockById(16L,request));
        System.out.println(thrown);
        assertThat(thrown).hasMessageContaining("updateStockById.stockRequest.currentPrice: must be greater than or equal to 0.0");
    }

}