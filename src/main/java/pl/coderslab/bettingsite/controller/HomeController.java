package pl.coderslab.bettingsite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @PostMapping("/api/info")
    public String receiveInfoFromApi(HttpServletRequest request) {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        String test = "test123";

//        HttpSession sess = request.getSession();
//        sess.setAttribute("username", name);
//        sess.setAttribute("password", pass);
        System.out.println("from betting site: " + name + " | " + pass);
//        receiveInfoFromApiGet(request, model);
        return "forward:/api/display";
    }

    @GetMapping("/api/display")
    public String receiveInfoFromApiGet(HttpServletRequest request, Model model) {
//        HttpSession sess = request.getSession();
        model.addAttribute("username", request.getAttribute("username"));
        model.addAttribute("password", request.getAttribute("password"));
        model.addAttribute("test", "test1234");
        System.out.println("info from api display");
        return "test";
    }

//    @PostMapping("/ticket/{game_id}/{type}/create")
//    public String receiveOneGameTicket(){
//        // create model attribute ticket List of game
//    }


}
