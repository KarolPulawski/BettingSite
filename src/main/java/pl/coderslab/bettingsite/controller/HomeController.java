package pl.coderslab.bettingsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
