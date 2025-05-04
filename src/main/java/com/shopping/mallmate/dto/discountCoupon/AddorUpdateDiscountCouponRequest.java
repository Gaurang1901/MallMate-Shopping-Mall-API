package com.shopping.mallmate.dto.discountCoupon;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddorUpdateDiscountCouponRequest {

    private String id;

    private String code;

    private Double discountPercentage;

    private Date expiryDate;

    private Boolean isActive;
}
