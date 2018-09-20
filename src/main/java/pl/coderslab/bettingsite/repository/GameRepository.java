package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Team;

import java.sql.Timestamp;
import java.util.List;

@Controller
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findAllByScheduledTrueAndFinishedFalse();
    List<Game> findAllByFinishedTrueAndActiveFalse();
    List<Game> findAllByActiveTrueAndHistoryFalse();


    @Query("SELECT g FROM Game g WHERE g.id IN ?1")
    List<Game> findAllGameByIdList(List<Integer> id);

    @Query("SELECT g FROM Game g WHERE team_home_id = ?1 OR team_away_id = ?1 AND g.history = TRUE ORDER BY started DESC")
    List<Game> findTotalPointsLastFiveMatches(int teamId);

    @Query("SELECT g FROM Game g WHERE team_home_id = ?1 AND g.history = TRUE ORDER BY started DESC")
    List<Game> findTotalPointsLastThreeMatchesHome(int teamId);

    @Query("SELECT g FROM Game g WHERE team_away_id = ?1 AND g.history = TRUE ORDER BY started DESC")
    List<Game> findTotalPointsLastThreeMatchesAway(int teamId);

    Game findFirstByTeamHomeAndStarted(Team teamHome, Timestamp started);

    @Query("SELECT g FROM Game g WHERE g.scheduled = true and g.active = false and g.finished = false and (g.teamHome.id = ?1 or g.teamAway.id = ?1)")
    Game findHotGame(int id);
}
