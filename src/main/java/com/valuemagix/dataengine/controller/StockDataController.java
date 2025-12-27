package com.valuemagix.dataengine.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valuemagix.dataengine.service.StockCsvLoaderService;

@RestController
@RequestMapping("/api/admin/stock")
public class StockDataController {

    private StockCsvLoaderService loaderService;

    public StockDataController(StockCsvLoaderService loaderService) {
        this.loaderService=loaderService;
    }

    @PostMapping("/load-stocks")
    public String uploadCsv() {
        try {
            loaderService.loadCsv();
            return "Stocks loaded successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading CSV: " + e.getMessage();
        }
    }

}
