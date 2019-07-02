package com.rajesh.stocky.v1.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Builder
public class StocksDetailListDTO {

    private Integer size;
    private Integer number;
    private Integer totalPages;
    private Long totalElements;
    private List<StockDetailDTO> stockDetailDTOS;

}
