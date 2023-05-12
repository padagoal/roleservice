package com.ecore.roleapi.beans.teams;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Teams {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("teamLeadId")
    private String teamLeadId;

    @JsonProperty("teamMemberIds")
    private String[] teamMemberIds;


    public Boolean isUserinTeam(String userId){
        boolean userIsATeamMember = false;
        for (String userIdTeam:teamMemberIds) {
            if (userIdTeam.equals(userId)) {
                userIsATeamMember = true;
                break;
            }
        }
        if(teamLeadId.equals(userId))userIsATeamMember = true;
        return userIsATeamMember;
    }
}
