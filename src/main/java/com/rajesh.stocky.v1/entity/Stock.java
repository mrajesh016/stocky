package com.rajesh.stocky.v1.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(exclude = {"lastUpdated"}) @ToString
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="stock_id")
    private Long stockId;

    @Column(name="stock_name")
    private String stockName;

    @Column(name="current_price")
    private Double currentPrice;

    @Column(name="last_updated")
    private OffsetDateTime lastUpdated = OffsetDateTime.now();

    public Stock(String stockName, Double currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.lastUpdated = OffsetDateTime.now(Clock.systemDefaultZone());
    }
}
