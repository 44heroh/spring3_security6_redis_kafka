package com.example.spring3security6docker.rest.controller;

import com.example.spring3security6docker.dao.entity.ERole;
import com.example.spring3security6docker.dao.entity.Role;
import com.example.spring3security6docker.dao.entity.User;
import com.example.spring3security6docker.dto.UserDto;
import com.example.spring3security6docker.dto.request.LoginRequest;
import com.example.spring3security6docker.dto.request.SignupRequest;
import com.example.spring3security6docker.dto.response.JwtResponse;
import com.example.spring3security6docker.dto.response.MessageResponse;
import com.example.spring3security6docker.mapper.UserMapper;
import com.example.spring3security6docker.repository.RoleRepository;
import com.example.spring3security6docker.repository.UserRepository;
import com.example.spring3security6docker.security.jwt.JwtUtils;
import com.example.spring3security6docker.security.services.UserDetailsImpl;
import com.example.spring3security6docker.service.UserService;
import com.example.spring3security6docker.service.impl.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final UserService userService;

    private final RoleServiceImpl roleService;

    private final JwtUtils jwtUtils;

    private final UserMapper userMapper;

    @GetMapping("/get-role-user")
    public ResponseEntity<?> getRoleUser() {
        Optional<Role> roleUser = roleService.findByName(String.valueOf(ERole.ROLE_USER));

        return ResponseEntity.of(roleUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }
}
