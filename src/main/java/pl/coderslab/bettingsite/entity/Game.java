package pl.coderslab.bettingsite.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Team teamHome;

    @OneToOne
    private Team teamAway;

    @OneToOne
    private Competition competition;

    @OneToOne
    private Odd odd;

    private Timestamp started;

    private Boolean active;
    private Boolean history;
    private Boolean finished;
    private Boolean scheduled;

    private int homeGoal;
    private int homeCorner;
    private int homeYellow;
    private int homeRed;
    private int homePenalty;
    private int homePoint;
    private int awayGoal;
    private int awayCorner;
    private int awayYellow;
    private int awayRed;
    private int awayPenalty;
    private int awayPoint;

    @OneToMany(mappedBy = "game")
    private List<Bet> bets;

    public Game() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Team getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(Team teamHome) {
        this.teamHome = teamHome;
    }

    public Team getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(Team teamAway) {
        this.teamAway = teamAway;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Odd getOdd() {
        return odd;
    }

    public void setOdd(Odd odd) {
        this.odd = odd;
    }

    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public int getHomeGoal() {
        return homeGoal;
    }

    public void setHomeGoal(int homeGoal) {
        this.homeGoal = homeGoal;
    }

    public int getHomeCorner() {
        return homeCorner;
    }

    public void setHomeCorner(int homeCorner) {
        this.homeCorner = homeCorner;
    }

    public int getHomeYellow() {
        return homeYellow;
    }

    public void setHomeYellow(int homeYellow) {
        this.homeYellow = homeYellow;
    }

    public int getHomeRed() {
        return homeRed;
    }

    public void setHomeRed(int homeRed) {
        this.homeRed = homeRed;
    }

    public int getHomePenalty() {
        return homePenalty;
    }

    public void setHomePenalty(int homePenalty) {
        this.homePenalty = homePenalty;
    }

    public int getHomePoint() {
        return homePoint;
    }

    public void setHomePoint(int homePoint) {
        this.homePoint = homePoint;
    }

    public int getAwayGoal() {
        return awayGoal;
    }

    public void setAwayGoal(int awayGoal) {
        this.awayGoal = awayGoal;
    }

    public int getAwayCorner() {
        return awayCorner;
    }

    public void setAwayCorner(int awayCorner) {
        this.awayCorner = awayCorner;
    }

    public int getAwayYellow() {
        return awayYellow;
    }

    public void setAwayYellow(int awayYellow) {
        this.awayYellow = awayYellow;
    }

    public int getAwayRed() {
        return awayRed;
    }

    public void setAwayRed(int awayRed) {
        this.awayRed = awayRed;
    }

    public int getAwayPenalty() {
        return awayPenalty;
    }

    public void setAwayPenalty(int awayPenalty) {
        this.awayPenalty = awayPenalty;
    }

    public int getAwayPoint() {
        return awayPoint;
    }

    public void setAwayPoint(int awayPoint) {
        this.awayPoint = awayPoint;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getScheduled() {
        return scheduled;
    }

    public void setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
    }
}

