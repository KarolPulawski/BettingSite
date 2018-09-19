package pl.coderslab.bettingsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @RequestMapping("/deposit")
    public String depositMoney() {
        return "wallet_deposit";
    }

    @PostMapping("/deposit")
    public String updateMoneyBalanceDeposit(HttpServletRequest request) {
        BigDecimal depositAmount = new BigDecimal(request.getParameter("depositAmount"));
        System.out.println(depositAmount);
        return "redirect:/games/scheduled/display";
    }

    @RequestMapping("/withdraw")
    public String withdrawMoney() {
        return "wallet_withdraw";
    }

    @PostMapping("/withdraw")
    public String updateMoneyBalanceWithdraw(HttpServletRequest request) {
        BigDecimal depositAmount = new BigDecimal(request.getParameter("withdrawAmount"));
        System.out.println(depositAmount);
        return "redirect:/games/scheduled/display";
    }

    @RequestMapping("/")
    public String walletPanel() {
        return "wallet_panel";
    }
}
