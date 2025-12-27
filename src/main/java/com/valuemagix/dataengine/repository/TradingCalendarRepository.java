package com.valuemagix.dataengine.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valuemagix.dataengine.model.TradingCalendar;

public interface TradingCalendarRepository extends JpaRepository<TradingCalendar, LocalDate> {

}
