package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.order.GetOrdersByStoreIdResponse;
import com.shopping.mallmate.dto.order.UpdateOrderStatusRequest;
import com.shopping.mallmate.dto.order.orderItem.OrderItemModal;
import com.shopping.mallmate.dto.user.address.AddressModal;
import com.shopping.mallmate.entity.Order;
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

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(request.getOrderId(),request.getStatus());
        ApiResponse response = new ApiResponse("Order status updated successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<Page<GetOrdersByStoreIdResponse>> getOrdersByStoreId(
            @PathVariable String storeId,
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

        Page<Order> orderPage = orderService.findOrdersByStoreId(storeId, pageable);

        Page<GetOrdersByStoreIdResponse> responsePage = orderPage.map(this::mapToOrderByStoreResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    private GetOrdersByStoreIdResponse mapToOrderByStoreResponse(Order order) {
        List<OrderItemModal> itemModals = Utility.getOrderItemModals(order);

        GetOrdersByStoreIdResponse response = new GetOrdersByStoreIdResponse();
        response.setOrderId(order.getId());
        response.setOrderStatus(order.getOrderStatus());

        if (order.getStore() != null) {
            response.setUserId(order.getUser().getId());
            response.setUserName(order.getUser().getName());
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
