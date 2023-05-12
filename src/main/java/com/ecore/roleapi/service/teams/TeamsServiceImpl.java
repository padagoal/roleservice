package com.ecore.roleapi.service.teams;

import com.ecore.roleapi.beans.teams.Teams;
import com.ecore.roleapi.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TeamsServiceImpl implements TeamsService{

    private final String URL_GET_TEAM = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/%s";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public Teams getTeam(String id) {
        Teams team = restTemplate.getForObject(String.format(URL_GET_TEAM,id),Teams.class);
        if(team == null){
            log.warn("The team ID: [{}] was not found",id);
            throw new ResourceNotFoundException(
                    String.format("The team ID: [%s] was not found", id));
        }
        return team;
    }

    @Override
    public Boolean isUserInTeam(String teamId, String userId) {
        Teams team = getTeam(teamId);

        boolean userInTeam = team.isUserinTeam(userId);
        if(!userInTeam){
            log.warn("The user in the request doesn't below to the team");
            throw new ResourceNotFoundException(
                    String.format("The user ID: [%s] in the request doesn't below to the team ID: [%s]",
                            userId,teamId));
        }

        return true;
    }
}
