package com.valuemagix.dataengine.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock_master")
public class StockMaster {
    @Id
    @Column(length = 20)
    private String symbol;

    @Column(nullable = false, length = 5)
    private String exchange; // NSE

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String sector;

    @Column(precision = 18, scale = 2)
    private BigDecimal marketCapCr;

    @Column
    private Boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public BigDecimal getMarketCapCr() {
        return marketCapCr;
    }

    public void setMarketCapCr(BigDecimal marketCapCr) {
        this.marketCapCr = marketCapCr;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }  
    
    
}
