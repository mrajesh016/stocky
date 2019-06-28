package com.rajesh.stocky.v1.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="stockId")
    private Long stockId;

    @Column(name="stock_name")
    private String stockName;

    @Column(name="current_price")
    private Double currentPrice;

    @Column(name="last_updated")
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

    public Stock() {
        this.lastUpdated = OffsetDateTime.now(Clock.systemDefaultZone());
    }

    public Stock(String stockName, Double currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.lastUpdated = OffsetDateTime.now(Clock.systemDefaultZone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(stockId, stock.stockId) &&
                Objects.equals(stockName, stock.stockName) &&
                Objects.equals(currentPrice, stock.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, stockName, currentPrice);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId='" + stockId + '\'' +
                ", stockName='" + stockName + '\'' +
                ", currentPrice=" + currentPrice +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
