package com.harsh.myfirstapp.repository;

import com.harsh.myfirstapp.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<SupportTicket, Integer> {

    List<SupportTicket> findByStatus(String status);
    List<SupportTicket> findByIssueContaining(String keyword);  // keyword se find krne k liye date me

    List<SupportTicket> findAllByOrderByCreatedAtDesc();    // new data (Latest at top) jo add hua he wo sabse uper dikhane k liye table me
    // Logic - findAll -> sab Nikalo, OrderByCreatedAt -> Date k hisaab se lagao, Desc -> Descending order me (latest first)
    // ISME APN "ID" k according bhi kr skte he kyuki new data ki id hmesha badi hogi to sirf hme -> findAllByLOrderByIdDesc(); ye krna rhega
}


// @Repository : Ye annotation Interface pr lagta he. Iska kaam he Database se connection or Data nikale ka rasta (Queries) bnana.