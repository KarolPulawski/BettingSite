package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Bet;

import java.util.List;

@Service
public interface BetService {
    void addBetToDb(Bet bet);

    Bet findBetById(int bet_id);

    Bet finBetByGameId(int bet_id);

    List<Bet> findALlByTicketId(int ticketId);
}
