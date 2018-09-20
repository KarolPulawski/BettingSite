package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.service.*;
import pl.coderslab.bettingsite.service.impl.GameServiceImpl;
import pl.coderslab.bettingsite.service.impl.TicketServiceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @Autowired
    private TeamService teamServiceImpl;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private OddService oddServiceImpl;
    @Autowired
    private BetService betServiceImpl;
    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @GetMapping("/scheduled/display")
    public String displayAllActiveGame(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName", userName);
        List<Game> gamesScheduled = gameServiceImpl.getAllScheduledGames();
        model.addAttribute("games", gamesScheduled);
        return "game_display";
    }

    @GetMapping("/results/display")
    public String displayAllResultsGame(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName", userName);
        List<Game> gamesResult = gameServiceImpl.getAllFinishedGames();
        model.addAttribute("games", gamesResult);
        return "game_results_display";
    }


    @PostMapping("/game")
    public String receiveInfoFromApi(@RequestBody GameDto gameDto) throws ParseException {
        // convert json to game object
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
        return "test";
    }

    @PostMapping("/result")
    public String receiveResultApi(@RequestBody GameResultDto gameResultDto) throws ParseException {

        // convert json to gameresultDto object
        Team teamHome = teamServiceImpl.loadTeamByName(gameResultDto.getTeamHome());
        Timestamp timestampToCheck = DateService.timestampFromString(gameResultDto.getStarted());
        Game currentGame = gameServiceImpl.findGameByTeamHomeAndStarted(teamHome, timestampToCheck);
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

        // change bet status WIN/LOSE from active
        List<Bet> bets = currentGame.getBets();
        for(Bet bet : bets) {
            Ticket ticketToCheck = bet.getTicket();

            String typeFromUser = bet.getType();

            int homePoint = currentGame.getHomePoint();

            if(homePoint == 3 && typeFromUser.equals("1") || homePoint == 1 && typeFromUser.equals("X")
                    || homePoint == 0 && typeFromUser.equals("2")) {
                bet.setBetStatus(BetStatus.WIN);
            } else {
                bet.setBetStatus(BetStatus.LOSE);
            }
            ticketServiceImpl.deincrementUncheckedCounter(ticketToCheck);
            betServiceImpl.addBetToDb(bet);
            ticketServiceImpl.addTicketToDb(ticketToCheck);
        }
        return "test";
    }
}
