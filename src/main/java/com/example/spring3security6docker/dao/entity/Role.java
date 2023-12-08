package com.example.spring3security6docker.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles", schema = "courses")
@Data
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }

//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//    private List<User> users;

}
