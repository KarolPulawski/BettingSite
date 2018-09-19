package pl.coderslab.bettingsite.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "wallethistories")
public class WalletHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Wallet wallet;

    private String action;
    private Timestamp actionTime;
}
