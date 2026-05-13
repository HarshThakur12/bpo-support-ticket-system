package com.harsh.myfirstapp.service;

import com.harsh.myfirstapp.SupportTicket;
import com.harsh.myfirstapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // ye Spring ko btata he ki ye "Brain" layer he
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // Saare tickets laane ka logic (GET ALL TICKETS)
    public List<SupportTicket> getAllTickets(){
        return ticketRepository.findAllByOrderByCreatedAtDesc();
    }

    // New Ticket SAVE krna ka logic (Post)
    public SupportTicket createTicket(SupportTicket ticket){
        return ticketRepository.save(ticket);
    }

    // ID se ticket dhoondne ka logic (FindByID)
    public SupportTicket getTicketById(int id){
        return ticketRepository.findById(id).orElse(null);
    }

    // Delete krne ka logic    (Delete)
    public String deleteTicket(int id){
        if(ticketRepository.existsById(id)){
            ticketRepository.deleteById(id);
        return "Ticket Successfully Deleted !!!";
        }
            return "Ticket not found !!!";
    }

    // Update ticket
    public SupportTicket updateTicket(int id, SupportTicket updatedDetails){
       return ticketRepository.findById(id).map(existingTicket->{
           existingTicket.setIssue(updatedDetails.getIssue());
           existingTicket.setStatus(updatedDetails.getStatus());
           return ticketRepository.save(existingTicket);
       }).orElse(null);
    }
    // uper jo map() and .orElse() use kiya he - ye java ka naya tarika he "NullPointerException" (Error) se bachne ka, agr Database me wo ID nahi mili to App crash nahi hoga wo 1
    // 1 clear message de dega

    //findByStatus
    public List<SupportTicket> findByStatus(String statusValue){
        return ticketRepository.findByStatus(statusValue);
    }

    // findByIssueContaining by Keyword
    public List<SupportTicket> searchTickets(String keyword){
       return ticketRepository.findByIssueContaining(keyword);
    }
}
