package com.valuemagix.dataengine.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock_weekly_data",
       indexes = {
           @Index(name = "idx_week_end", columnList = "weekEndDate"),
           @Index(name = "idx_complete_week", columnList = "isCompleteWeek")
       })
public class StockWeeklyData {

    @EmbeddedId
    private StockWeeklyId id;

    // ─── Time Identity ─────────────────────────────
    private LocalDate weekEndDate;
    private Integer weekYear;
    private Integer weekNumber;
    private Boolean isCompleteWeek;

    // ─── Price ─────────────────────────────────────
    @Column(precision = 10, scale = 2)
    private BigDecimal open;

    @Column(precision = 10, scale = 2)
    private BigDecimal high;

    @Column(precision = 10, scale = 2)
    private BigDecimal low;

    @Column(precision = 10, scale = 2)
    private BigDecimal close;

    // ─── Volume ────────────────────────────────────
    private Long totalVolume;
    private Long avgDailyVolume;
    private Integer tradingDays;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public StockWeeklyId getId() {
        return id;
    }

    public void setId(StockWeeklyId id) {
        this.id = id;
    }

    public LocalDate getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(LocalDate weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public Integer getWeekYear() {
        return weekYear;
    }

    public void setWeekYear(Integer weekYear) {
        this.weekYear = weekYear;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Boolean getIsCompleteWeek() {
        return isCompleteWeek;
    }

    public void setIsCompleteWeek(Boolean isCompleteWeek) {
        this.isCompleteWeek = isCompleteWeek;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public Long getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Long totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Long getAvgDailyVolume() {
        return avgDailyVolume;
    }

    public void setAvgDailyVolume(Long avgDailyVolume) {
        this.avgDailyVolume = avgDailyVolume;
    }

    public Integer getTradingDays() {
        return tradingDays;
    }

    public void setTradingDays(Integer tradingDays) {
        this.tradingDays = tradingDays;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
