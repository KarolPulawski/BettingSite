package pl.coderslab.bettingsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.bettingsite.service.impl.WalletServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletServiceImpl walletServiceImpl;

    @RequestMapping("/deposit")
    public String depositMoney() {
        return "wallet_deposit";
    }

    @PostMapping("/deposit")
    public String updateMoneyBalanceDeposit(HttpServletRequest request) {
        BigDecimal depositAmount = new BigDecimal(request.getParameter("depositAmount"));
        walletServiceImpl.depositMoney(depositAmount);
        return "redirect:/games/scheduled/display";
    }

    @RequestMapping("/withdraw")
    public String withdrawMoney() {
        return "wallet_withdraw";
    }

    @PostMapping("/withdraw")
    public String updateMoneyBalanceWithdraw(HttpServletRequest request) {
        BigDecimal withdrawAmount = new BigDecimal(request.getParameter("withdrawAmount"));
        if(walletServiceImpl.withdrawMoney(withdrawAmount)) {
            return "redirect:/games/scheduled/display";
        } else {
            return "warning_not_enough_money_withdraw";
        }
    }

    @RequestMapping("/panel")
    public String walletPanel() {
        return "wallet_panel";
    }
}
