package com.hemant.tickets.dto;


import com.hemant.tickets.domains.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListEventTicketTypeResponseDto {

    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer totalAvailable;


}
