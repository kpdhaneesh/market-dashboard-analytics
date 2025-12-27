package com.valuemagix.dataengine.controller;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valuemagix.dataengine.service.WeeklyAggregationService;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;

@RestController
@RequestMapping("/weekly")
public class WeeklyAggregationController {

    private final WeeklyAggregationService weeklyAggregationService;

    public WeeklyAggregationController(WeeklyAggregationService weeklyAggregationService) {
        this.weeklyAggregationService = weeklyAggregationService;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runWeeklyAggregation() throws KiteException, JSONException, IOException {
        weeklyAggregationService.aggregateWeeklyData();
        return ResponseEntity.ok("Weekly aggregation triggered");
    }

}
