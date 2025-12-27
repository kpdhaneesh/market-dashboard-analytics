package com.valuemagix.dataengine.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "market_events")
public class MarketEvent {

    @Id
    private LocalDate tradeDate;

    @Column(nullable = false)
    private Boolean isExpiry = false;

    @Column(nullable = false)
    private Boolean isBudget = false;

    @Column(nullable = false)
    private Boolean isRbi = false;

    @Column
    private String eventName; // optional, e.g., "Budget 2025"

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Boolean getIsExpiry() {
        return isExpiry;
    }

    public void setIsExpiry(Boolean isExpiry) {
        this.isExpiry = isExpiry;
    }

    public Boolean getIsBudget() {
        return isBudget;
    }

    public void setIsBudget(Boolean isBudget) {
        this.isBudget = isBudget;
    }

    public Boolean getIsRbi() {
        return isRbi;
    }

    public void setIsRbi(Boolean isRbi) {
        this.isRbi = isRbi;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    

}
