package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.service.DateService;
import pl.coderslab.bettingsite.service.StatisticService;
import pl.coderslab.bettingsite.service.UserService;
import pl.coderslab.bettingsite.service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//    @RequestMapping("/get-events")
//    public String getScheduledEvents(Model model) throws ServletException, IOException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String url = "http://localhost:8080/home/gameWeekSchedule";
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<GameDto[]> responseGames = restTemplate.getForEntity(
//                url, GameDto[].class);
//        GameDto[] games = responseGames.getBody();
//        for (GameDto game : games) {
////            logger.info("countries {}", country);
//            System.out.println(game.getGameId() + ") " + game.getTeamHome() + " : " + game.getTeamAway() + " | "
//                    + game.getStarted() + " | " + game.getHomeOdd() + " - " + game.getDrawOdd() + " - "
//                    + game.getAwayOdd());
//        }
//        System.out.println("**** size games: " + games.length + "******");
//        model.addAttribute("games", games);
//
//        return "game_display";
//    }

//    @RequestMapping("/get-results")
//    public String getResults(Model model) throws ServletException, IOException {
//        String url = "http://localhost:8080/home/gameWeekResults";
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<GameResultDto[]> responseGames = restTemplate.getForEntity(
//                url, GameResultDto[].class);
//        GameResultDto[] games = responseGames.getBody();
//        for (GameResultDto game : games) {
////            logger.info("countries {}", country);
//            System.out.println(game.getGameId() + ") " + game.getTeamHome() + " " + game.getHomeGoal() + ":"
//                    + game.getAwayGoal() + " " + game.getTeamAway() + " | "
//                    + game.getStarted() + " | " + game.getHomeOdd() + " - " + game.getDrawOdd() + " - "
//                    + game.getAwayOdd()
//                    + " yellow: " + game.getHomeYellow() + "|" + game.getAwayYellow());
//        }
//        System.out.println("**** size games: " + games.length + "******");
//        model.addAttribute("games", games);
//        return "game_results_display";
//    }

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
        System.out.println("ACTIVE: " + gameDto.isActive());

        Odd odd = new Odd();
        odd.setAwayOdd(gameDto.getAwayOdd());
        odd.setDrawOdd(gameDto.getDrawOdd());
        odd.setHomeOdd(gameDto.getHomeOdd());

        odd = statisticService.generateOdd(newGame);

        oddServiceImpl.addNewOdd(odd);
        newGame.setOdd(odd);
        
        // save to db new games
        gameServiceImpl.saveGameToDb(newGame);
        return "test";
    }

    @PostMapping("/api/result")
    public String receiveResultApi(@RequestBody GameResultDto gameResultDto) throws ParseException {

        Team teamHome = teamServiceImpl.loadTeamByName(gameResultDto.getTeamHome());
        System.out.println("name from dto: " + gameResultDto.getTeamHome());

        Timestamp timestampToCheck = DateService.timestampFromString(gameResultDto.getStarted());
        System.out.println("TIMESTAMP: " + timestampToCheck.toString());

        Game currentGame = gameServiceImpl.findGameByTeamHomeAndStarted(teamHome, timestampToCheck);
        System.out.println("name from db: " + currentGame.getTeamHome().getName());

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
        return "test";
    }

    @GetMapping("/games/scheduled/display")
    public String displayAllActiveGame(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName", userName);
        List<Game> gamesScheduled = gameServiceImpl.getAllScheduledGames();
        model.addAttribute("games", gamesScheduled);
        return "game_display";
    }

    @GetMapping("/games/results/display")
    public String displayAllResultsGame(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName", userName);
        List<Game> gamesResult = gameServiceImpl.getAllFinishedGames();
        model.addAttribute("games", gamesResult);
        return "game_results_display";
    }

    @PostMapping("/ticket/create")
    public String createNewTicket(HttpServletRequest request) {
        HttpSession sess = request.getSession();
        Set<Bet> bets = new HashSet<>();
        double totalOdd = 1.0;
        sess.setAttribute("bets", bets);
        sess.setAttribute("totalOdd", totalOdd);
        return "redirect:/games/scheduled/display";
    }

    @GetMapping("/ticket/{game_id}/{type}/create")
    public String createBet(HttpServletRequest request, @PathVariable String game_id, @PathVariable String type){
        HttpSession sess = request.getSession();
        Set<Bet> bets = (Set<Bet>) sess.getAttribute("bets");
        double totalOdd = (double)sess.getAttribute("totalOdd");
        Game game = gameServiceImpl.findGameById(Integer.parseInt(game_id));

        double currentOdd;

        if(type.equals("1")) {
            currentOdd = game.getOdd().getHomeOdd();
        } else if (type.equals("X")) {
            currentOdd = game.getOdd().getDrawOdd();
        } else if (type.equals("2")) {
            currentOdd = game.getOdd().getAwayOdd();
        } else {
            currentOdd = 1.0;
        }

        Bet bet = new Bet(game, type, currentOdd);
        try {
            if(!bets.contains(bet)) {
                bets.add(bet);
                totalOdd *= currentOdd;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BET IS ALREADY EXIST");
        }
        totalOdd = Math.round(totalOdd * 100)/100.0;
        sess.setAttribute("bets", bets);
        sess.setAttribute("totalOdd", totalOdd);
        return "redirect:/games/scheduled/display";
    }

    @PostMapping("/ticket/delete/{game_id}")
    public String deleteBetFromTicket(Model model, @PathVariable String game_id, HttpServletRequest request) {
        HttpSession sess = request.getSession();
        Set<Bet> bets = (Set<Bet>) sess.getAttribute("bets");
        double totalOdd = (double) sess.getAttribute("totalOdd");
        Bet betToDelete = null;
        for(Bet b : bets) {
            if(b.getGame().getId() == Integer.parseInt(game_id)) {
                betToDelete = b;
                break;
            }
        }
        totalOdd /= betToDelete.getOdd();
        totalOdd = Math.round(totalOdd * 100)/100.0;
        bets.remove(betToDelete);
        sess.setAttribute("bets", bets);
        sess.setAttribute("totalOdd", totalOdd);
        return "redirect:/games/scheduled/display";
    }

    @PostMapping("/ticket/submit")
    public String submitTicket(HttpServletRequest request, Model model) {
        HttpSession sess = request.getSession();
        Set<Bet> bets = (Set<Bet>) sess.getAttribute("bets");
        double stake = Double.parseDouble(request.getParameter("stake"));

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findUserByEmail(userName);

        Boolean active = true;
        Boolean paid = false;
        Boolean win = false;

        Ticket ticket = new Ticket();
        ticketServiceImpl.addNewTicketToDb(ticket);
        ticket.setBets(bets);
        ticket.setUser(currentUser);
        ticket.setActive(active);
        ticket.setPaid(paid);
        ticket.setWin(win);
        ticket.setStake(new BigDecimal(stake));


        double totalOdd = 1.0;

        for(Bet b : bets) {
            b.setTicket(ticket);
            totalOdd *= b.getOdd();
            betServiceImpl.addBetToDb(b);
        }
        ticket.setTotalOdd(new BigDecimal(totalOdd));
        ticket.setExpectedWin();

        ticketServiceImpl.addNewTicketToDb(ticket);
        model.addAttribute("ticket", ticket);

        return "ticket_display";
    }

    @RequestMapping("/ticket/displayAll")
    public String displayCreatedTicket(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findUserByEmail(userName);
        model.addAttribute("tickets", ticketServiceImpl.findTicketsByCurrentUserWinFalseActiveTrue(currentUser));
        return "tickets_display";
    }

    @PostMapping("/ticket/display/{id}")
    public String displayOneTicket(@PathVariable int id, Model model) {
        Ticket ticket = ticketServiceImpl.findTicketById(id);
        model.addAttribute("ticket", ticket);
        return "ticket_display";
    }

    @RequestMapping("/wallet/deposit")
    public String depositMoney() {
        return "wallet_deposit";
    }

    @PostMapping("/wallet/deposit")
    public String updateMoneyBalanceDeposit(HttpServletRequest request) {
        BigDecimal depositAmount = new BigDecimal(request.getParameter("depositAmount"));
        System.out.println(depositAmount);
        return "redirect:/games/scheduled/display";
    }

    @RequestMapping("/wallet/withdraw")
    public String withdrawMoney() {
        return "wallet_withdraw";
    }

    @PostMapping("/wallet/withdraw")
    public String updateMoneyBalanceWithdraw(HttpServletRequest request) {
        BigDecimal depositAmount = new BigDecimal(request.getParameter("withdrawAmount"));
        System.out.println(depositAmount);
        return "redirect:/games/scheduled/display";
    }

    @RequestMapping("/wallet")
    public String walletPanel() {
        return "wallet_panel";
    }
}
