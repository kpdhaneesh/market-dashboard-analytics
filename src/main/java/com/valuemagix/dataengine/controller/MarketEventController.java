package com.valuemagix.dataengine.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valuemagix.dataengine.dto.MarketEventRequest;
import com.valuemagix.dataengine.model.MarketEvent;
import com.valuemagix.dataengine.service.MarketEventService;

@RestController
@RequestMapping("/api/admin/events")
public class MarketEventController {

    private final MarketEventService eventService;

    public MarketEventController(MarketEventService eventService) {
        this.eventService = eventService;
    }

    // Create or Update event
    @PostMapping
    public ResponseEntity<MarketEvent> saveEvent(@RequestBody MarketEventRequest request) {
        MarketEvent savedEvent =  eventService.saveOrUpdate(request);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(savedEvent);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<MarketEvent>> saveEvents(@RequestBody List<MarketEventRequest> requests) {
        List<MarketEvent> savedEvents = requests.stream()
                                                .map(eventService::saveOrUpdate)
                                                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(savedEvents);
    }

    // Get event by date
    @GetMapping("/{date}")
    public MarketEvent getEvent(@PathVariable LocalDate date) {
        return eventService.getByDate(date);
    }

    // Get events in range
    @GetMapping
    public List<MarketEvent> getEvents(@RequestParam LocalDate from,@RequestParam LocalDate to) {
        return eventService.getBetween(from, to);
    }

}
