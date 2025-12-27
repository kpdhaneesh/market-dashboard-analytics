package com.valuemagix.dataengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valuemagix.dataengine.model.IngestionStatus;

public interface IngestionStatusRepository extends JpaRepository<IngestionStatus, String> {

}
