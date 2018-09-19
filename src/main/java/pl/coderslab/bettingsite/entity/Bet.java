package pl.coderslab.bettingsite.entity;

import pl.coderslab.bettingsite.model.BetStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Game game;

    private String type;

    private double odd;

    @Enumerated(EnumType.STRING)
    private BetStatus betStatus;

    @ManyToOne
    private Ticket ticket;

    public Bet() {
    }

    public Bet(Game game, String type, double odd, BetStatus betStatus) {
        this.game = game;
        this.type = type;
        this.odd = odd;
        this.betStatus = betStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public BetStatus getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
    }

    @Override
    public boolean equals(Object o) {

        try {
            int id1 = this.game.getId();
            int id2 = ((Bet) o).game.getId();
            boolean result = id1 == id2;
           return result;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public int hashCode() {

        return Objects.hash(getGame().getId());
    }
}
