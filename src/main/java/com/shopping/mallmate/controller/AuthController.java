package com.shopping.mallmate.controller;

import com.shopping.mallmate.appconfig.JwtProvider;
import com.shopping.mallmate.dto.AuthResponse;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.entity.enums.USER_ROLE;
import com.shopping.mallmate.repository.UserRepository;
import com.shopping.mallmate.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    ResponseEntity<AuthResponse> signUp(@RequestBody User authRequest) {

        User alreadyUser = userRepository.findUserByEmail(authRequest.getEmail());
        if (alreadyUser != null) {
            return ResponseEntity.badRequest().build();
        } else {

            User user = new User();
            user.setEmail(authRequest.getEmail());
            user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            user.setName(authRequest.getName());

            User savedUser = userRepository.save(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtProvider.generateToken(authentication);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(jwt);
            authResponse.setRole(savedUser.getRole());
            authResponse.setMsg("User registered successfully");
            authResponse.setStatus(HttpStatus.CREATED.value());
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }
    }

    @PostMapping("/signin")
    ResponseEntity<AuthResponse> signIn(@RequestBody User authRequest) {
        String username = authRequest.getEmail();
        String password = authRequest.getPassword();

        Authentication authentication = getAuthentication(username, password);

        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setMsg("User logged in successfully");
        authResponse.setStatus(HttpStatus.ACCEPTED.value());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        authResponse.setRole(USER_ROLE.valueOf(roles));
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);

    }

    private Authentication getAuthentication(String username, String password) {
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
