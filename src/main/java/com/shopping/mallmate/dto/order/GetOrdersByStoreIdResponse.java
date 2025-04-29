package com.shopping.mallmate.dto.order;

import com.shopping.mallmate.dto.order.orderItem.OrderItemModal;
import com.shopping.mallmate.dto.user.address.AddressModal;
import com.shopping.mallmate.entity.enums.ORDER_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrdersByStoreIdResponse {

    private String orderId;

    private ORDER_STATUS orderStatus;

    private String userId;

    private String userName;

    private double amount;

    private AddressModal addressModal;

    private List<OrderItemModal> orderItems;
}
