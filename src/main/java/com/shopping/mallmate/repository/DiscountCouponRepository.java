package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, String> {

    DiscountCoupon findByCode(String code);

    DiscountCoupon findDiscountCouponById(String id);
}
