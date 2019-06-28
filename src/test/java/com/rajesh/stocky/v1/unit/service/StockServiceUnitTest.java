package com.rajesh.stocky.v1.unit.service;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.model.StockDetailSRO;
import com.rajesh.stocky.v1.model.StocksDetailListSRO;
import com.rajesh.stocky.v1.repository.StockRepository;
import com.rajesh.stocky.v1.service.IStockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceUnitTest {
    @Autowired
    private IStockService stockService;
    //
    // MockBean is the annotation provided by Spring that wraps mockito one
    // Annotation that can be used to add mocks to a Spring ApplicationContext.
    // If any existing single bean of the same type defined in the context will be replaced by the mock, if no existing bean is defined a new one will be added.
    @MockBean
    private StockRepository stockRepository;

    @Test
    public void testGetStocksById() throws IOException {
        Stock stock = new Stock("testStock",16.6);
        stock.setStockId(4116L);
        when(stockRepository.findByStockId(4116L)).thenReturn(stock);

        StockDetailSRO detailSRO = stockService.getStockById(4116L);
        assertThat(detailSRO.getStockName()).isEqualTo("testStock");
        assertThat(detailSRO.getCurrentPrice()).isEqualTo(16.6);
        assertThat(detailSRO.getStockId()).isEqualTo(4116L);
    }

    @Test
    public void testGetAllStocksPaginated() throws IOException {
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock("testStock1",16.6);stock1.setStockId(4116L);
        Stock stock2 = new Stock("testStock2",14.2);stock2.setStockId(8462L);
        stockList.add(stock1);stockList.add(stock2);
        Page<Stock> stocksPaginated = new PageImpl<>(stockList);

        Pageable pageableRequest = PageRequest.of(0, 15);
        when(stockRepository.findAll(pageableRequest)).thenReturn(stocksPaginated);
        StocksDetailListSRO responseSRO = stockService.getAllStocksPaginated(pageableRequest);

        List<StockDetailSRO> stockDetailSROs = new ArrayList<>();
        StockDetailSRO stockSRO1 = new StockDetailSRO("testStock1",16.6);stockSRO1.setStockId(4116L);
        StockDetailSRO stockSRO2 = new StockDetailSRO("testStock2",14.2);stockSRO2.setStockId(8462L);
        stockDetailSROs.add(stockSRO1);stockDetailSROs.add(stockSRO2);

        System.out.println(responseSRO);
        assertThat(responseSRO.getTotalPages()).isEqualTo(1);
        assertThat(responseSRO.getTotalElements()).isEqualTo(2);
        assertThat(responseSRO.getStockDetailSROs()).isEqualTo(stockDetailSROs);
    }


}