package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.entity.Wallet;
import pl.coderslab.bettingsite.repository.WalletRepository;
import pl.coderslab.bettingsite.service.UserService;
import pl.coderslab.bettingsite.service.WalletService;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserService userService;

    @Override
    public void saveNewWalletToDb(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public boolean depositMoney(BigDecimal depositAmount) {
        Wallet currentWallet = findByCurrentLoggedInUser();
        currentWallet.setBalance(currentWallet.getBalance().add(depositAmount));
        saveNewWalletToDb(currentWallet);
        return true;
    }

    @Override
    public boolean depositMoneyWin(BigDecimal winAmount, Wallet wallet) {
        wallet.setBalance(wallet.getBalance().add(winAmount));
        saveNewWalletToDb(wallet);
        return true;
    }

    @Override
    public boolean withdrawMoney(BigDecimal withdrawAmount) {
        Wallet currentWallet = findByCurrentLoggedInUser();
        if(currentWallet.getBalance().compareTo(withdrawAmount) >= 0) {
            currentWallet.setBalance(currentWallet.getBalance().subtract(withdrawAmount));
            saveNewWalletToDb(currentWallet);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Wallet findByCurrentLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return walletRepository.findByUser(userService.findUserByEmail(email));

    }

    @Override
    public boolean withdrawMoneyForStake(BigDecimal stakeAmount) {
        return false;
    }
}
