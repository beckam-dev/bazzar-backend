package com.becksoft.bazzar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, unique = true, updatable = false)
    private Product product;

    @Column(nullable = false)
    private int stock;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Inventory(Product product, int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock inicial no puede ser negativo");
        }
        this.product = product;
        this.stock = stock;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser mayor a 0");
        }
        this.stock += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad a descontar debe ser mayor a 0");
        }
        if (this.stock < amount) {
            throw new IllegalStateException("Stock insuficiente. Stock actual: " + this.stock + ", solicitado: " + amount);
        }
        this.stock -= amount;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}