 package com.hemant.tickets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

 @Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketTypeRequestDto {

    private UUID id;
    @NotBlank(message = "Ticket type name  is required")
    private String name;

    private String description;

    @NotBlank(message = "Price is required")
    @PositiveOrZero(message = "Price must be zero or greater integer")
    private Double price;
    private Integer totalAvailable;



}
