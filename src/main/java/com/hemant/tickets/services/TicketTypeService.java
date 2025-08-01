package com.hemant.tickets.services;

import com.hemant.tickets.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaaseTicket(UUID userId , UUID ticketTypeId);

}
