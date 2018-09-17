package pl.coderslab.bettingsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.bettingsite.entity.Team;
import pl.coderslab.bettingsite.repository.TeamRepository;
import pl.coderslab.bettingsite.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team loadTeamByName(String nameTeam) {
        return teamRepository.findTeamByName(nameTeam);
    }
}
