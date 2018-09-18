package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Bet;
import pl.coderslab.bettingsite.repository.BetRepository;
import pl.coderslab.bettingsite.service.BetService;

@Service
public class BetServiceImpl implements BetService {

    @Autowired
    private BetRepository betRepository;

    @Override
    public void addBetToDb(Bet bet) {
        betRepository.save(bet);
    }
}
