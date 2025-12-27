package com.valuemagix.dataengine.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valuemagix.dataengine.model.MarketEvent;

public interface MarketEventRepository extends JpaRepository<MarketEvent, LocalDate> {

     List<MarketEvent> findByTradeDateBetween(LocalDate from, LocalDate to);

}
