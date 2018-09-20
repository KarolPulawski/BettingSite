package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Team;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface GameService {
    void saveGameToDb(Game game);

    List<Game> getAllActiveGame();

    List<Game> getAllScheduledGames();
    List<Game> getAllFinishedGames();

    List<Game> totalPointsLastFiveMatches(int teamId);

    List<Game> totalPointsLastThreeHomeMatches(int teamId);
    List<Game> totalPointsLastThreeAwayMatches(int teamId);

    Game findByScheduledTrueAndFinishedFalseAndActiveFalseAndTeamHome(int teamHomeId);

    Game findGameByTeamHomeAndStarted(Team teamHome, Timestamp started);

    Game findGameById(int id);
}
