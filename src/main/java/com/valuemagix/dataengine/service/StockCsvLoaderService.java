package com.valuemagix.dataengine.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import com.valuemagix.dataengine.model.StockMaster;
import com.valuemagix.dataengine.repository.StockMasterRepository;

import jakarta.annotation.PostConstruct;

@Service
public class StockCsvLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(StockCsvLoaderService.class);

    private final StockMasterRepository stockMasterRepo;

    public StockCsvLoaderService(StockMasterRepository stockMasterRepo) {
        this.stockMasterRepo = stockMasterRepo;
    }

    @Value("${stock.csv.path}")
    private Resource csvFile; // inject CSV as Resource
    
    public void loadCsv() throws Exception {
        try (InputStream is = csvFile.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true; // skip header
                    continue;
                }

                String[] cols = line.split(",", -1);
                if (cols.length < 5)
                    continue;

                String symbol = cols[0].trim();
                String name = cols[1].trim();
                String exchange = cols[2].trim();
                String sector = cols[3].trim();
                BigDecimal marketCapCr = cols[4].isEmpty() ? BigDecimal.ZERO : new BigDecimal(cols[4]);

                StockMaster stock = stockMasterRepo.findById(symbol).orElse(new StockMaster());
                stock.setSymbol(symbol);
                stock.setName(name);
                stock.setExchange(exchange);
                stock.setSector(sector);
                stock.setMarketCapCr(marketCapCr);
                stock.setIsActive(true);

                stockMasterRepo.save(stock);

                // Log each record
                logger.info("Saved StockMaster: symbol={}, name={}, exchange={}, sector={}, marketCapCr={}",
                        symbol, name, exchange, sector, marketCapCr);
            }
        }
    }

}
