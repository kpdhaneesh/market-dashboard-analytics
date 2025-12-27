package com.valuemagix.dataengine.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class StockWeeklyId  implements Serializable {

    private String symbol;

    @Column(name = "week_start_date")
    private LocalDate weekStartDate;

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public LocalDate getWeekStartDate() {
        return weekStartDate;
    }
    public void setWeekStartDate(LocalDate weekStartDate) {
        this.weekStartDate = weekStartDate;
    }
    
}
