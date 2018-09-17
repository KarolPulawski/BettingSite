package pl.coderslab.bettingsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Game;
import pl.coderslab.bettingsite.entity.Odd;
import pl.coderslab.bettingsite.entity.Team;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class StatisticService {

    private final Integer MAX_HOME_EXPECTATION = 57/5;
    private final Integer MAX_AWAY_EXPECTATION = 141/10;

    private final Double BETTING_SITE_PERCENTAGE = 1.1;

    @Autowired
    private GameService gameServiceImpl;

    public int lastFiveMatchesTotalPoints(Team team) {
        List<Game> games = gameServiceImpl.totalPointsLastFiveMatches(team.getId());
        int counter = 1;
        int totalPoints = 0;

        for (Game g : games) {
            if (g.getTeamHome().getId() == team.getId()) {
                try {
                    totalPoints += g.getHomePoint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                counter++;
            } else if (g.getTeamAway().getId() == team.getId()) {
                try {
                    totalPoints += g.getAwayPoint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                counter++;
            }
            if (counter == 5) break;
        }
        return totalPoints;
    }

    public int lastThreeMatchesHomeTotalPoints(Team team) {
        List<Game> games = gameServiceImpl.totalPointsLastThreeHomeMatches(team.getId());
        int totalPoints = 0;

        for(int i = 0; i < 3; i++) {
            try {
                totalPoints += games.get(i).getHomePoint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalPoints;
    }

    public int lastThreeMatchesAwayTotalPoints(Team team) {
        int totalPoints = 0;
        List<Game> games = gameServiceImpl.totalPointsLastThreeAwayMatches(team.getId());
        for(int i = 0; i < 3; i++) {
            try {
                totalPoints += games.get(i).getAwayPoint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalPoints;
    }

    public Odd generateOdd(Game game) {
        int homeTeamLastThree = lastThreeMatchesHomeTotalPoints(game.getTeamHome());
        int awayTeamLastThree = lastThreeMatchesAwayTotalPoints(game.getTeamAway());
        int homeTeamLastFive = lastFiveMatchesTotalPoints(game.getTeamHome());
        int awayTeamLastFive = lastFiveMatchesTotalPoints(game.getTeamAway());

        double homeExpectationPoints = homeTeamLastFive*0.4 + homeTeamLastThree*0.6;
        double awayExpectationPoints = awayTeamLastFive*0.4 + awayTeamLastThree*0.6*1.5;

        double homeExpectationPercentage = homeExpectationPoints / MAX_HOME_EXPECTATION;
        double awayExpectationPercentage = awayExpectationPoints / MAX_AWAY_EXPECTATION;

        return calculateOdd(homeExpectationPercentage, awayExpectationPercentage);
    }

    public Odd calculateOdd(double homeExpactationPercentage, double awayExapctationPercentage) {
        double difference = homeExpactationPercentage - awayExapctationPercentage;
        double homeOdd = 0.0;
        double awayOdd = 0.0;

        if(difference == 0) {
            homeOdd = 2.5;
            awayOdd = 2.5;
        } else if (difference > 0) {
            homeOdd = Math.exp(1-difference);
//            awayOdd = Math.exp(1+difference);
            awayOdd = Math.pow(3,1+difference);
        } else if (difference < 0) {
//            homeOdd = Math.exp(1+difference);
            homeOdd = Math.pow(3,1+difference);
            awayOdd = Math.exp(1-difference);
        }

        DecimalFormat df = new DecimalFormat("0.00");

        return new Odd(Double.parseDouble(df.format(homeOdd)),
                Double.parseDouble(df.format(calculateDrawOdd(homeOdd, awayOdd))),
                Double.parseDouble(df.format(awayOdd)));
    }

    private double calculateDrawOdd(double hOdd, double aOdd) {
        return (hOdd * aOdd) / (BETTING_SITE_PERCENTAGE*aOdd*hOdd - aOdd - hOdd);
    }
}
