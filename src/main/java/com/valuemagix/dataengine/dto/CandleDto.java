package com.valuemagix.dataengine.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.valuemagix.dataengine.model.NiftyCandle;

public class CandleDto {

    private LocalDateTime ts;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private Long volume;
    private Boolean expiry;
    private Boolean budget;
    private Boolean rbi;

    public CandleDto() {}

    public CandleDto(LocalDateTime ts, BigDecimal open, BigDecimal high, BigDecimal low,
                     BigDecimal close, Long volume, Boolean expiry, Boolean budget, Boolean rbi) {
        this.ts = ts;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.expiry = expiry;
        this.budget = budget;
        this.rbi = rbi;
    }

    public static CandleDto fromEntity(NiftyCandle candle) {
        return new CandleDto(
                candle.getTs(),
                candle.getOpen(),
                candle.getHigh(),
                candle.getLow(),
                candle.getClose(),
                candle.getVolume(),
                candle.getExpiry(),
                candle.getBudget(),
                candle.getRbi()
        );
    }

    // Getters and setters
    public LocalDateTime getTs() { return ts; }
    public void setTs(LocalDateTime ts) { this.ts = ts; }
    public BigDecimal getOpen() { return open; }
    public void setOpen(BigDecimal open) { this.open = open; }
    public BigDecimal getHigh() { return high; }
    public void setHigh(BigDecimal high) { this.high = high; }
    public BigDecimal getLow() { return low; }
    public void setLow(BigDecimal low) { this.low = low; }
    public BigDecimal getClose() { return close; }
    public void setClose(BigDecimal close) { this.close = close; }
    public Long getVolume() { return volume; }
    public void setVolume(Long volume) { this.volume = volume; }
    public Boolean getExpiry() { return expiry; }
    public void setExpiry(Boolean expiry) { this.expiry = expiry; }
    public Boolean getBudget() { return budget; }
    public void setBudget(Boolean budget) { this.budget = budget; }
    public Boolean getRbi() { return rbi; }
    public void setRbi(Boolean rbi) { this.rbi = rbi; }

}
