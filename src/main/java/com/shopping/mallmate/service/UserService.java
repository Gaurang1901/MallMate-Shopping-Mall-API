package com.shopping.mallmate.service;

import com.shopping.mallmate.appconfig.JwtProvider;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByJwtToke(String token) {
        String email = JwtProvider.getEmailFromToken(token);
        return userRepository.findUserByEmail(email);
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }
}
