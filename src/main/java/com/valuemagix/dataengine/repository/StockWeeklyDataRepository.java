package com.valuemagix.dataengine.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.valuemagix.dataengine.model.StockWeeklyData;
import com.valuemagix.dataengine.model.StockWeeklyId;
import com.valuemagix.dataengine.service.WeeklyStockSectorView;

@Repository
public interface StockWeeklyDataRepository extends JpaRepository<StockWeeklyData, StockWeeklyId> {

    boolean existsByIdSymbolAndIdWeekStartDate(
            String symbol, LocalDate weekStartDate);

    List<StockWeeklyData> findByIdSymbolOrderByIdWeekStartDateDesc(
            String symbol);

    @Query("""
        select max(w.id.weekStartDate)
        from StockWeeklyData w
        where w.isCompleteWeek = true
    """)
    LocalDate findLatestCompletedWeek();

    @Query("""
        select max(w.id.weekStartDate)
        from StockWeeklyData w
        where w.id.symbol = :symbol
    """)
    Optional<LocalDate> findLastUpdateDate(@Param("symbol") String symbol);

    @Query("""
        SELECT
            w.id.symbol      AS symbol,
            m.sector         AS sector,
            w.weekEndDate    AS weekEndDate,
            w.high           AS high,
            w.close          AS close,
            w.low           AS low,
            w.open          AS open,
            w.totalVolume    AS totalVolume,
            w.avgDailyVolume AS avgDailyVolume
        FROM StockWeeklyData w
        JOIN StockMaster m
            ON w.id.symbol = m.symbol
        WHERE w.weekEndDate BETWEEN :fromDate AND :toDate
        AND w.isCompleteWeek = true
    """)
    List<WeeklyStockSectorView> findWeeklyDataWithSector(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
