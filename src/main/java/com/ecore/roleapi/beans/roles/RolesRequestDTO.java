package com.ecore.roleapi.beans.roles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesRequestDTO {

    @JsonProperty("name")
    private String name;

}
