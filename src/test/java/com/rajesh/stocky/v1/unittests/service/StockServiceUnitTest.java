package com.rajesh.stocky.v1.unittests.service;

import com.rajesh.stocky.v1.entity.Stock;
import com.rajesh.stocky.v1.dto.StockDetailDTO;
import com.rajesh.stocky.v1.dto.StocksDetailListDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceUnitTest {

    @Autowired
    private IStockService stockService;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void testGetStockByIdExists() {
        Stock stock = new Stock("testStock",16.6);
        stock.setStockId(4116L);
        when(stockRepository.findByStockId(4116L)).thenReturn(Optional.of(stock));

        StockDetailDTO detailDTO = stockService.getStockById(4116L).orElse(null);
        assertThat(detailDTO.getStockName()).isEqualTo("testStock");
        assertThat(detailDTO.getCurrentPrice()).isEqualTo(16.6);
        assertThat(detailDTO.getStockId()).isEqualTo(4116L);
    }

    @Test
    public void testGetStockByIdNotExists() {
        when(stockRepository.findByStockId(4116L)).thenReturn(Optional.empty());
        assertThat(stockService.getStockById(4116L).isPresent()).isEqualTo(false);
    }

    @Test
    public void testGetAllStocksPaginated() {
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock("testStock1",16.6);stock1.setStockId(4116L);
        Stock stock2 = new Stock("testStock2",14.2);stock2.setStockId(8462L);
        stockList.add(stock1);stockList.add(stock2);
        Page<Stock> stocksPaginated = new PageImpl<>(stockList);

        Pageable pageableRequest = PageRequest.of(0, 15);
        when(stockRepository.findAll(pageableRequest)).thenReturn(stocksPaginated);
        StocksDetailListDTO responseDTO = stockService.getAllStocksPaginated(pageableRequest);

        List<StockDetailDTO> stockDetailDTOS = new ArrayList<>();
        StockDetailDTO stockDTO1 = new StockDetailDTO("testStock1",16.6);stockDTO1.setStockId(4116L);
        StockDetailDTO stockDTO2 = new StockDetailDTO("testStock2",14.2);stockDTO2.setStockId(8462L);
        stockDetailDTOS.add(stockDTO1);
        stockDetailDTOS.add(stockDTO2);

        assertThat(responseDTO.getTotalPages()).isEqualTo(1);
        assertThat(responseDTO.getTotalElements()).isEqualTo(2);
        assertThat(responseDTO.getStockDetailDTOS()).isEqualTo(stockDetailDTOS);
    }

    @Test
    public void testSaveStock() {
        Stock stock = new Stock("testStock",16.6);
        stock.setStockId(4116L);
        when(stockRepository.save(stock)).thenReturn(stock);

        StockDetailDTO stockDetailDTO = stockService.saveOrUpdateStock(stock);
        assertThat(stockDetailDTO.getStockName()).isEqualTo("testStock");
        assertThat(stockDetailDTO.getCurrentPrice()).isEqualTo(16.6);
        assertThat(stockDetailDTO.getStockId()).isEqualTo(4116L);
    }


}