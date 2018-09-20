package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Bet;
import pl.coderslab.bettingsite.entity.Ticket;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.entity.Wallet;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.repository.BetRepository;
import pl.coderslab.bettingsite.repository.TicketRepository;
import pl.coderslab.bettingsite.repository.WalletRepository;
import pl.coderslab.bettingsite.service.TicketService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private WalletServiceImpl walletServiceImpl;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void addTicketToDb(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findTicketsByCurrentUser(User user) {
        return ticketRepository.findAllByUser(user);
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

    @Override
    public List<Ticket> findAllTicketByWinTrueAndPaidFalse() {
        return ticketRepository.findAllByWinTrue();
    }

    @Scheduled(fixedDelay = 5_000L)
    public void checkIfTicketIsWin() {
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
            addTicketToDb(ticket);
        }
    }

    @Scheduled(fixedDelay = 10_000L)
    public void paidIfTicketIsWin() {
        List<Ticket> ticketsToCheckPaid = findAllTicketByWinTrueAndPaidFalse();
        for(Ticket ticket : ticketsToCheckPaid) {
            BigDecimal amountToPaid = ticket.getExpectedWin();
            User user = ticket.getUser();
            Wallet currentWallet = user.getWallet();
            walletServiceImpl.depositMoneyWin(amountToPaid, currentWallet);
            ticket.setPaid(true);
            addTicketToDb(ticket);
        }
    }
}
