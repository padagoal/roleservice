package com.ecore.roleapi.beans.membership;

import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MembershipResponseDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("userid")
    private String userId;

    @JsonProperty("teamId")
    private String teamId;

    @JsonProperty("rolId")
    private RolesResponseDTO rolId;

}
