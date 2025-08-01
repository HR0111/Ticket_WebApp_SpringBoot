package com.hemant.tickets.services.impl;

import com.hemant.tickets.domains.TicketStatusEnum;
import com.hemant.tickets.entity.Ticket;
import com.hemant.tickets.entity.TicketType;
import com.hemant.tickets.entity.User;
import com.hemant.tickets.exceptions.TicketSoldOutException;
import com.hemant.tickets.exceptions.TicketTypeNotFoundException;
import com.hemant.tickets.exceptions.UserNotFoundException;
import com.hemant.tickets.repositories.TicketRepository;
import com.hemant.tickets.repositories.TicketTypeRepository;
import com.hemant.tickets.repositories.UserRepository;
import com.hemant.tickets.services.QrCodeService;
import com.hemant.tickets.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaaseTicket(UUID userId, UUID ticketTypeId) {

    User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(
            String.format("User with ID %s was not found",userId)
    ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(()-> new TicketTypeNotFoundException(String.format("Ticket type with ID %s was not found",ticketTypeId)));


        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());

        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable){
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);

    }
}
