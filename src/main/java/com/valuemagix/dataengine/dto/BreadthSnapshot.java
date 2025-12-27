package com.valuemagix.dataengine.dto;

public class BreadthSnapshot {

    private Double advancersPct;
    private Double declinersPct;
    private Double flatPct;

    public BreadthSnapshot(Double advancersPct, Double declinersPct, Double flatPct) {
        this.advancersPct = advancersPct;
        this.declinersPct = declinersPct;
        this.flatPct = flatPct;
    }

}
