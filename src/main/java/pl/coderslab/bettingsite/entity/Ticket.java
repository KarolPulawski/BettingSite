package pl.coderslab.bettingsite.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "ticket")
    private List<Bet> bets;

    @OneToOne
    private User user;

    private Boolean active;
    private Boolean paid;
    private Boolean win;

    private BigDecimal stake;
    private BigDecimal totalOdd;
    private BigDecimal expectedWin;

    public Ticket() {
    }

    public Ticket(Set<Bet> bets, User user, Boolean active, Boolean paid, Boolean win, double stake) {
        this.bets = new ArrayList<>();
        for(Bet bet : bets) {
            this.bets.add(bet);
        }
        this.user = user;
        this.active = active;
        this.paid = paid;
        this.win = win;
        this.stake = new BigDecimal(stake);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
    public void setBets(Set<Bet> bets) {
        this.bets = new ArrayList<>();
        for(Bet bet : bets) {
            this.bets.add(bet);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getStake() {
        return this.stake;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getTotalOdd() {
        return totalOdd.setScale(2, RoundingMode.HALF_DOWN);
    }

    public void setTotalOdd(BigDecimal totalOdd) {
        this.totalOdd = totalOdd;
    }

    public BigDecimal getExpectedWin() {
        return this.expectedWin.setScale(2, RoundingMode.HALF_DOWN);
    }

    public void setExpectedWin() {
        this.expectedWin = this.totalOdd.multiply(this.stake);
    }

}
