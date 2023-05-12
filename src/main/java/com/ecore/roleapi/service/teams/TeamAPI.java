package com.ecore.roleapi.service.teams;

import com.ecore.roleapi.beans.teams.Teams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamAPI {

    private final TeamsService teamsService;

    @GetMapping(value = "/{teamid}")
    public Teams getTeamById(@PathVariable(name = "teamid") String teamid ){
        return teamsService.getTeam(teamid);
    }

}
