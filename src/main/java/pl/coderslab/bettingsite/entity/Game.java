package pl.coderslab.bettingsite.entity;

import pl.coderslab.bettingsite.model.GameDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue
    private Integer id;

    private String teamHome;
    private String teamAway;

    private String competition;

    private double homeOdd;
    private double drawOdd;
    private double awayOdd;

    private Timestamp started;

    private boolean active;
    private boolean history;

    public Game() {
    }

    public Game(GameDto gameDto) {
        this.teamHome = gameDto.getTeamHome();
        this.teamAway = gameDto.getTeamAway();

        this.homeOdd = gameDto.getHomeOdd();
        this.drawOdd = gameDto.getDrawOdd();
        this.awayOdd = gameDto.getAwayOdd();

        this.active = gameDto.isActive();
        this.history = gameDto.isHistory();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public double getHomeOdd() {
        return homeOdd;
    }

    public void setHomeOdd(double homeOdd) {
        this.homeOdd = homeOdd;
    }

    public double getDrawOdd() {
        return drawOdd;
    }

    public void setDrawOdd(double drawOdd) {
        this.drawOdd = drawOdd;
    }

    public double getAwayOdd() {
        return awayOdd;
    }

    public void setAwayOdd(double awayOdd) {
        this.awayOdd = awayOdd;
    }

    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }
}

