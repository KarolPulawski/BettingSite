package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.service.DateService;
import pl.coderslab.bettingsite.service.StatisticService;
import pl.coderslab.bettingsite.service.UserService;
import pl.coderslab.bettingsite.service.impl.*;

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



}
