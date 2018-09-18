package pl.coderslab.bettingsite.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Odd;
import pl.coderslab.bettingsite.entity.Team;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.model.CurrentUser;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.service.StatisticService;
import pl.coderslab.bettingsite.service.impl.GameServiceImpl;
import pl.coderslab.bettingsite.service.impl.OddServiceImpl;
import pl.coderslab.bettingsite.service.impl.TeamServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/home")
    public String myHome() {

        return "game_display";

    }

    @RequestMapping("/moderator/mypage")
    public String myModeratorPage() {

        return "my moderator page";

    }

//    @RequestMapping("/admin/home")
//    @ResponseBody
//    public String admin() {
//        return "admin";
//    }

    @RequestMapping("/get-events")
    public String getScheduledEvents(Model model) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String url = "http://localhost:8080/home/gameWeekSchedule";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GameDto[]> responseGames = restTemplate.getForEntity(
                url, GameDto[].class);
        GameDto[] games = responseGames.getBody();
        for (GameDto game : games) {
//            logger.info("countries {}", country);
            System.out.println(game.getGameId() + ") " + game.getTeamHome() + " : " + game.getTeamAway() + " | "
                    + game.getStarted() + " | " + game.getHomeOdd() + " - " + game.getDrawOdd() + " - "
                    + game.getAwayOdd());
        }
        System.out.println("**** size games: " + games.length + "******");
        model.addAttribute("games", games);

        return "game_display";
    }

    @RequestMapping("/get-results")
    public String getResults(Model model) throws ServletException, IOException {
        String url = "http://localhost:8080/home/gameWeekResults";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GameResultDto[]> responseGames = restTemplate.getForEntity(
                url, GameResultDto[].class);
        GameResultDto[] games = responseGames.getBody();
        for (GameResultDto game : games) {
//            logger.info("countries {}", country);
            System.out.println(game.getGameId() + ") " + game.getTeamHome() + " " + game.getHomeGoal() + ":"
                    + game.getAwayGoal() + " " + game.getTeamAway() + " | "
                    + game.getStarted() + " | " + game.getHomeOdd() + " - " + game.getDrawOdd() + " - "
                    + game.getAwayOdd()
                    + " yellow: " + game.getHomeYellow() + "|" + game.getAwayYellow());
        }
        System.out.println("**** size games: " + games.length + "******");
        model.addAttribute("games", games);
        return "game_results_display";
    }

    @PostMapping("/api/game")
    public String receiveInfoFromApi(@RequestBody GameDto gameDto) {

        Game newGame = new Game();

        Team teamHome = teamServiceImpl.loadTeamByName(gameDto.getTeamHome());
        Team teamAway = teamServiceImpl.loadTeamByName(gameDto.getTeamAway());
        newGame.setTeamHome(teamHome);
        newGame.setTeamAway(teamAway);

        newGame.setActive(gameDto.isActive());
        newGame.setHistory(gameDto.isHistory());
        System.out.println("ACTIVE: " + gameDto.isActive());

        Odd odd = new Odd();
        odd.setAwayOdd(gameDto.getAwayOdd());
        odd.setDrawOdd(gameDto.getDrawOdd());
        odd.setHomeOdd(gameDto.getHomeOdd());

        odd = statisticService.generateOdd(newGame);

        oddServiceImpl.addNewOdd(odd);
        newGame.setOdd(odd);

//        System.out.print("****** scheduled");
//        System.out.print(newGame.getTeamHome().getName());
//        System.out.print(" | ");
//        System.out.print(newGame.getTeamAway().getName());
//        System.out.print(" | ");
//        System.out.print(newGame.getActive());
//        System.out.print(" | ");
//        System.out.print(newGame.getHistory());
//        System.out.print("\n");
        
        // save to db new games
        gameServiceImpl.saveGameToDb(newGame);
        return "test";
    }

    @PostMapping("/api/result")
    public String receiveResultApi(@RequestBody GameResultDto gameResultDto) {

        Team teamHome = teamServiceImpl.loadTeamByName(gameResultDto.getTeamHome());
        System.out.println("name from dto: " + gameResultDto.getTeamHome());

        Game currentGame = gameServiceImpl.findFirstScheduleByTeam(teamHome);
        System.out.println("name from db: " + currentGame.getTeamHome().getName());

        currentGame.setActive(gameResultDto.isActive());
        currentGame.setHistory(gameResultDto.isHistory());

        currentGame.setHomeGoal(gameResultDto.getHomeGoal());
        currentGame.setAwayGoal(gameResultDto.getAwayGoal());

        currentGame.setHomePoint(gameResultDto.getHomePoint());
        currentGame.setAwayPoint(gameResultDto.getAwayPoint());

        currentGame.setHomeYellow(gameResultDto.getHomeYellow());
        currentGame.setAwayYellow(gameResultDto.getAwayYellow());

        currentGame.setHomeRed(gameResultDto.getHomeRed());
        currentGame.setAwayRed(gameResultDto.getAwayRed());

        gameServiceImpl.saveGameToDb(currentGame);
//        System.out.print("****** result");
//        System.out.print(game.getTeamHome());
//        System.out.print(" | ");
//        System.out.print(game.getHomeGoal());
//        System.out.print(" | ");
//        System.out.print(game.getAwayGoal());
//        System.out.print(" | ");
//        System.out.print(game.getTeamAway());
//        System.out.print(" | ");
//        System.out.print(game.isActive());
//        System.out.print(" | ");
//        System.out.print(game.isHistory());
//        System.out.print(" | ");
//        System.out.println("\n");
//        // save to db new games
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
        List<Game> gamesResult = gameServiceImpl.getAllScheduledGames();
        model.addAttribute("games", gamesResult);
        return "game_result_display";
    }

    @GetMapping("/ticket/{game_id}/{type}/create")
    public String receiveOneGameTicket(@PathVariable String game_id, @PathVariable String type){
        // create model attribute ticket List of game

        System.out.println(game_id + "-" + type);
        return "redirect:/get-events";
    }


}
