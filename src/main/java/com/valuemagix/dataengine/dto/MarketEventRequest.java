package com.valuemagix.dataengine.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MarketEventRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate tradeDate;

    private boolean isExpiry;
    private boolean isBudget;
    private boolean isRbi;
    private String eventName;
    
    public LocalDate getTradeDate() {
        return tradeDate;
    }
    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }
    public boolean isExpiry() {
        return isExpiry;
    }
    public void setExpiry(boolean isExpiry) {
        this.isExpiry = isExpiry;
    }
    public boolean isBudget() {
        return isBudget;
    }
    public void setBudget(boolean isBudget) {
        this.isBudget = isBudget;
    }
    public boolean isRbi() {
        return isRbi;
    }
    public void setRbi(boolean isRbi) {
        this.isRbi = isRbi;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    

}
