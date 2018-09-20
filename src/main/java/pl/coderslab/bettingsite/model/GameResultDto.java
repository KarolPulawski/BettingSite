package pl.coderslab.bettingsite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameResultDto extends GameDto {
    @JsonProperty("homeGoal")
    private int homeGoal;
    
    @JsonProperty("homeYellow")
    private int homeYellow;

    @JsonProperty("homeRed")
    private int homeRed;
    
    @JsonProperty("homePenalty")
    private int homePenalty;
    
    @JsonProperty("homePoint")
    private int homePoint;

    @JsonProperty("awayGoal")
    private int awayGoal;

    @JsonProperty("awayYellow")
    private int awayYellow;

    @JsonProperty("awayRed")
    private int awayRed;

    @JsonProperty("awayPenalty")
    private int awayPenalty;

    @JsonProperty("awayPoint")
    private int awayPoint;

    @JsonProperty("homeCorner")
    private int homeCorner;

    @JsonProperty("awayCorner")
    private int awayCorner;

    public int getHomeCorner() {
        return homeCorner;
    }

    public void setHomeCorner(int homeCorner) {
        this.homeCorner = homeCorner;
    }

    public int getAwayCorner() {
        return awayCorner;
    }

    public void setAwayCorner(int awayCorner) {
        this.awayCorner = awayCorner;
    }

    public int getHomeGoal() {
        return homeGoal;
    }

    public void setHomeGoal(int homeGoal) {
        this.homeGoal = homeGoal;
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
}

