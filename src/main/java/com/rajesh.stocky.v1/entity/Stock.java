package com.rajesh.stocky.v1.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="stock_name")
    private String stockName;

    @Column(name="current_price")
    private Double currentPrice;

    @Column(name="last_updated")
    private OffsetDateTime lastUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public String toString() {
        return "Stock{" +
                "id='" + id + '\'' +
                ", stockName='" + stockName + '\'' +
                ", currentPrice=" + currentPrice +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
