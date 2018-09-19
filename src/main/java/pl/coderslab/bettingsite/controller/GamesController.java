package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.service.GameService;
import pl.coderslab.bettingsite.service.impl.GameServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    @Autowired
    private GameServiceImpl gameServiceImpl;

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
}
