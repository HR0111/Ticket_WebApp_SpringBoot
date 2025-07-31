package com.hemant.tickets.mappers;


import com.hemant.tickets.dto.*;
import com.hemant.tickets.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toListEventTicketResponseDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventTicketTypeResponseDto toGetEventTicketTypeResponseDto(TicketType ticketType);

    GetEventDetailResponseDto toGetEventDetailResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);
    UpdateEventRequest fromDto(UpdateEventResponseDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);
    UpdateEventResponseDto  toUpdateEventResponseDto(Event event);

    UpdateEventRequest fromDto(UpdateEventRequestDto updateEventRequestDto);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

    GetPublishedEventDetailResponseDto toGetPublishedEventDetailResponseDto(Event event);
    GetPublishedEventDetailResponseDto toGetPublishedEventDetailResponseDto(TicketType ticketType);

}
