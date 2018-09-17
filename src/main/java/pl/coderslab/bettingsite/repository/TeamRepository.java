package pl.coderslab.bettingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.bettingsite.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    Team findTeamByName(String name);
}
