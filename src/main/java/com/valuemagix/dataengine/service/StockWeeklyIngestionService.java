package com.valuemagix.dataengine.service;

public interface StockWeeklyIngestionService {
    
    void initialLoad(int lookbackDays);

    void incrementalUpdate();

    void updateSingleStock(String symbol, int lookbackDays);
    
}
