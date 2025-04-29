package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.order.GetOrdersByStoreIdResponse;
import com.shopping.mallmate.dto.order.GetOrdersByUserIdResponse;
import com.shopping.mallmate.dto.order.OrderCreateUpdateModel;
import com.shopping.mallmate.dto.order.orderItem.OrderItemModal;
import com.shopping.mallmate.dto.user.address.AddressModal;
import com.shopping.mallmate.entity.Order;
import com.shopping.mallmate.entity.OrderItem;
import com.shopping.mallmate.repository.OrderRepository;
import com.shopping.mallmate.repository.StoreRepository;
import com.shopping.mallmate.repository.UserRepository;
import com.shopping.mallmate.service.OrderService;
import com.shopping.mallmate.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderCreateUpdateModel order) {
        Order createdOrder = orderService.createOrder(order);
        ApiResponse response = new ApiResponse("Order created successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOrder(@RequestBody OrderCreateUpdateModel order, @PathVariable String id) {
        Order updatedOrder = orderService.updateOrder(order, id);
        ApiResponse response = new ApiResponse("Order updated successfully", HttpStatus.ACCEPTED.value());
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable String id) {
        orderService.cancelOrder(id);
        ApiResponse response = new ApiResponse("Order cancelled successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<GetOrdersByUserIdResponse>> getOrdersByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort
    ) {
        // Extract sorting
        Sort sortOrder = Sort.by(Sort.Order.desc("createdAt")); // default
        if (sort.length > 0) {
            String sortBy = sort[0];
            Sort.Direction direction = sort.length > 1 && sort[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            sortOrder = Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Order> orderPage = orderService.findOrdersByUserId(userId, pageable);

        // Map Page<Order> to Page<GetOrdersByUserIdResponse>
        Page<GetOrdersByUserIdResponse> responsePage = orderPage.map(this::mapToOrderByUserResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }




    private GetOrdersByUserIdResponse mapToOrderByUserResponse(Order order) {
        List<OrderItemModal> itemModals = Utility.getOrderItemModals(order);

        GetOrdersByUserIdResponse response = new GetOrdersByUserIdResponse();
        response.setOrderId(order.getId());
        response.setOrderStatus(order.getOrderStatus());

        if (order.getStore() != null) {
            response.setStoreId(order.getStore().getId());
            response.setStoreName(order.getStore().getName());
        }

        response.setOrderItems(itemModals);
        response.setAmount(Objects.equals(order.getOriginalAmount(), order.getDiscountedAmount())
                ? order.getOriginalAmount() : order.getDiscountedAmount());

        if (order.getShippingAddress() != null) {
            response.setAddressModal(AddressModal.fromEntity(order.getShippingAddress()));
        }

        return response;
    }



}
