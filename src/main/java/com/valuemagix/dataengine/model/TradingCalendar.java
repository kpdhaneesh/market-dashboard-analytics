package com.valuemagix.dataengine.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trading_calendar")
public class TradingCalendar {

    @Id
    private LocalDate tradeDate;

    @Column(nullable = false)
    private Boolean isTradingDay;

    @Column
    private Integer sessionMinutes; // e.g., 375 normal, <375 for half days

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Boolean getIsTradingDay() {
        return isTradingDay;
    }

    public void setIsTradingDay(Boolean isTradingDay) {
        this.isTradingDay = isTradingDay;
    }

    public Integer getSessionMinutes() {
        return sessionMinutes;
    }

    public void setSessionMinutes(Integer sessionMinutes) {
        this.sessionMinutes = sessionMinutes;
    }

}
