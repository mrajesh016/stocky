package com.rajesh.stocky.v1.sro;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Builder
public class StocksDetailListSRO {

    private Integer size;
    private Integer number;
    private Integer totalPages;
    private Long totalElements;
    private List<StockDetailSRO> stockDetailSROs;

}
