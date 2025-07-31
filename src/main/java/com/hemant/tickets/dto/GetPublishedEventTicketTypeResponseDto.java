package com.hemant.tickets.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetPublishedEventTicketTypeResponseDto {

    private UUID id;
    private String name;
    private String description;
    private Double price;



}
