package com.rajesh.stocky.v1.sro;

import lombok.*;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(exclude = {"lastUpdated", "created"}) @ToString
public class StockDetailSRO {

    private Long stockId;
    private String stockName;
    private Double currentPrice;
    private OffsetDateTime lastUpdated;
    private OffsetDateTime created;

    public StockDetailSRO(String stockName, Double currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
    }
}

