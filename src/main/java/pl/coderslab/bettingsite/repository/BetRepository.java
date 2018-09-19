package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.bettingsite.entity.Bet;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {
    Bet findByGameId(int gameId);

    List<Bet> findAllByTicketId(int ticketId);
}
