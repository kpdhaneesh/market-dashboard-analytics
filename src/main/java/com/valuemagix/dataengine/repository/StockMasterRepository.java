package com.valuemagix.dataengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valuemagix.dataengine.model.StockMaster;

public interface StockMasterRepository  extends JpaRepository<StockMaster, String> {

    List<StockMaster> findByIsActiveTrue();
}
