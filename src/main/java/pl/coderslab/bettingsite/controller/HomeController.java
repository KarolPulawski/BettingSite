package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.service.StatisticService;
import pl.coderslab.bettingsite.service.UserService;
import pl.coderslab.bettingsite.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
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

    @GetMapping("/home")
    public String myHome(HttpServletRequest request) {
        HttpSession sess = request.getSession();
        User user = userService.isLoggedIn();
        String currentEmail = user.getEmail();
        sess.setAttribute("loggedIn", "loggedIn");
        sess.setAttribute("currentEmail", currentEmail);
        return "redirect:/games/scheduled/display";
    }

    @RequestMapping("/moderator/mypage")
    public String myModeratorPage() {
        return "my moderator page";
    }

}
