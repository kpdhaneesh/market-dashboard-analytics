package com.valuemagix.dataengine.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RelativeStrengthPoint {

    private String sector;
    private Double rsLevel;
    private Double rsMomentum;

    public RelativeStrengthPoint(String sector, Double rsLevel, Double rsMomentum) {
        this.sector = sector;
        this.rsLevel = rsLevel;
        this.rsMomentum = rsMomentum;
    }

}
