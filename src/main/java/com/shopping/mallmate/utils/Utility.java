package com.shopping.mallmate.utils;

import com.shopping.mallmate.dto.order.orderItem.OrderItemModal;
import com.shopping.mallmate.entity.Order;
import com.shopping.mallmate.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static List<OrderItemModal> getOrderItemModals(Order order) {
        List<OrderItemModal> itemModals = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                OrderItemModal modal = new OrderItemModal();
                modal.setId(item.getId());
                modal.setProductId(item.getProduct().getId());
                modal.setProductName(item.getProduct().getName());
                modal.setQuantity(item.getQuantity());
                modal.setAmount(item.getAmount());
                itemModals.add(modal);
            }
        }
        return itemModals;
    }
}
