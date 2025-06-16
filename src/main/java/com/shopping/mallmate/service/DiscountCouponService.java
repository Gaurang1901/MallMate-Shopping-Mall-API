package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.discountCoupon.AddorUpdateDiscountCouponRequest;
import com.shopping.mallmate.entity.DiscountCoupon;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.entity.enums.USER_ROLE;
import com.shopping.mallmate.repository.DiscountCouponRepository;
import com.shopping.mallmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiscountCouponService {

    @Autowired
    private DiscountCouponRepository discountCouponRepository;

    @Autowired
    private UserRepository userRepository;

    public DiscountCoupon createDiscountCoupon(AddorUpdateDiscountCouponRequest request, String userId) {

        User user = userRepository.findUserById(userId);

        DiscountCoupon discountCoupon = new DiscountCoupon();
        if (user != null) {
            if (user.getRole().equals(USER_ROLE.ADMIN) || user.getRole().equals(USER_ROLE.OWNER)) {
                discountCoupon.setCode(request.getCode());
                discountCoupon.setDiscountPercentage(request.getDiscountPercentage());
                discountCoupon.setExpiryDate(request.getExpiryDate());
                discountCoupon.setIsActive(request.getIsActive());
                discountCoupon.setCreatedBy(user.getRole().toString());
                discountCoupon.setUpdatedBy(user.getRole().toString());
                discountCoupon.setCreateDate(new Date());
                discountCoupon.setUpdateDate(new Date());
                return discountCouponRepository.save(discountCoupon);
            } else {
                throw new RuntimeException("You are not authorized");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public DiscountCoupon updateDiscountCoupon(AddorUpdateDiscountCouponRequest request, String userId, String discountCouponId) {

        User user = userRepository.findUserById(userId);

        DiscountCoupon discountCoupon = discountCouponRepository.findDiscountCouponById(discountCouponId);

        if (user != null) {
            if (user.getRole().equals(USER_ROLE.ADMIN) || user.getRole().equals(USER_ROLE.OWNER)) {
                discountCoupon.setCode(request.getCode());
                discountCoupon.setDiscountPercentage(request.getDiscountPercentage());
                discountCoupon.setExpiryDate(request.getExpiryDate());
                discountCoupon.setIsActive(request.getIsActive());
                discountCoupon.setUpdatedBy(user.getRole().toString());
                discountCoupon.setUpdateDate(new Date());
                return discountCouponRepository.save(discountCoupon);
            } else {
                throw new RuntimeException("You are not authorized");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public List<DiscountCoupon> getAllDiscountCoupon(String UserId) {
        User user = userRepository.findUserById(UserId);
        if (user.getRole().equals(USER_ROLE.ADMIN) || user.getRole().equals(USER_ROLE.OWNER)) {
            return discountCouponRepository.findAll();
        }
        throw new RuntimeException("You are not authorized to view discount coupons");
    }

    public void deleteDiscountCoupon(String discountCouponId) {
        DiscountCoupon discountCoupon = discountCouponRepository.findDiscountCouponById(discountCouponId);
        discountCouponRepository.delete(discountCoupon);
    }

    public DiscountCoupon findDiscountCouponById(String discountCouponId) {
        return discountCouponRepository.findDiscountCouponById(discountCouponId);
    }

    public boolean validateDiscountCoupon(String id) {
        DiscountCoupon discountCoupon = discountCouponRepository.findDiscountCouponById(id);
        if (discountCoupon == null) {
            throw new RuntimeException("Discount coupon not found");
        }
        if (!discountCoupon.getIsActive()) {
            return false;
        }
        return !discountCoupon.getExpiryDate().before(new java.util.Date());
    }

}
