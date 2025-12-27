package com.valuemagix.dataengine.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.valuemagix.dataengine.model.NiftyCandle;

public interface HistoricalDataLoaderService {

    public void loadBetween(LocalDate from, LocalDate to);
    
    public List<NiftyCandle> getCandlesBetween(LocalDateTime from, LocalDateTime to,
                                           Boolean expiry, Boolean budget,Boolean rbi);
} 
