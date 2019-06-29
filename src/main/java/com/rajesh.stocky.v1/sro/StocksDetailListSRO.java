package com.rajesh.stocky.v1.sro;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(exclude = {"lastUpdated"}) @ToString
public class StocksDetailListSRO {

    private Integer size;
    private Integer number;
    private Integer totalPages;
    private Long totalElements;
    private List<StockDetailSRO> stockDetailSROs;

    public StocksDetailListSRO(int size, int number, int totalPages, Long totalElements, List<StockDetailSRO> stockDetailSROs) {
        this.size = size;
        this.number = number;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.stockDetailSROs = stockDetailSROs;
    }
}
