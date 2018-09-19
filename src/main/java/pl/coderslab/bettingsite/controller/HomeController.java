package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.service.DateService;
import pl.coderslab.bettingsite.service.StatisticService;
import pl.coderslab.bettingsite.service.UserService;
import pl.coderslab.bettingsite.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @Autowired
    private TeamServiceImpl teamServiceImpl;

    @Autowired
    private OddServiceImpl oddServiceImpl;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @Autowired
    private BetServiceImpl betServiceImpl;

    @RequestMapping("/home")
    public String myHome() {
        return "game_display";
    }

    @RequestMapping("/moderator/mypage")
    public String myModeratorPage() {
        return "my moderator page";
    }


    @PostMapping("/api/game")
    public String receiveInfoFromApi(@RequestBody GameDto gameDto) throws ParseException {

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

    @PostMapping("/api/result")
    public String receiveResultApi(@RequestBody GameResultDto gameResultDto) throws ParseException {

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

        List<Bet> bets = currentGame.getBets();
        for(Bet bet : bets) {
            Ticket ticketToCheck = bet.getTicket();

            String typeFromUser = bet.getType();

            int homePoint = currentGame.getHomePoint();
            int awayPoint = currentGame.getAwayPoint();

        }
        return "test";
    }






}
