package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Ticket;
import pl.coderslab.bettingsite.entity.User;

import java.util.List;

@Service
public interface TicketService {
    void addNewTicketToDb(Ticket ticket);

    List<Ticket> findTicketsByCurrentUserWinFalseActiveTrue(User user);

    Ticket findTicketById(int id);

    List<Ticket> findAllTicketByUncheckedCounterZero();
}
