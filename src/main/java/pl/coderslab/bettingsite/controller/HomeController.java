package pl.coderslab.bettingsite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.bettingsite.model.GameDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {

    @RequestMapping("/myhome")
    @ResponseBody
    public String myHome() {

        return "my home page";

    }

    @RequestMapping("/moderator/mypage")
    @ResponseBody
    public String myModeratorPage() {

        return "my moderator page";

    }

    @RequestMapping("/get-events")
    @ResponseBody
    public String getCountriesAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "http://localhost:8080/home/gameWeekSchedule";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GameDto[]> responseGames = restTemplate.getForEntity(
                url, GameDto[].class);
        GameDto[] games = responseGames.getBody();
        for (GameDto game : games) {
//            logger.info("countries {}", country);
            System.out.println(game.getTeamHome() + " : " + game.getTeamAway() + " | " + game.getStarted() + " | " + game.getHomeOdd() + " - " + game.getDrawOdd() + " - "+ game.getAwayOdd());
        }

        return "successfully downloaded events";
    }
}
