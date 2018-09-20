package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.bettingsite.entity.Bet;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Ticket;
import pl.coderslab.bettingsite.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Service
public interface TicketService {
    void addTicketToDb(Ticket ticket);

    List<Ticket> findTicketsByCurrentUser(User user);

    Ticket findTicketById(int id);

    List<Ticket> findAllTicketByUncheckedCounterZero();
    List<Ticket> findAllTicketByWinTrueAndPaidFalse();

    double createTicket(Set<Bet> bets, Game game, String type);

    boolean submitTicket(Set<Bet> bets, double stake, Model model);
}
