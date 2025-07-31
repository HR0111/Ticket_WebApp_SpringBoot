package com.hemant.tickets.controller;


import com.hemant.tickets.dto.*;
import com.hemant.tickets.entity.CreateEventRequest;
import com.hemant.tickets.entity.Event;
import com.hemant.tickets.entity.UpdateEventRequest;
import com.hemant.tickets.mappers.EventMapper;
import com.hemant.tickets.services.EventService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(@AuthenticationPrincipal Jwt jwt, @Valid
                @RequestBody CreateEventRequestDto createEventRequestDto){

        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);

        UUID userId = UUID.fromString(jwt.getSubject());
        Event createdEvent = eventService.createEvent(userId , createEventRequest);

        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);

        return new ResponseEntity<>(createEventResponseDto , HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(@AuthenticationPrincipal Jwt jwt,
      @PathVariable UUID eventId,@Valid @RequestBody UpdateEventRequestDto updateEventRequestDto){

        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);

        UUID userId = UUID.fromString(jwt.getSubject());
        Event updatedEvent = eventService.updateEventForOrganizer(userId ,eventId, updateEventRequest);

        UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDto(updatedEvent);

//        return new ResponseEntity<>(updateEventResponseDto , HttpStatus.CREATED);
            return ResponseEntity.ok(updateEventResponseDto);
    }



    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(@AuthenticationPrincipal Jwt jwt, Pageable pageable){


        UUID userId = parseUserId(jwt);
        Page<Event> events = eventService.listEventForOrganizer(userId, pageable);

        return ResponseEntity.ok(events.map(eventMapper::toListEventResponseDto));

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetEventDetailResponseDto> getEventDetail(@AuthenticationPrincipal Jwt jwt , @PathVariable UUID eventId){

        UUID userId = parseUserId(jwt);
        return eventService.getEventForOrganizer(userId, eventId)
                .map(eventMapper::toGetEventDetailResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt , @PathVariable UUID eventId
    ){
        UUID userId = parseUserId(jwt);
        eventService.deleteEventForOrganizer(userId , eventId);
        return ResponseEntity.noContent().build();
    }

    private UUID parseUserId(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }





}
