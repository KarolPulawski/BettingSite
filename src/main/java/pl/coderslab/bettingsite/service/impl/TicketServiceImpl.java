package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Ticket;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.repository.TicketRepository;
import pl.coderslab.bettingsite.service.TicketService;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void addNewTicketToDb(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findTicketsByCurrentUserWinFalseActiveTrue(User user) {
        return ticketRepository.findAllByUserAndActiveTrueAndWinFalse(user);
    }

    @Override
    public Ticket findTicketById(int id) {
        return ticketRepository.findOneById(id);
    }
}
