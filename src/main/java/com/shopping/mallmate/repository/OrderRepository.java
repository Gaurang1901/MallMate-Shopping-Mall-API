package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.Order;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.entity.enums.ORDER_STATUS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    Order findOrderById(String id);

    Page<Order> findOrdersByUserId(String userId, Pageable pageable);

    Page<Order> findOrderByStoreId(String storeId, Pageable pageable);

    Page<Order> findOrdersByOrderStatus(ORDER_STATUS orderStatus, Pageable pageable);
}
