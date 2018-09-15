package pl.coderslab.bettingsite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @ResponseBody
    public String getResults() throws ServletException, IOException {
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

        return "successfully downloaded events";
    }
}
