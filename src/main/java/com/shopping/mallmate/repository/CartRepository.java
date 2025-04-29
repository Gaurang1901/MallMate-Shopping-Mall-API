package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {

    Cart findCartByUserId(String userId);




}
