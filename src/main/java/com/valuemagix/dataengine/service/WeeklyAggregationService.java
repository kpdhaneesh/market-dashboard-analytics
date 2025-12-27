package com.valuemagix.dataengine.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.valuemagix.dataengine.dto.DailyCandle;
import com.valuemagix.dataengine.helper.WeeklyAccumulator;
import com.valuemagix.dataengine.model.StockMaster;
import com.valuemagix.dataengine.model.StockWeeklyData;
import com.valuemagix.dataengine.model.StockWeeklyId;
import com.valuemagix.dataengine.repository.StockMasterRepository;
import com.valuemagix.dataengine.repository.StockWeeklyDataRepository;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Instrument;

import jakarta.transaction.Transactional;

@Service
public class WeeklyAggregationService {

    private static final Logger log = LoggerFactory.getLogger(WeeklyAggregationService.class);

    private final StockMasterRepository stockMasterRepository;
    private final StockWeeklyDataRepository stockWeeklyDataRepository;
    private final KiteDataService kiteDataService;

    public WeeklyAggregationService(StockMasterRepository stockMasterRepository,
            StockWeeklyDataRepository stockWeeklyDataRepository,
            KiteDataService kiteDataService) {
        this.stockMasterRepository = stockMasterRepository;
        this.stockWeeklyDataRepository = stockWeeklyDataRepository;
        this.kiteDataService = kiteDataService;
    }

    @Transactional
    public void aggregateWeeklyData() throws KiteException, JSONException, IOException {
        List<StockMaster> stocks = stockMasterRepository.findByIsActiveTrue().stream()
                .toList();

        log.info("Started....aggregateWeeklyData");

        Map<String, Long> symbolToToken = kiteDataService.getTokenMap();

        // Write to CSV for later use
        //writeTokenMapToCsv(symbolToToken, "F:/NIFTY/symbol_token_map.csv");

        for (StockMaster stock : stocks) {
            try {
                Long instrumentToken = symbolToToken.get(stock.getSymbol());

                if (instrumentToken == null) {
                    log.info("Token Missing {} - {}", stock.getSymbol());
                    continue;
                }

                // Last updated date for this stock
                LocalDate lastUpdated = stockWeeklyDataRepository.findLastUpdateDate(stock.getSymbol())
                        .orElse(LocalDate.now().minusMonths(12)); // fallback 6 months

                LocalDate today = LocalDate.now();
                List<DailyCandle> dailyCandles = kiteDataService.getDailyCandles(
                        instrumentToken.toString(),
                        Date.valueOf(lastUpdated.plusDays(1)),
                        Date.valueOf(today));

                if (dailyCandles.isEmpty()) {
                    log.info("No new daily data for {}", stock.getSymbol());
                    continue;
                }

                // Sort by date
                dailyCandles.sort(Comparator.comparing(DailyCandle::getDate));

                aggregateAndSaveWeekly(stock.getSymbol(), dailyCandles);

                log.info("completed {}", stock.getSymbol());

            } catch (Exception e) {
                log.error("Error processing stock {}: {}", stock.getSymbol(), e.getMessage(), e);
            }
        }
    }

    public void writeTokenMapToCsv(Map<String, Long> symbolToToken, String filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            // Write header
            writer.write("symbol,instrument_token");
            writer.newLine();

            for (Map.Entry<String, Long> entry : symbolToToken.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }

            writer.flush();
            System.out.println("Token map written to CSV: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void aggregateAndSaveWeekly(String symbol, List<DailyCandle> dailyCandles) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        WeeklyAccumulator acc = null;

        for (DailyCandle d : dailyCandles) {
            LocalDate weekStart = d.getDate().with(DayOfWeek.MONDAY);
            LocalDate weekEnd = d.getDate().with(DayOfWeek.FRIDAY);

            if (acc == null || !acc.getWeekStart().equals(weekStart)) {
                if (acc != null) {
                    saveWeekly(symbol, acc, weekFields);
                }
                acc = new WeeklyAccumulator(d);
                acc.getWeekStart().equals(weekStart); // already set in constructor
                acc.getWeekEnd().equals(weekEnd); // override end date
            } else {
                acc.addDailyCandle(d);
            }
        }

        if (acc != null) {
            saveWeekly(symbol, acc, weekFields);
        }
    }

    private void saveWeekly(String symbol, WeeklyAccumulator acc, WeekFields weekFields) {
        StockWeeklyId id = new StockWeeklyId();
        id.setSymbol(symbol);
        id.setWeekStartDate(acc.getWeekStart());

        StockWeeklyData data = new StockWeeklyData();
        data.setId(id);
        data.setWeekEndDate(acc.getWeekEnd());
        data.setWeekYear(acc.getWeekStart().getYear());
        data.setWeekNumber(acc.getWeekStart().get(weekFields.weekOfWeekBasedYear()));
        data.setIsCompleteWeek(acc.getWeekEnd().isBefore(LocalDate.now()));
        data.setOpen(acc.getOpen());
        data.setHigh(acc.getHigh());
        data.setLow(acc.getLow());
        data.setClose(acc.getClose());
        data.setTotalVolume(acc.getTotalVolume());
        data.setTradingDays(acc.getTradingDays());
        data.setAvgDailyVolume(acc.getAvgDailyVolume());

        stockWeeklyDataRepository.save(data);
        log.info("Saved weekly data: {} {}-{}", symbol, acc.getWeekStart(), acc.getWeekEnd());
    }

}
