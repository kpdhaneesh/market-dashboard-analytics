package com.valuemagix.dataengine.service;

import java.time.LocalDate;
import java.util.List;

import com.valuemagix.dataengine.dto.MarketEventRequest;
import com.valuemagix.dataengine.model.MarketEvent;

public interface MarketEventService {

    public boolean isExpiry(LocalDate date);
    public boolean isBudget(LocalDate date);
    public boolean isRbi(LocalDate date);

    MarketEvent saveOrUpdate(MarketEventRequest request);
    MarketEvent getByDate(LocalDate date);
    List<MarketEvent> getBetween(LocalDate from, LocalDate to);

}
