package pl.coderslab.bettingsite.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal balance;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "wallet")
    List<WalletHistory> walletHistories = new ArrayList<>();

    public Wallet() {
    }

    public Wallet(BigDecimal balance, User user) {
        this.balance = balance;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<WalletHistory> getWalletHistories() {
        return walletHistories;
    }

    public void setWalletHistories(List<WalletHistory> walletHistories) {
        this.walletHistories = walletHistories;
    }
}
