package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.bettingsite.entity.Bet;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Ticket;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.service.UserService;
import pl.coderslab.bettingsite.service.impl.BetServiceImpl;
import pl.coderslab.bettingsite.service.impl.GameServiceImpl;
import pl.coderslab.bettingsite.service.impl.TicketServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @Autowired
    private BetServiceImpl betServiceImpl;

    @PostMapping("/create")
    public String createNewTicket(HttpServletRequest request) {
        HttpSession sess = request.getSession();
        Set<Bet> bets = new HashSet<>();
        double totalOdd = 1.0;
        sess.setAttribute("bets", bets);
        sess.setAttribute("totalOdd", totalOdd);
        return "redirect:/games/scheduled/display";
    }

    @GetMapping("/{game_id}/{type}/create")
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

        Bet bet = new Bet(game, type, currentOdd, BetStatus.ACTIVE);
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

    @PostMapping("/delete/{game_id}")
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

    @PostMapping("/submit")
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
        ticket.setUncheckedCounter(bets.size());

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

    @RequestMapping("/displayAll")
    public String displayCreatedTicket(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findUserByEmail(userName);
        model.addAttribute("tickets", ticketServiceImpl.findTicketsByCurrentUserWinFalseActiveTrue(currentUser));
        return "tickets_display";
    }

    @PostMapping("/display/{id}")
    public String displayOneTicket(@PathVariable int id, Model model) {
        Ticket ticket = ticketServiceImpl.findTicketById(id);
        model.addAttribute("ticket", ticket);
        return "ticket_display";
    }
}
