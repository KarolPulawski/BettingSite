package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import pl.coderslab.bettingsite.entity.Game;

import java.util.List;

@Controller
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findAllByActiveFalseAndHistoryFalse();
    List<Game> findAllByActiveTrueAndHistoryFalse();


    @Query("SELECT g FROM Game g WHERE g.id IN ?1")
    List<Game> findAllGameByIdList(List<Integer> id);

    @Query("SELECT g FROM Game g WHERE team_home_id = ?1 OR team_away_id = ?1 AND g.history = TRUE ORDER BY started DESC")
    List<Game> findTotalPointsLastFiveMatches(int teamId);

    @Query("SELECT g FROM Game g WHERE team_home_id = ?1 AND g.history = TRUE ORDER BY started DESC")
    List<Game> findTotalPointsLastThreeMatchesHome(int teamId);

    @Query("SELECT g FROM Game g WHERE team_away_id = ?1 AND g.history = TRUE ORDER BY started DESC")
    List<Game> findTotalPointsLastThreeMatchesAway(int teamId);

}
