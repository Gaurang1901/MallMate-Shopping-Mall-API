package com.shopping.mallmate.dto.order.orderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModal {

    private String id;

    private String productId;

    private String productName;

    private int quantity;

    private double amount;

}
