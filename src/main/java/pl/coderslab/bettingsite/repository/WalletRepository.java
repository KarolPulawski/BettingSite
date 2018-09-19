package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.bettingsite.entity.User;
import pl.coderslab.bettingsite.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByUser(User user);
}
