package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Wallet;

@Service
public interface WalletService {
    void saveNewWalletToDb(Wallet wallet);
}
