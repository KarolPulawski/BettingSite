package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Wallet;
import pl.coderslab.bettingsite.repository.WalletRepository;
import pl.coderslab.bettingsite.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void saveNewWalletToDb(Wallet wallet) {
        walletRepository.save(wallet);
    }
}
