package com.shopping.mallmate.dto.order;

import com.shopping.mallmate.entity.enums.ORDER_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusRequest {

    private String orderId;
    private ORDER_STATUS status;
}
