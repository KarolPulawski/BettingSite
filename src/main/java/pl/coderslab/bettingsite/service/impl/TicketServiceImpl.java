package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.repository.BetRepository;
import pl.coderslab.bettingsite.repository.TicketRepository;
import pl.coderslab.bettingsite.repository.WalletRepository;
import pl.coderslab.bettingsite.service.TicketService;
import pl.coderslab.bettingsite.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private UserService userService;
    @Autowired
    private BetServiceImpl betServiceImpl;

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
        return ticketRepository.findAllByWinTrueAndPaidFalse();
    }

    @Scheduled(fixedDelay = 5_000L)
    public void checkIfTicketIsWin() {
        List<Ticket> ticketsToCheck = findAllTicketByUncheckedCounterZero();

        for(Ticket ticket : ticketsToCheck) {
            boolean isWin = true;
            List<Bet> bets = betRepository.findAllByTicketId(ticket.getId());
            for (Bet b : bets) {
                if(b.getBetStatus() == BetStatus.LOSE) {
                    isWin = false;
                    break;
                }
            }
            ticket.setActive(false);
            if(isWin) ticket.setWin(true);
            else ticket.setWin(false);
            addTicketToDb(ticket);
        }
    }

    @Scheduled(fixedDelay = 10_000L)
    public void paidIfTicketIsWin() {
        List<Ticket> ticketsToCheckPaid = null;
        try {
            ticketsToCheckPaid = findAllTicketByWinTrueAndPaidFalse();
            for(Ticket ticket : ticketsToCheckPaid) {
                BigDecimal amountToPaid = ticket.getExpectedWin();
                User user = ticket.getUser();
                Wallet currentWallet = user.getWallet();
                walletServiceImpl.depositMoneyWin(amountToPaid, currentWallet);
                ticket.setPaid(true);
                addTicketToDb(ticket);
                ticketsToCheckPaid.clear();
            }
        } catch (Exception e) {
            System.out.println("TICKETS TO CHECK ARE NULL");

        }
    }


    @Override
    public double createTicket(Set<Bet> bets, Game game, String type) {
        double totalOdd = 1.0;
        double currentOdd = 0.0;

        if(type.equals("1")) {
            currentOdd = game.getOdd().getHomeOdd();
        } else if (type.equals("X")) {
            currentOdd = game.getOdd().getDrawOdd();
        } else if (type.equals("2")) {
            currentOdd = game.getOdd().getAwayOdd();
        } else {
            currentOdd = 1.0;
        }

        Bet bet = new Bet(game, type, currentOdd, BetStatus.ACTIVE);
        try {
            if(!bets.contains(bet)) {
                bets.add(bet);
                totalOdd *= currentOdd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalOdd;
    }

    @Override
    public boolean submitTicket(Set<Bet> bets, double stake, Model model) {

        BigDecimal stakeBigDecimal = new BigDecimal(stake);



        Wallet currentWallet = walletServiceImpl.findByCurrentLoggedInUser();
        if(currentWallet.getBalance().compareTo(stakeBigDecimal) >= 0
                && currentWallet.getBalance().compareTo(new BigDecimal(0.0)) == 1) {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = userService.findUserByEmail(userName);


            Boolean active = true;
            Boolean paid = false;
            Boolean win = false;

            Ticket ticket = new Ticket();
            addTicketToDb(ticket);
            ticket.setBets(bets);
            ticket.setUser(currentUser);
            ticket.setActive(active);
            ticket.setPaid(paid);
            ticket.setWin(win);
            ticket.setStake(stakeBigDecimal);
            ticket.setUncheckedCounter(bets.size());

            double totalOdd = 1.0;

            for (Bet b : bets) {
                b.setTicket(ticket);
                totalOdd *= b.getOdd();
                betServiceImpl.addBetToDb(b);
            }

            ticket.setTotalOdd(new BigDecimal(totalOdd));
            ticket.setExpectedWin();
            addTicketToDb(ticket);
            model.addAttribute("ticket", ticket);
            walletServiceImpl.withdrawMoneyForStake(stakeBigDecimal);
            return true;
        } else {
            return false;
        }
    }

}
