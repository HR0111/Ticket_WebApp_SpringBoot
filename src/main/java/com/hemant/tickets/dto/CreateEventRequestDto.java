package com.hemant.tickets.dto;

import com.hemant.tickets.domains.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {

    @NotBlank(message = "Event name  is required")
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;

    @NotBlank(message = "Venue name  is required")
    private String venue;

    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;

    @NotNull(message = "Event status must be provided")
    private EventStatusEnum status;

    @NotEmpty(message = "Message at least one ticket type")
    @Valid
    private List<CreateTicketTypeRequestDto> ticketTypes;

}
