package com.ecore.roleapi.beans.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Users {

    @JsonProperty("id")
    private String id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("location")
    private String location;

}
