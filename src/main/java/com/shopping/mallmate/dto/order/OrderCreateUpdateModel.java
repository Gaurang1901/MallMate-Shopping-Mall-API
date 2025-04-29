package com.shopping.mallmate.dto.order;


import com.shopping.mallmate.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateUpdateModel {

    private String id;

    private String userId;

    private String storeId;

    private double amount;

    private String shippingAddressId;

    private String discountCouponId;

    private List<String> orderItemIds;
}
