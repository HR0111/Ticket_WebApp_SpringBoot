package com.hemant.tickets.repositories;

import com.hemant.tickets.entity.Event;
import com.hemant.tickets.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User , UUID> {


}
