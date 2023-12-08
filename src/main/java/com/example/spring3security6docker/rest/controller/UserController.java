package com.example.spring3security6docker.rest.controller;

import com.example.spring3security6docker.dto.UserDto;
import com.example.spring3security6docker.dto.request.SignupRequest;
import com.example.spring3security6docker.dto.response.MessageResponse;
import com.example.spring3security6docker.mapper.UserMapper;
import com.example.spring3security6docker.repository.UserRepository;
import com.example.spring3security6docker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<?> addUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        UserDto ans = userMapper.map(userService.registerUser(signUpRequest));

        return ResponseEntity.ok(ans);
    }
}
