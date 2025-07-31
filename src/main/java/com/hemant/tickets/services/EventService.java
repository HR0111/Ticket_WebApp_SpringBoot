package com.hemant.tickets.services;


import com.hemant.tickets.entity.CreateEventRequest;
import com.hemant.tickets.entity.Event;
import com.hemant.tickets.entity.UpdateEventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {

    Event createEvent(UUID organizerId , CreateEventRequest createEventRequest);
    Page<Event> listEventForOrganizer(UUID organizerId , Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId , UUID eventId);

    Event updateEventForOrganizer(UUID organizerId , UUID eventId , UpdateEventRequest updateEventRequest);
    
    void deleteEventForOrganizer(UUID organizerId, UUID id);

}
