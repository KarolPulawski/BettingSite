package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Wallet currentWallet = walletRepository.findByUser(userService.findUserByEmail(userName));
        currentWallet.setBalance(currentWallet.getBalance().add(depositAmount));
        saveNewWalletToDb(currentWallet);
        return true;
    }

    @Override
    public boolean withdrawMoney(BigDecimal withdrawAmount) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Wallet currentWallet = walletRepository.findByUser(userService.findUserByEmail(userName));
        if(currentWallet.getBalance().compareTo(withdrawAmount) >= 0) {
            currentWallet.setBalance(currentWallet.getBalance().subtract(withdrawAmount));
            saveNewWalletToDb(currentWallet);
            return true;
        } else {
            return false;
        }
    }
}
