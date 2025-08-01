package com.hemant.tickets.repositories;

import com.hemant.tickets.domains.EventStatusEnum;
import com.hemant.tickets.entity.Event;
import com.hemant.tickets.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    int countByTicketTypeId(UUID ticketTypeId);

    Page<Ticket> findByPurchaserId(UUID purchaserId , Pageable pageable);

    Optional<Ticket> findByIdAndPurchaserId(UUID id , UUID purchaserId);


}
