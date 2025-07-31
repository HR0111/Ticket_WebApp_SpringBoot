package com.hemant.tickets.controller;

import com.hemant.tickets.dto.ListPublishedEventResponseDto;
import com.hemant.tickets.entity.Event;
import com.hemant.tickets.mappers.EventMapper;
import com.hemant.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable){

        Page<Event> event;

        if( q != null && ! q.trim().isEmpty()){
            event = eventService.searchPublishedEvents(q,pageable);
        }else{
            event = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(event.map(eventMapper::toListPublishedEventResponseDto));


        //Page<Event> events = eventService.listPublishedEvents(pageable);
        //Page<ListPublishedEventResponseDto> map = events.map(eventMapper::toListPublishedEventResponseDto);


    }



}
