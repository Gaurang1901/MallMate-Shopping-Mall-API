package com.shopping.mallmate.service;

import com.shopping.mallmate.appconfig.JwtProvider;
import com.shopping.mallmate.dto.user.UserModal;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public User findUserByJwtToken(String token) {
        String email = jwtProvider.getEmailFromJwtToken(token);
        return userRepository.findUserByEmail(email);
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    public User findUserById(String id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
