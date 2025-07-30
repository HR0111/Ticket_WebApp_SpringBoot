package com.hemant.tickets.repositories;

import com.hemant.tickets.entity.Event;
import com.hemant.tickets.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    Page<Event> findByOrganizerId(UUID organizerId , Pageable pageable);
    Optional<Event> findByIdAndOrganizerId(UUID id , UUID organizerId);

}
