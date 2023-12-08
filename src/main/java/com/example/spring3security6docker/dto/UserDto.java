package com.example.spring3security6docker.dto;

import com.example.spring3security6docker.dao.entity.Role;
import com.example.spring3security6docker.dao.entity.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;
import java.util.Set;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty("created")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date created;
    @JsonProperty("updated")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updated;
    private Status status;
    private Set<Role> roles;
}
