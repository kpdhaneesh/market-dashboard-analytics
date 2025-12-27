package com.valuemagix.dataengine.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.valuemagix.dataengine.dto.MarketEventRequest;
import com.valuemagix.dataengine.model.MarketEvent;
import com.valuemagix.dataengine.repository.MarketEventRepository;

@Service
public class MarketEventServiceImpl implements MarketEventService {

    private final MarketEventRepository repo;

    public MarketEventServiceImpl(MarketEventRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean isExpiry(LocalDate date) {
        return repo.findById(date).map(MarketEvent::getIsExpiry).orElse(false);
    }

    @Override
    public boolean isBudget(LocalDate date) {
       return repo.findById(date).map(MarketEvent::getIsBudget).orElse(false);
    }

    @Override
    public boolean isRbi(LocalDate date) {
       return repo.findById(date).map(MarketEvent::getIsRbi).orElse(false);
    }

    @Override
    public MarketEvent saveOrUpdate(MarketEventRequest request) {
        MarketEvent e = new MarketEvent();
        e.setTradeDate(request.getTradeDate());
        e.setIsExpiry(request.isExpiry());
        e.setIsBudget(request.isBudget());
        e.setIsRbi(request.isRbi());
        e.setEventName(request.getEventName());
        return repo.save(e);
    }

    @Override
    public MarketEvent getByDate(LocalDate date) {
        return repo.findById(date).orElse(null);
    }

    @Override
    public List<MarketEvent> getBetween(LocalDate from, LocalDate to) {
         return repo.findByTradeDateBetween(from, to);
    }

}
