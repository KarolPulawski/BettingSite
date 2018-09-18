package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Ticket;

@Service
public interface TicketService {
    void addNewTicketToDb(Ticket ticket);
}
