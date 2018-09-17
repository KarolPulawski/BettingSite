package pl.coderslab.bettingsite.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;

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

    @RequestMapping("/home")
    public String myHome() {

        return "game_display";

    }

    @RequestMapping("/moderator/mypage")
    public String myModeratorPage() {

        return "my moderator page";

    }

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
    public String receiveInfoFromApi(@RequestBody GameDto game) {
        System.out.print("****** scheduled");
        System.out.print(game.getTeamHome());
        System.out.print(" | ");
        System.out.print(game.getTeamAway());
        System.out.print(" | ");
        System.out.print(game.isActive());
        System.out.print(" | ");
        System.out.print(game.isHistory());
        System.out.print(" | ");
        System.out.print(game.getHomeOdd());
        System.out.print(" | ");
        System.out.print(game.getDrawOdd());
        System.out.print(" | ");
        System.out.print(game.getAwayOdd());
        System.out.print("\n");
        // save to db new games
        return "test";
    }

    @PostMapping("/api/result")
    public String receiveResultApi(@RequestBody GameResultDto game) {

        System.out.print("****** result");
        System.out.print(game.getTeamHome());
        System.out.print(" | ");
        System.out.print(game.getHomeGoal());
        System.out.print(" | ");
        System.out.print(game.getAwayGoal());
        System.out.print(" | ");
        System.out.print(game.getTeamAway());
        System.out.print(" | ");
        System.out.print(game.isActive());
        System.out.print(" | ");
        System.out.print(game.isHistory());
        System.out.print(" | ");
        // save to db new games
        return "test";

    }

    @GetMapping("/games/active/display")
    public String displayAllActiveGame() {
        return "game_display";
    }

    @GetMapping("/ticket/{game_id}/{type}/create")
    public String receiveOneGameTicket(@PathVariable String game_id, @PathVariable String type){
        // create model attribute ticket List of game
        System.out.println(game_id + "-" + type);
        return "redirect:/get-events";
    }


}
