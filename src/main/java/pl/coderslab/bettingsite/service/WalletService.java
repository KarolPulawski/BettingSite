package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.entity.Wallet;

import java.math.BigDecimal;

@Service
public interface WalletService {
    void saveNewWalletToDb(Wallet wallet);

    Wallet findByCurrentLoggedInUser();

    boolean depositMoney(BigDecimal depositAmount);
    boolean depositMoneyWin(BigDecimal depositAmount, Wallet wallet);
    boolean withdrawMoney(BigDecimal withdrawAmount);
    boolean withdrawMoneyForStake(BigDecimal stakeAmount);
}
