package pl.coderslab.bettingsite.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Team;

@Service
public interface TeamService {
    Team loadTeamByName(String nameTeam);
}
