package com.valuemagix.dataengine.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.valuemagix.dataengine.model.NiftyCandle;

public interface NiftyCandleRepository extends JpaRepository<NiftyCandle, LocalDateTime> {

    @Query("""
                SELECT c FROM NiftyCandle c
                WHERE c.ts BETWEEN :from AND :to
                AND (:expiry IS NULL OR c.expiry = :expiry)
                AND (:budget IS NULL OR c.budget = :budget)
                AND (:rbi IS NULL OR c.rbi = :rbi)
                ORDER BY c.ts ASC
            """)
    List<NiftyCandle> findCandles(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("expiry") Boolean expiry,
            @Param("budget") Boolean budget,
            @Param("rbi") Boolean rbi);

}
