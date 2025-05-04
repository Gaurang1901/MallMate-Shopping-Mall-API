package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.order.OrderCreateUpdateModel;
import com.shopping.mallmate.entity.*;
import com.shopping.mallmate.entity.enums.ORDER_STATUS;
import com.shopping.mallmate.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DiscountCouponRepository couponRepository;

    public Order createOrder(OrderCreateUpdateModel order) {
        User user = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setStore(store);
        createdOrder.setOrderStatus(ORDER_STATUS.ORDERED);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setUpdatedAt(new Date());

        setShippingAddress(createdOrder, order.getShippingAddressId());

        List<OrderItem> orderItems = getOrderItemsFromOrderIds(order, createdOrder);
        createdOrder.setOrderItems(orderItems);

        double totalAmount = orderItems.stream().mapToDouble(OrderItem::getAmount).sum();
        applyDiscount(createdOrder, totalAmount, order.getDiscountCouponId());

        Order savedOrder = orderRepository.save(createdOrder);

        orderItems.forEach(item -> {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        });

        return savedOrder;
    }

    public Order updateOrder(OrderCreateUpdateModel order, String orderId) {
        Order existingOrder = orderRepository.findOrderById(orderId);
        if (existingOrder == null) {
            throw new RuntimeException("Order not found");
        }

        if (!order.getOrderItemIds().isEmpty()) {
            List<OrderItem> orderItems = getOrderItemsFromOrderIds(order, existingOrder);
            existingOrder.setOrderItems(orderItems);
            double amount = orderItems.stream().mapToDouble(OrderItem::getAmount).sum();
            applyDiscount(existingOrder, amount, order.getDiscountCouponId());
        } else {
            existingOrder.setOrderItems(null);
            existingOrder.setOrderStatus(ORDER_STATUS.CANCELLED);
            applyDiscount(existingOrder, 0.0, null);
        }

        setShippingAddress(existingOrder, order.getShippingAddressId());

        existingOrder.setUpdatedAt(new Date());
        return orderRepository.save(existingOrder);
    }

    public void updateOrderStatus(String orderId, ORDER_STATUS orderStatus) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }

    public void cancelOrder(String orderId) {
        updateOrderStatus(orderId, ORDER_STATUS.CANCELLED);
    }

    public Page<Order> findOrdersByUserId(String userId, Pageable pageable) {
        return orderRepository.findOrdersByUserId(userId, pageable);
    }

    public Page<Order> findOrdersByStoreId(String storeId, Pageable pageable) {
        return orderRepository.findOrderByStoreId(storeId, pageable);
    }

    public Page<Order> findOrdersByOrderStatus(ORDER_STATUS orderStatus, Pageable pageable) {
        return orderRepository.findOrdersByOrderStatus(orderStatus, pageable);
    }

    private void applyDiscount(Order orderEntity, double amount, String couponId) {
        if (couponId != null) {
            DiscountCoupon coupon = couponRepository.findDiscountCouponById(couponId);
            if (coupon == null) throw new RuntimeException("Discount coupon not found");
            orderEntity.setDiscountCoupon(coupon);
            double discount = amount * coupon.getDiscountPercentage() / 100;
            orderEntity.setDiscountedAmount(amount - discount);
        } else {
            orderEntity.setDiscountCoupon(null);
            orderEntity.setDiscountedAmount(amount);
        }
        orderEntity.setOriginalAmount(amount);
    }

    private void setShippingAddress(Order orderEntity, String addressId) {
        if (addressId != null) {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Shipping address not found"));
            orderEntity.setShippingAddress(address);
        }
    }

    private List<OrderItem> getOrderItemsFromOrderIds(OrderCreateUpdateModel order, Order orderEntity) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (String id : order.getOrderItemIds()) {
            OrderItem orderItem = orderItemRepository.findItemsById(id);
            if (orderItem == null) {
                Product product = productRepository.findProductById(id);
                if (product == null) throw new RuntimeException("Product not found with ID: " + id);
                orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(1);
                orderItem.setAmount(product.getPrice());
            }
            orderItem.setOrder(orderEntity);
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}