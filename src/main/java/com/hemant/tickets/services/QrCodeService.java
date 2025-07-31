package com.hemant.tickets.services;

import com.hemant.tickets.entity.QrCode;
import com.hemant.tickets.entity.Ticket;
import com.hemant.tickets.entity.TicketType;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

}
