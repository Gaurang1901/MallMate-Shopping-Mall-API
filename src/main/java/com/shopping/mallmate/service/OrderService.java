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

    public List<OrderItem> getOrderItemsFromOrderIds(OrderCreateUpdateModel order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (String id : order.getOrderItemIds()) {
            OrderItem orderItem = orderItemRepository.findItemsById(id);
            if (orderItem == null) {
                Product product = productRepository.findProductById(id);
                if (product == null) {
                    throw new RuntimeException("Product not found with ID: " + id);
                }
                orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(1);
                orderItem.setAmount(product.getPrice());
            }
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public Order createOrder(OrderCreateUpdateModel order) {
        User user = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));

        List<OrderItem> orderItems = getOrderItemsFromOrderIds(order);

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setStore(store);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setOrderStatus(ORDER_STATUS.ORDERED);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setUpdatedAt(new Date());

        // Optional: Address
        if (order.getShippingAddressId() != null) {
            Address address = addressRepository.findById(order.getShippingAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Shipping address not found"));
            createdOrder.setShippingAddress(address);
        }

        // Calculate amount
        double totalAmount = orderItems.stream().mapToDouble(OrderItem::getAmount).sum();
        createdOrder.setOriginalAmount(totalAmount);

        if (order.getDiscountCouponId() != null) {
            DiscountCoupon coupon = couponRepository.findById(order.getDiscountCouponId())
                    .orElseThrow(() -> new EntityNotFoundException("Discount coupon not found"));
            createdOrder.setDiscountCoupon(coupon);
            double discount = totalAmount * coupon.getDiscountPercentage() / 100;
            createdOrder.setDiscountedAmount(totalAmount - discount);
        } else {
            createdOrder.setDiscountedAmount(totalAmount);
        }

        Order savedOrder = orderRepository.save(createdOrder);

        // Link and save items
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
            List<OrderItem> orderItems = getOrderItemsFromOrderIds(order);
            existingOrder.setOrderItems(orderItems);
            double amount = orderItems.stream().mapToDouble(OrderItem::getAmount).sum();
            existingOrder.setOriginalAmount(amount);
            existingOrder.setDiscountedAmount(amount);
        } else {
            existingOrder.setOrderItems(null);
            existingOrder.setOrderStatus(ORDER_STATUS.CANCELLED);
            existingOrder.setOriginalAmount(0.0);
            existingOrder.setDiscountedAmount(0.0);
        }

        if (order.getDiscountCouponId() != null) {
            DiscountCoupon discountCoupon = couponRepository.findDiscountCouponById(order.getDiscountCouponId());
            if (discountCoupon == null) {
                throw new RuntimeException("Discount coupon not found");
            }
            existingOrder.setDiscountCoupon(discountCoupon);
            existingOrder.setOriginalAmount(order.getAmount());
            double discount = order.getAmount() * discountCoupon.getDiscountPercentage() / 100;
            existingOrder.setDiscountedAmount(order.getAmount() - discount);
        } else {
            existingOrder.setDiscountCoupon(null);
            existingOrder.setOriginalAmount(order.getAmount());
            existingOrder.setDiscountedAmount(order.getAmount());
        }

        if (order.getShippingAddressId() != null) {
            Address shippingAddress = addressRepository.findAddressById(order.getShippingAddressId());
            if (shippingAddress == null) {
                throw new RuntimeException("Shipping address not found");
            }
            existingOrder.setShippingAddress(shippingAddress);
        }

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
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        order.setOrderStatus(ORDER_STATUS.CANCELLED);
        orderRepository.save(order);
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
}