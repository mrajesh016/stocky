package com.rajesh.stocky.v1.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(exclude = {"lastUpdated", "created"}) @ToString
public class StockDetailDTO {

    private Long stockId;
    private String stockName;
    private Double currentPrice;
    private OffsetDateTime lastUpdated;
    private OffsetDateTime created;

    public StockDetailDTO(String stockName, Double currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
    }
}

