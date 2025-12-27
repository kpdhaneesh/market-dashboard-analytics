package com.valuemagix.dataengine.helper;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.valuemagix.dataengine.dto.DailyCandle;

public class WeeklyAccumulator {
    
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private long totalVolume = 0;
    private int tradingDays = 0;

    public WeeklyAccumulator(DailyCandle d) {
        this.weekStart = d.getDate();
        this.weekEnd = d.getDate();
        this.open = d.getOpen();
        this.high = d.getHigh();
        this.low = d.getLow();
        this.close = d.getClose();
        this.totalVolume = d.getVolume();
        this.tradingDays = 1;
    }

    public void addDailyCandle(DailyCandle d) {
        if (d.getDate().isBefore(weekStart)) {
            weekStart = d.getDate();
            open = d.getOpen();
        }
        if (d.getDate().isAfter(weekEnd)) {
            weekEnd = d.getDate();
            close = d.getClose();
        }

        if (d.getHigh().compareTo(high) > 0) high = d.getHigh();
        if (d.getLow().compareTo(low) < 0) low = d.getLow();

        totalVolume += d.getVolume();
        tradingDays++;
    }

    // Getters
    public LocalDate getWeekStart() { return weekStart; }
    public LocalDate getWeekEnd() { return weekEnd; }
    public BigDecimal getOpen() { return open; }
    public BigDecimal getHigh() { return high; }
    public BigDecimal getLow() { return low; }
    public BigDecimal getClose() { return close; }
    public long getTotalVolume() { return totalVolume; }
    public int getTradingDays() { return tradingDays; }
    public long getAvgDailyVolume() { return tradingDays > 0 ? totalVolume / tradingDays : 0; }
    
}
