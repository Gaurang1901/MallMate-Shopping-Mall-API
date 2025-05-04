package com.shopping.mallmate.dto.discountCoupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateDiscountCouponResponse {

    private String message;

    private int status;

    private String discountCouponId;

    private boolean isValid;

}
