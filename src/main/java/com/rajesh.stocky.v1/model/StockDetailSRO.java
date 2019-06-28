package com.rajesh.stocky.v1.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class StockDetailSRO {

    private Long stockId;
    private String stockName;
    private Double currentPrice;
    private OffsetDateTime lastUpdated;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public StockDetailSRO() {
    }

    public StockDetailSRO(String stockName, Double currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.lastUpdated = OffsetDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDetailSRO detailSRO = (StockDetailSRO) o;
        return Objects.equals(stockId, detailSRO.stockId) &&
                Objects.equals(stockName, detailSRO.stockName) &&
                Objects.equals(currentPrice, detailSRO.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, stockName, currentPrice);
    }

    @Override
    public String toString() {
        return "StockDetailSRO{" +
                "stockId=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", currentPrice=" + currentPrice +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}

