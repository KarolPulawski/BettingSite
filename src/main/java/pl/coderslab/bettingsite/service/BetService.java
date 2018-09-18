package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Bet;

@Service
public interface BetService {
    void addBetToDb(Bet bet);
}
