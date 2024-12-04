package com.example.demo.repository.ticketPoolRepository;

import com.example.demo.model.ticketPool.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPoolRepository extends JpaRepository <TicketPool , Long> {
}
