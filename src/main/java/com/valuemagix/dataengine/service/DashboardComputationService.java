package com.valuemagix.dataengine.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.valuemagix.dataengine.dto.BreadthSnapshot;
import com.valuemagix.dataengine.dto.MarketDashboardSnapshot;
import com.valuemagix.dataengine.model.StockWeeklyData;
import com.valuemagix.dataengine.repository.StockWeeklyDataRepository;

@Service
public class DashboardComputationService {

    private final StockWeeklyDataRepository repository;

    public DashboardComputationService(StockWeeklyDataRepository repository) {
        this.repository = repository;
    }

    public MarketDashboardSnapshot compute(LocalDate from, LocalDate to) {

        List<WeeklyStockSectorView> data = repository.findWeeklyDataWithSector(from, to);

        if (data.isEmpty()) {
            throw new IllegalStateException("No data for selected range");
        }

        int weeks = (int) ChronoUnit.WEEKS.between(from, to) + 1;

        // ---------------- Market Strength ----------------
        long adv = data.stream().filter(d -> d.getClose().compareTo(d.getOpen()) > 0).count();
        long dec = data.stream().filter(d -> d.getClose().compareTo(d.getOpen()) < 0).count();
        long flat = data.size() - adv - dec;

        int marketStrength = (int) (adv * 100.0 / data.size());

        String regime =
                marketStrength > 60 ? "TRENDING" :
                marketStrength < 40 ? "DISTRIBUTION" :
                "RANGE";

        BreadthSnapshot breadth = new BreadthSnapshot(
                adv * 100.0 / data.size(),
                dec * 100.0 / data.size(),
                flat * 100.0 / data.size()
        );

        double participation = data.stream()
                .filter(d -> d.getTotalVolume() > d.getAvgDailyVolume() * 1.2)
                .count() * 100.0 / data.size();

         return MarketDashboardSnapshot.builder()
                .fromDate(from)
                .toDate(to)
                .weeks(weeks)
                .marketStrength(marketStrength)
                .marketRegime(regime)
                .breadth(breadth)
                .participationPct(participation)
                .build();

    }

}
