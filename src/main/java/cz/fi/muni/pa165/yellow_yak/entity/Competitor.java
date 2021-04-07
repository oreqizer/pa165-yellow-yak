package cz.fi.muni.pa165.yellow_yak.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Boris Petrenko
 */
public class Competitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @NotNull
    @CreatedDate
    private Date createdAt;

    public Competitor() {
    }

    public Long getId() {
        return id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competitor)) return false;

        Competitor that = (Competitor) o;

        if (!id.equals(that.getId())) return false;
        if (!competition.equals(that.getCompetition())) return false;
        if (!team.equals(that.getTeam())) return false;
        return createdAt.equals(that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getCompetition().hashCode();
        result = 31 * result + getTeam().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        return result;
    }
}