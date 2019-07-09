package com.rajesh.stocky.v1.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "stock")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(exclude = {"lastUpdated", "created"}) @ToString
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="stock_id")
    private Long stockId;

    @Column(name="stock_name")
    private String stockName;

    @Column(name="current_price")
    private Double currentPrice;

    @Column(name="last_updated")
    @UpdateTimestamp
    private OffsetDateTime lastUpdated;

    @Column(name="created")
    @CreationTimestamp
    private OffsetDateTime created;

    public Stock(String stockName, Double currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
    }
}
