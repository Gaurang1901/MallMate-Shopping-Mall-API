package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserByEmail(String email);

    User findUserById(String id);
}
