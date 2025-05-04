package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.discountCoupon.AddorUpdateDiscountCouponRequest;
import com.shopping.mallmate.dto.discountCoupon.DiscountCouponResponse;
import com.shopping.mallmate.entity.DiscountCoupon;
import com.shopping.mallmate.service.DiscountCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/")
@RestController
public class AdminDiscountCouponController {

    @Autowired
    private DiscountCouponService discountCouponService;

    @PostMapping("discount-coupon/{userId}")
    public ResponseEntity<ApiResponse> createDiscountCoupon(@RequestBody AddorUpdateDiscountCouponRequest request, @PathVariable String userId) {

        DiscountCoupon dc = new DiscountCoupon();

        dc = discountCouponService.createDiscountCoupon(request, userId);

        ApiResponse response = new ApiResponse("Discount coupon created successfully", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("discount-coupon/{userId}/{discountCouponId}")
    public ResponseEntity<ApiResponse> updateDiscountCoupon(@RequestBody AddorUpdateDiscountCouponRequest request, @PathVariable String userId, @PathVariable String discountCouponId)  {

        DiscountCoupon dc = new DiscountCoupon();

        dc = discountCouponService.updateDiscountCoupon(request, userId, discountCouponId);

        ApiResponse response = new ApiResponse("Discount coupon updated successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("discount-coupons/{userId}")
    public ResponseEntity<List<DiscountCouponResponse>> getAllDiscountCoupons(@PathVariable String userId) {
        List<DiscountCoupon> discountCoupons = discountCouponService.getAllDiscountCoupon(userId);
        return ResponseEntity.ok(discountCoupons.stream().map(DiscountCouponResponse::fromEntity).toList());
    }

    @DeleteMapping("discount-coupon/{discountCouponId}")
    public ResponseEntity<ApiResponse> deleteDiscountCoupon(@PathVariable String discountCouponId) {
        discountCouponService.deleteDiscountCoupon(discountCouponId);
        ApiResponse response = new ApiResponse("Discount coupon deleted successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("discount-coupon/{discountCouponId}")
    public ResponseEntity<DiscountCouponResponse> getDiscountCouponById(@PathVariable String discountCouponId) {
        DiscountCoupon dc = discountCouponService.findDiscountCouponById(discountCouponId);
        if (dc == null) {
            return ResponseEntity.notFound().build();
        }
        DiscountCouponResponse response = DiscountCouponResponse.fromEntity(dc);
        return ResponseEntity.ok(response);
    }


}
