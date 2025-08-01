package com.hemant.tickets.controller;


import com.hemant.tickets.dto.GetTicketResponseDto;
import com.hemant.tickets.dto.ListTicketResponseDto;
import com.hemant.tickets.entity.Ticket;
import com.hemant.tickets.mappers.TicketMapper;
import com.hemant.tickets.services.QrCodeService;
import com.hemant.tickets.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static com.hemant.tickets.util.JwtUtil.parseUserId;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QrCodeService  qrCodeService;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDto>> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        Page<ListTicketResponseDto> map = ticketService.listTicketsForUsers(
                parseUserId(jwt), pageable).map(ticketMapper::toListTicketResponseDto);

        return ResponseEntity.ok(map);


    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(@AuthenticationPrincipal Jwt jwt , @PathVariable UUID ticketId){
        Optional<GetTicketResponseDto> getTicketResponseDto = ticketService
                .getTicketForUser(parseUserId(jwt), ticketId)
                .map(ticketMapper::toGetTicketResponseDto);

        return getTicketResponseDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(

            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId

    ){
        byte[] qrImage = qrCodeService.getQrCodeImageForUserAndTicket(parseUserId(jwt), ticketId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(qrImage.length);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(qrImage);


    }


}
