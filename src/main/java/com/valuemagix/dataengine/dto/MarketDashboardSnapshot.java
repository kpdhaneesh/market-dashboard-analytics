package com.valuemagix.dataengine.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketDashboardSnapshot {

    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer weeks;

    // Market
    private Integer marketStrength;
    private String marketRegime;
    private BreadthSnapshot breadth;
    private Double participationPct;

    // Breakouts
    private Integer breakoutCount;
    private Double breakoutPct;
    private Double breakoutFailureRate;

    // Sector
    private List<SectorSnapshot> sectors;

    // RS Matrix
    private List<RelativeStrengthPoint> rsMatrix;

    private String narrative;

}
