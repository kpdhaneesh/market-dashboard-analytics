package com.valuemagix.dataengine.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nifty_1m_candles")
public class NiftyCandle {

    @Id
    private LocalDateTime ts; // primary key to prevent duplicates

    @Column(nullable = false)
    private LocalDate tradeDate;

    @Column(nullable = false)
    private LocalTime tradeTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal open;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal high;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal low;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal close;

    @Column(nullable = false)
    private Long volume;

    // Enrichment / context
    @Column(nullable = false)
    private Integer dayOfWeek; // 1=Mon .. 7=Sun

    @Column(nullable = false)
    private Integer minuteOfDay; // 1..375

    @Column(nullable = false)
    private Integer hourBlock; // 1..7

    @Column(nullable = false)
    private Boolean expiry = false;

    @Column(nullable = false)
    private Boolean budget = false;

    @Column(nullable = false)
    private Boolean rbi = false;

    public LocalDateTime getTs() {
        return ts;
    }

    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public LocalTime getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(LocalTime tradeTime) {
        this.tradeTime = tradeTime;
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

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getMinuteOfDay() {
        return minuteOfDay;
    }

    public void setMinuteOfDay(Integer minuteOfDay) {
        this.minuteOfDay = minuteOfDay;
    }

    public Integer getHourBlock() {
        return hourBlock;
    }

    public void setHourBlock(Integer hourBlock) {
        this.hourBlock = hourBlock;
    }

    public Boolean getExpiry() {
        return expiry;
    }

    public void setExpiry(Boolean expiry) {
        this.expiry = expiry;
    }

    public Boolean getBudget() {
        return budget;
    }

    public void setBudget(Boolean budget) {
        this.budget = budget;
    }

    public Boolean getRbi() {
        return rbi;
    }

    public void setRbi(Boolean rbi) {
        this.rbi = rbi;
    }

}
