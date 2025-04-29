package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.Cart;
import com.shopping.mallmate.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, String> {

    CartItems findByProductId(String productId);

    List<CartItems> findItemsByCartId(String cartId);

    CartItems findByCartIdAndProductId(String cartId, String productId);

    void deleteAllByCartId(String cartId);

}
