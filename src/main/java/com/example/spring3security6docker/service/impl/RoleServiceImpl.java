package com.example.spring3security6docker.service.impl;

import com.example.spring3security6docker.dao.entity.Role;
import com.example.spring3security6docker.repository.CourseRepository;
import com.example.spring3security6docker.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl {

    private final RoleRepository repository;

    public Optional<Role> findByName(String name){
        return repository.findByName(name);
    }
}
