package com.ecore.roleapi.service.teams;

import com.ecore.roleapi.beans.teams.Teams;

public interface TeamsService {
    Teams getTeam(String id);

    Boolean isUserInTeam(String teamId,String userId);
}
