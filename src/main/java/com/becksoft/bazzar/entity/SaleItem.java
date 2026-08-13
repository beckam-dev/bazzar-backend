package com.becksoft.bazzar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "sale_items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false, updatable = false)
    private Sale sale;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;
    @Setter
    @Column(nullable = false)
    private int quantity = 0;
    @Setter
    private BigDecimal unitPrice;
    @Setter
    private BigDecimal subtotal;

    public SaleItem(Sale sale, Product product, Price price, int quantity) {
        this.sale = sale;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

}
