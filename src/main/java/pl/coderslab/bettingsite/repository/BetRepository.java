package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.bettingsite.entity.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {
    Bet findByGameId(int gameId);
}
