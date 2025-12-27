package com.valuemagix.dataengine.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface WeeklyStockSectorView {

    String getSymbol();
    String getSector();

    LocalDate getWeekEndDate();

    BigDecimal getHigh();
    BigDecimal getClose();

    BigDecimal getOpen();
    BigDecimal getLow();

    Long getTotalVolume();
    Long getAvgDailyVolume();

}
