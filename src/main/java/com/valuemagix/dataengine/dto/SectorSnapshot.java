package com.valuemagix.dataengine.dto;

public class SectorSnapshot {

    private String sector;
    private Integer breakoutCount;
    private Double strength;
    private Double volumeMultiple;

    public SectorSnapshot(String sector, Integer breakoutCount, Double strength, Double volumeMultiple) {
        this.sector = sector;
        this.breakoutCount = breakoutCount;
        this.strength = strength;
        this.volumeMultiple = volumeMultiple;
    }

}
