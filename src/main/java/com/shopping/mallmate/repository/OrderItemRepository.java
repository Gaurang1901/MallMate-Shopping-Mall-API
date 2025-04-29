package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    OrderItem findItemsById(String id);
}
