package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.repository.GameRepository;
import pl.coderslab.bettingsite.service.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TeamServiceImpl teamServiceImpl;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private OddService oddServiceImpl;
    @Autowired
    private GameServiceImpl gameServiceImpl;
    @Autowired
    private TicketServiceImpl ticketServiceImpl;
    @Autowired
    private BetService betServiceImpl;

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
    public Game findByScheduledTrueAndFinishedFalseAndActiveFalseAndTeamHome(int teamHomeId) {
        return gameRepository.findHotGame(teamHomeId);
    }

    @Override
    public Game findGameByTeamHomeAndStarted(Team teamHome, Timestamp started) {
        return gameRepository.findFirstByTeamHomeAndStarted(teamHome, started);
    }

    @Override
    public Game findGameById(int id) {
        return gameRepository.findOne(id);
    }

    public void convertJsonObjectToGameDto(GameDto gameDto) throws ParseException {
        Game newGame = new Game();
        Team teamHome = teamServiceImpl.loadTeamByName(gameDto.getTeamHome());
        Team teamAway = teamServiceImpl.loadTeamByName(gameDto.getTeamAway());
        newGame.setTeamHome(teamHome);
        newGame.setTeamAway(teamAway);
        newGame.setStarted(DateService.timestampFromString(gameDto.getStarted()));
        newGame.setActive(gameDto.isActive());
        newGame.setHistory(gameDto.isHistory());
        newGame.setScheduled(gameDto.getScheduled());
        newGame.setFinished(gameDto.getFinished());

        Odd odd = new Odd();
        odd.setAwayOdd(gameDto.getAwayOdd());
        odd.setDrawOdd(gameDto.getDrawOdd());
        odd.setHomeOdd(gameDto.getHomeOdd());
        odd = statisticService.generateOdd(newGame);
        oddServiceImpl.addNewOdd(odd);
        newGame.setOdd(odd);
        gameServiceImpl.saveGameToDb(newGame);
    }

    public void convertJsonObjectToGameResultDto(GameResultDto gameResultDto, Game currentGame) throws ParseException {
        currentGame.setActive(gameResultDto.isActive());
        currentGame.setHistory(gameResultDto.isHistory());
        currentGame.setFinished(gameResultDto.getFinished());
        currentGame.setScheduled(gameResultDto.getScheduled());
        currentGame.setHomeGoal(gameResultDto.getHomeGoal());
        currentGame.setAwayGoal(gameResultDto.getAwayGoal());
        currentGame.setHomePoint(gameResultDto.getHomePoint());
        currentGame.setAwayPoint(gameResultDto.getAwayPoint());
        currentGame.setHomeYellow(gameResultDto.getHomeYellow());
        currentGame.setAwayYellow(gameResultDto.getAwayYellow());
        currentGame.setHomeRed(gameResultDto.getHomeRed());
        currentGame.setAwayRed(gameResultDto.getAwayRed());
        gameServiceImpl.saveGameToDb(currentGame);
    }

    public void changeBetStatus(Game currentGame) {
        List<Bet> bets = currentGame.getBets();
        for(Bet bet : bets) {
            Ticket ticketToCheck = bet.getTicket();

            String typeFromUser = bet.getType();

            int homePoint = currentGame.getHomePoint();

            if(homePoint == 3 && typeFromUser.equals("1")) {
                bet.setBetStatus(BetStatus.WIN);
            } else if(homePoint == 1 && typeFromUser.equals("X")) {
                bet.setBetStatus(BetStatus.WIN);
            } else if(homePoint == 0 && typeFromUser.equals("2")) {
                bet.setBetStatus(BetStatus.WIN);
            } else {
                bet.setBetStatus(BetStatus.LOSE);
            }
            ticketServiceImpl.deincrementUncheckedCounter(ticketToCheck);
            betServiceImpl.addBetToDb(bet);
            ticketServiceImpl.addTicketToDb(ticketToCheck);
        }
    }

}
