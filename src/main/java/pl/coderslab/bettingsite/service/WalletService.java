package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Wallet;

import java.math.BigDecimal;

@Service
public interface WalletService {
    void saveNewWalletToDb(Wallet wallet);

    void depositMoney(BigDecimal depositAmount);
    void withdrawMoney(BigDecimal depositAmount);
}
