package com.ecore.roleapi.beans.membership;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MembershipRequestDTO {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("teamId")
    private String teamId;

    @JsonProperty("rolId")
    private String rolId;

}
