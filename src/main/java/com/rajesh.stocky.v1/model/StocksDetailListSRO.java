package com.rajesh.stocky.v1.model;

import java.util.List;

public class StocksDetailListSRO {

    private Integer size;
    private Integer number;
    private Integer totalPages;
    private Long totalElements;
    private List<StockDetailSRO> stockDetailSROs;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<StockDetailSRO> getStockDetailSROs() {
        return stockDetailSROs;
    }

    public void setStockDetailSROs(List<StockDetailSRO> stockDetailSROs) {
        this.stockDetailSROs = stockDetailSROs;
    }

    public StocksDetailListSRO() {
    }

    public StocksDetailListSRO(int size, int number, int totalPages, Long totalElements, List<StockDetailSRO> stockDetailSROs) {
        this.size = size;
        this.number = number;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.stockDetailSROs = stockDetailSROs;
    }

    @Override
    public String toString() {
        return "StocksDetailListSRO{" +
                "size=" + size +
                ", number=" + number +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", stockDetailSROs=" + stockDetailSROs +
                '}';
    }
}
