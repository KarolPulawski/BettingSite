package pl.coderslab.bettingsite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDto {
    @JsonProperty("gameId")
    private String gameId;

    @JsonProperty("teamHome")
    private String teamHome;

    @JsonProperty("teamAway")
    private String teamAway;

    @JsonProperty("started")
    private String started;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("scheduled")
    private boolean scheduled;

    @JsonProperty("finished")
    private boolean finished;

    @JsonProperty("history")
    private boolean history;

    @JsonProperty("homeOdd")
    private double homeOdd;

    @JsonProperty("drawOdd")
    private double drawOdd;

    @JsonProperty("awayOdd")
    private double awayOdd;

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

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public boolean isFinished() {
        return finished;
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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean getScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
