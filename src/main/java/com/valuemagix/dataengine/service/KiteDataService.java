package com.valuemagix.dataengine.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.valuemagix.dataengine.dto.DailyCandle;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.Instrument;

@Service
public class KiteDataService {

    private final KiteConnect kiteConnect;

    public KiteDataService(KiteConnect kiteConnect) {
        this.kiteConnect = kiteConnect;
    }

    public Map<String, Long> getTokenMap() throws JSONException, IOException, KiteException{
        List<Instrument> instruments = kiteConnect.getInstruments("NSE");
        
        return instruments.stream()
            .collect(Collectors.toMap(Instrument::getTradingsymbol, Instrument::getInstrument_token));
    }

    public List<DailyCandle> getDailyCandles(String instrumentToken,
                                             Date from,
                                             Date to) throws KiteException, JSONException, IOException {

        HistoricalData historicalData = kiteConnect.getHistoricalData(
                from,
                to,
                instrumentToken,
                "day",   // daily
                false,   // continuous (false for equities)
                false    // oi not needed
        );

        return historicalData.dataArrayList.stream().map(h -> {
            String ts = h.timeStamp.replaceAll("(\\+|\\-)(\\d{2})(\\d{2})$", "$1$2:$3");
            OffsetDateTime odt = OffsetDateTime.parse(ts, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            LocalDate date = odt.toLocalDate();
            return new DailyCandle(
                    date,
                    BigDecimal.valueOf(h.open),
                    BigDecimal.valueOf(h.high),
                    BigDecimal.valueOf(h.low),
                    BigDecimal.valueOf(h.close),
                    h.volume
            );
        }).collect(Collectors.toList());
    }

}
