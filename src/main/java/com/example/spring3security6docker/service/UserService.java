package com.example.spring3security6docker.service;

import com.example.spring3security6docker.dao.entity.User;
import com.example.spring3security6docker.dto.UserDto;
import com.example.spring3security6docker.dto.request.SignupRequest;

import java.util.Optional;

public interface UserService {

    public User registerUser(SignupRequest signUpRequest);
}
