package com.hemant.tickets.services;

import com.hemant.tickets.entity.QrCode;
import com.hemant.tickets.entity.Ticket;
import com.hemant.tickets.entity.TicketType;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

   byte[] getQrCodeImageForUserAndTicket(UUID userId , UUID ticketId);

}
