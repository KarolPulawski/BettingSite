package pl.coderslab.bettingsite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "odds")
public class Odd {
    @Id
    @GeneratedValue
    private Integer id;

    private Double homeOdd;
    private Double drawOdd;
    private Double awayOdd;

    public Odd(Double homeOdd, Double drawOdd, Double awayOdd) {
        this.homeOdd = homeOdd;
        this.drawOdd = drawOdd;
        this.awayOdd = awayOdd;
    }

    public Odd() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHomeOdd(Double homeOdd) {
        this.homeOdd = homeOdd;
    }

    public void setDrawOdd(Double drawOdd) {
        this.drawOdd = drawOdd;
    }

    public void setAwayOdd(Double awayOdd) {
        this.awayOdd = awayOdd;
    }

    public Double getHomeOdd() {
        return homeOdd;
    }

    public Double getDrawOdd() {
        return drawOdd;
    }

    public Double getAwayOdd() {
        return awayOdd;
    }
}
