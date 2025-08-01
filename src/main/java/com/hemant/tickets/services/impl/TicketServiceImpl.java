package com.hemant.tickets.services.impl;

import com.hemant.tickets.domains.EventStatusEnum;
import com.hemant.tickets.entity.*;
import com.hemant.tickets.exceptions.EventNotFoundException;
import com.hemant.tickets.exceptions.EventUpdateException;
import com.hemant.tickets.exceptions.TicketTypeNotFoundException;
import com.hemant.tickets.exceptions.UserNotFoundException;
import com.hemant.tickets.repositories.EventRepository;
import com.hemant.tickets.repositories.TicketRepository;
import com.hemant.tickets.repositories.UserRepository;
import com.hemant.tickets.services.EventService;
import com.hemant.tickets.services.TicketService;
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
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listTicketsForUsers(UUID userId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(userId , pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId , userId);
    }
}
