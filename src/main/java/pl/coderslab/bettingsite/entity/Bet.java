package pl.coderslab.bettingsite.entity;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Game game;

    private String type;

    public Bet() {
    }

    public Bet(Game game, String type) {
        this.game = game;
        this.type = type;
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
}
