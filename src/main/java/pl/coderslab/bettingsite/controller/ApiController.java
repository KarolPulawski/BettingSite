package pl.coderslab.bettingsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.bettingsite.entity.*;
import pl.coderslab.bettingsite.model.BetStatus;
import pl.coderslab.bettingsite.model.GameDto;
import pl.coderslab.bettingsite.model.GameResultDto;
import pl.coderslab.bettingsite.service.*;
import pl.coderslab.bettingsite.service.impl.TicketServiceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {


}
