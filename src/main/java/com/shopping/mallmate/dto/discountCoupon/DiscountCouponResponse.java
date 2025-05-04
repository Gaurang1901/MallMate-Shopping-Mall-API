package com.shopping.mallmate.dto.discountCoupon;

import com.shopping.mallmate.entity.DiscountCoupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiscountCouponResponse {

    private String id;

    private String code;

    private Double discountPercentage;

    private Boolean isActive;

    public static DiscountCouponResponse fromEntity(DiscountCoupon discountCoupon) {
        return new DiscountCouponResponse(
                discountCoupon.getId(),
                discountCoupon.getCode(),
                discountCoupon.getDiscountPercentage(),
                discountCoupon.getIsActive()
        );
    }
}
