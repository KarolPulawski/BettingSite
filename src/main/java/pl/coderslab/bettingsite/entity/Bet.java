package pl.coderslab.bettingsite.entity;

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

    @ManyToOne
    private Ticket ticket;

    public Bet() {
    }

    public Bet(Game game, String type, double odd) {
        this.game = game;
        this.type = type;
        this.odd = odd;
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
