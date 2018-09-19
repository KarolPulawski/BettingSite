package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Team;
import pl.coderslab.bettingsite.repository.GameRepository;
import pl.coderslab.bettingsite.service.GameService;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void saveGameToDb(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<Game> getAllActiveGame() {
        return gameRepository.findAllByActiveTrueAndHistoryFalse();
    }

    @Override
    public List<Game> getAllScheduledGames() {
        return gameRepository.findAllByScheduledTrueAndFinishedFalse();
    }

    @Override
    public List<Game> getAllFinishedGames() {
        return gameRepository.findAllByFinishedTrueAndActiveFalse();
    }


    @Override
    public List<Game> totalPointsLastFiveMatches(int teamId) {
        return gameRepository.findTotalPointsLastFiveMatches(teamId);
    }

    @Override
    public List<Game> totalPointsLastThreeHomeMatches(int teamId) {
        return gameRepository.findTotalPointsLastThreeMatchesHome(teamId);
    }

    @Override
    public List<Game> totalPointsLastThreeAwayMatches(int teamId) {
        return gameRepository.findTotalPointsLastThreeMatchesAway(teamId);
    }

    @Override
    public Game findActiveGameByTeam(Team team) {
        return gameRepository.findByActiveFalseAndTeamHome(team);
    }

    @Override
    public Game findFirstByTeamHome(Team teamHome) {
        return gameRepository.findFirstByTeamHome(teamHome);
    }

    @Override
    public Game findFirstScheduleByTeam(Team teamHome) {
        return gameRepository.findFirstByTeamHomeAndActiveFalseAndHistoryFalse(teamHome);
    }

    @Override
    public Game findGameById(int id) {
        return gameRepository.findOne(id);
    }
}
