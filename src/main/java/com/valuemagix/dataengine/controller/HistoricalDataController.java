package com.valuemagix.dataengine.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valuemagix.dataengine.dto.CandleDto;
import com.valuemagix.dataengine.dto.CandleQueryRequest;
import com.valuemagix.dataengine.dto.DataLoadRequest;
import com.valuemagix.dataengine.model.NiftyCandle;
import com.valuemagix.dataengine.service.HistoricalDataLoaderService;

@RestController
@RequestMapping("/api/admin/data")
public class HistoricalDataController {

    private final HistoricalDataLoaderService loaderService;

    public HistoricalDataController(HistoricalDataLoaderService loaderService) {
        this.loaderService = loaderService;
    }

    @PostMapping("/load")
    public ResponseEntity<String> loadHistoricalData(@RequestBody DataLoadRequest request) {

        loaderService.loadBetween(request.getFrom(), request.getTo());

        return ResponseEntity.ok(
        "Load triggered from " + request.getFrom() + " to " + request.getTo()
        );
    }

    @PostMapping("/candles/query")
    public ResponseEntity<List<CandleDto>> queryCandles(@RequestBody CandleQueryRequest request) {
        List<NiftyCandle> candles = loaderService.getCandlesBetween(
                request.getFrom(),
                request.getTo(),
                request.getExpiry(),
                request.getBudget(),
                request.getRbi()
        );

        List<CandleDto> dtos = candles.stream()
                                      .map(CandleDto::fromEntity)
                                      .toList();

        return ResponseEntity.ok(dtos);
    }

}
