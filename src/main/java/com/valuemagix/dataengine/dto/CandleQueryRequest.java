package com.valuemagix.dataengine.dto;

import java.time.LocalDateTime;

public class CandleQueryRequest {

    private LocalDateTime from;
    private LocalDateTime to;
    private Boolean expiry;
    private Boolean budget;
    private Boolean rbi;

    public LocalDateTime getFrom() { return from; }
    public void setFrom(LocalDateTime from) { this.from = from; }
    public LocalDateTime getTo() { return to; }
    public void setTo(LocalDateTime to) { this.to = to; }
    public Boolean getExpiry() { return expiry; }
    public void setExpiry(Boolean expiry) { this.expiry = expiry; }
    public Boolean getBudget() { return budget; }
    public void setBudget(Boolean budget) { this.budget = budget; }
    public Boolean getRbi() { return rbi; }
    public void setRbi(Boolean rbi) { this.rbi = rbi; }

}
