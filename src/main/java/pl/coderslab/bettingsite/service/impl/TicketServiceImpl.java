package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Bet;
import pl.coderslab.bettingsite.entity.Ticket;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.repository.BetRepository;
import pl.coderslab.bettingsite.repository.TicketRepository;
import pl.coderslab.bettingsite.service.TicketService;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BetRepository betRepository;

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

    public void deincrementUncheckedCounter(Ticket ticket) {
        ticket.setUncheckedCounter(ticket.getUncheckedCounter() - 1);
    }

    @Override
    public List<Ticket> findAllTicketByUncheckedCounterZero() {
        return ticketRepository.findAllByUncheckedCounterZeroAndActiveTrue();
    }

    @Scheduled(fixedDelay = 5_000L)
    public void checkIfTicketIsWin() {
        System.out.println("CHECKING TICKET");
        List<Ticket> ticketsToCheck = findAllTicketByUncheckedCounterZero();
        int counterWin = 0;
        for(Ticket ticket : ticketsToCheck) {
            List<Bet> bets = betRepository.findAllByTicketId(ticket.getId());
            for (Bet b : bets) {
                if(b.getBetStatus() == BetStatus.WIN) {
                    counterWin++;
                }
            }
            ticket.setActive(false);
            if(counterWin == bets.size()) {
                ticket.setWin(true);
            }
            addNewTicketToDb(ticket);
        }
    }
}
