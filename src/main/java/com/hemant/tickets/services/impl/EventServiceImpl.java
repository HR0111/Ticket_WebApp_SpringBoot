package com.hemant.tickets.services.impl;

import com.hemant.tickets.domains.EventStatusEnum;
import com.hemant.tickets.entity.*;
import com.hemant.tickets.exceptions.EventNotFoundException;
import com.hemant.tickets.exceptions.EventUpdateException;
import com.hemant.tickets.exceptions.TicketTypeNotFoundException;
import com.hemant.tickets.exceptions.UserNotFoundException;
import com.hemant.tickets.repositories.EventRepository;
import com.hemant.tickets.repositories.UserRepository;
import com.hemant.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {

        User organizer =  userRepository.findById(organizerId)
                .orElseThrow(()->new UserNotFoundException("User with ID '%s' not found" + organizerId ));

        Event eventToBeCreated = new Event();


        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setEvent(eventToBeCreated);
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    return ticketTypeToCreate;
                }).toList();

        eventToBeCreated.setName(event.getName());
        eventToBeCreated.setStart(event.getStart());
        eventToBeCreated.setEnd(event.getEnd());
        eventToBeCreated.setVenue(event.getVenue());
        eventToBeCreated.setSalesStart(event.getSalesStart());
        eventToBeCreated.setSalesEnd(event.getSalesEnd());
        eventToBeCreated.setStatus(event.getStatus());
        eventToBeCreated.setOrganizer(organizer);
        eventToBeCreated.setTicketTypes(ticketTypesToCreate);



        return eventRepository.save(eventToBeCreated);

    }

    @Override
    public Page<Event> listEventForOrganizer(UUID organizerId, Pageable pageable) {

        return eventRepository.findByOrganizerId(organizerId, pageable);

    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
        return eventRepository.findByIdAndOrganizerId(eventId , organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest updateEventRequest) {

        if(null == eventId){
            throw new EventUpdateException("Event ID cannot be null");
        }

        if(!eventId.equals(updateEventRequest.getId())){
            throw new EventUpdateException("Cannot update the ID of an event");
        }

        Event exisitingEvent = eventRepository.findByIdAndOrganizerId(eventId, organizerId).orElseThrow(() -> new EventNotFoundException("Event with ID '%s' does not exist " + eventId));

        exisitingEvent.setName(updateEventRequest.getName());
        exisitingEvent.setStart(updateEventRequest.getStart());
        exisitingEvent.setEnd(updateEventRequest.getEnd());
        exisitingEvent.setVenue(exisitingEvent.getVenue());
        exisitingEvent.setSalesStart(exisitingEvent.getSalesStart());
        exisitingEvent.setSalesEnd(exisitingEvent.getSalesEnd());
        exisitingEvent.setStatus(exisitingEvent.getStatus());

        Set<UUID> reqTicketsTypeIds = updateEventRequest.getTicketTypes().stream().map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        exisitingEvent.getTicketTypes().removeIf(exisitingTicketType -> !reqTicketsTypeIds.contains(exisitingTicketType.getId()));


        Map<UUID , TicketType> existingTicketTypeIndex = exisitingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId , Function.identity()));


        for(UpdateTicketTypeRequest ticketType: updateEventRequest.getTicketTypes()){

            if(null == ticketType.getId()){
                //create

                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(exisitingEvent);
                exisitingEvent.getTicketTypes().add(ticketTypeToCreate);

            }else if(existingTicketTypeIndex.containsKey(ticketType.getId())){
                //update

                TicketType exisitingTicketType = existingTicketTypeIndex.get(ticketType.getId());
                exisitingTicketType.setName(ticketType.getName());
                exisitingTicketType.setPrice(ticketType.getPrice());
                exisitingTicketType.setDescription(ticketType.getDescription());
                exisitingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            }else{
                throw new TicketTypeNotFoundException("Ticket type with ID '%s' does not exist" + ticketType.getId());
            }
        }

        return eventRepository.save(exisitingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {
        getEventForOrganizer(organizerId, id).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query,pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID eventId) {
        return eventRepository.findByIdAndStatus(eventId , EventStatusEnum.PUBLISHED);
    }


}
