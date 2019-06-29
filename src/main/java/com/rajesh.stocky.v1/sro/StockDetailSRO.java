package com.rajesh.stocky.v1.sro;

import lombok.*;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(exclude = {"lastUpdated"}) @ToString
public class StockDetailSRO {

    private Long stockId;
    private String stockName;
    private Double currentPrice;
    private OffsetDateTime lastUpdated;

    public StockDetailSRO(String stockName, Double currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.lastUpdated = OffsetDateTime.now();
    }
}

