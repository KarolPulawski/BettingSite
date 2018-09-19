package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Team;

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

    Game findActiveGameByTeam(Team team);

    Game findFirstByTeamHome(Team teamHome);

    Game findFirstScheduleByTeam(Team teamHome);

    Game findGameById(int id);
}
