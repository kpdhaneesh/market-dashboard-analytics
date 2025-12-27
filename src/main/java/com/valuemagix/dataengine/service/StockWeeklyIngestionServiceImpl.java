package com.valuemagix.dataengine.service;

import org.springframework.stereotype.Service;

@Service
public class StockWeeklyIngestionServiceImpl implements StockWeeklyIngestionService {

    @Override
    public void initialLoad(int lookbackDays) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialLoad'");
    }

    @Override
    public void incrementalUpdate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'incrementalUpdate'");
    }

    @Override
    public void updateSingleStock(String symbol, int lookbackDays) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSingleStock'");
    }


}
