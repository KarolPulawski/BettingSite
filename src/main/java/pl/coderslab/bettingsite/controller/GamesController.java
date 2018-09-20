package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.service.*;
import pl.coderslab.bettingsite.service.impl.GameServiceImpl;

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
    @ResponseBody
    public String receiveGameInfoFromApi(@RequestBody GameDto gameDto) throws ParseException {
        gameServiceImpl.convertJsonObjectToGameDto(gameDto);
        return "Game was received.";
    }

    @PostMapping("/result")
    @ResponseBody
    public String receiveResultFromApi(@RequestBody GameResultDto gameResultDto) throws ParseException {
        Team teamHome = teamServiceImpl.loadTeamByName(gameResultDto.getTeamHome());
        Timestamp timestampToCheck = DateService.timestampFromString(gameResultDto.getStarted());
        Game currentGame = gameServiceImpl.findGameByTeamHomeAndStarted(teamHome, timestampToCheck);
        gameServiceImpl.convertJsonObjectToGameResultDto(gameResultDto, currentGame);
        gameServiceImpl.changeBetStatus(currentGame);
        return "Result was received.";
    }
}
