package com.example.spring3security6docker.dao.entity;

import com.example.spring3security6docker.dto.request.SignupRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "courses")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
    schema = "courses"
    )
    private Set<Role> roles;

    public User(SignupRequest signUpRequest, String password) {
        this.username = signUpRequest.getEmail();
        this.lastName = signUpRequest.getLastName();
        this.firstName = signUpRequest.getFirstName();
        this.email = signUpRequest.getEmail();
        this.password = password;
        this.setCreated(new Date());
        this.setUpdated(new Date());
        this.setStatus(Status.NOT_ACTIVE);
    }
}
