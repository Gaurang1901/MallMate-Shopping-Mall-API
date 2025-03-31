package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findUserByEmail(String email);
}
