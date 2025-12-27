package com.valuemagix.dataengine.service;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.valuemagix.dataengine.model.NiftyCandle;
import com.valuemagix.dataengine.repository.NiftyCandleRepository;

@Service
public class HistoricalDataLoaderServiceImpl implements HistoricalDataLoaderService {

    @Value("${nifty.data.base-path}")
    private String basePath;

    private static final Logger log = LoggerFactory.getLogger(HistoricalDataLoaderService.class);

    private static final DateTimeFormatter CSV_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");


    @Autowired
    private NiftyCandleRepository repo;

    @Autowired
    private MarketEventService eventService;

    public void loadBetween(LocalDate from, LocalDate to) {

        LocalDate date = from;

        while (!date.isAfter(to)) {

            Path path = Paths.get(
                    basePath,
                    String.valueOf(date.getYear()),
                    String.format("%02d", date.getMonthValue()),
                    String.format("%02d.csv", date.getDayOfMonth()));

            if (Files.exists(path)) {
                loadCsv(path, date); // delegate
            } else {
                log.warn("Missing file: {}", path);
            }

            date = date.plusDays(1);
        }
    }

    private void loadCsv(Path csvPath, LocalDate tradeDate) {

        List<NiftyCandle> batch = new ArrayList<>();

        boolean expiry=eventService.isExpiry(tradeDate);
        boolean budget=eventService.isBudget(tradeDate);
        boolean rbi=eventService.isRbi(tradeDate);

        try (BufferedReader br = Files.newBufferedReader(csvPath)) {

            br.lines().skip(1).forEach(line -> {

                String[] c = line.split(",");

                ZonedDateTime zdt = ZonedDateTime.parse(c[0], CSV_TIMESTAMP_FORMATTER);
                LocalTime time = zdt.toLocalTime();
                LocalDateTime ts = LocalDateTime.of(zdt.toLocalDate(), time);

                NiftyCandle candle = new NiftyCandle();
                candle.setTs(ts);
                candle.setTradeDate(tradeDate);
                candle.setTradeTime(time);
                candle.setOpen(new BigDecimal(c[1]));
                candle.setHigh(new BigDecimal(c[2]));
                candle.setLow(new BigDecimal(c[3]));
                candle.setClose(new BigDecimal(c[4]));
                candle.setVolume(Long.parseLong(c[5]));

                // enrichment
                candle.setDayOfWeek(tradeDate.getDayOfWeek().getValue());
                candle.setMinuteOfDay(calcMinuteOfDay(time));
                candle.setHourBlock(calcHourBlock(time));
                candle.setExpiry(expiry);
                candle.setBudget(budget);
                candle.setRbi(rbi);

                batch.add(candle);
            });

            repo.saveAll(batch); // idempotent via PK(ts)

            log.info("Loaded {} rows from {}", batch.size(), csvPath);

        } catch (Exception e) {
            log.error("Error loading file {}", csvPath, e);
        }

    }

    private int calcMinuteOfDay(LocalTime time) {

        // Market opens at 09:15
        int minutesFromOpen = (time.getHour() * 60 + time.getMinute())
                - (9 * 60 + 15);

        return minutesFromOpen + 1; // 1..375
    }

    private int calcHourBlock(LocalTime time) {

        if (time.isBefore(LocalTime.of(10, 15)))
            return 1;
        if (time.isBefore(LocalTime.of(11, 15)))
            return 2;
        if (time.isBefore(LocalTime.of(12, 15)))
            return 3;
        if (time.isBefore(LocalTime.of(13, 15)))
            return 4;
        if (time.isBefore(LocalTime.of(14, 15)))
            return 5;
        if (time.isBefore(LocalTime.of(15, 15)))
            return 6;
        return 7; // 15:15–15:29
    }

    public List<NiftyCandle> getCandlesBetween(LocalDateTime from, LocalDateTime to,
                                            Boolean expiry, Boolean budget,Boolean rbi) {
        return repo.findCandles(from, to, expiry, budget, rbi);
    }

}
