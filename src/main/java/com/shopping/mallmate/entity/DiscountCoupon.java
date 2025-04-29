package com.shopping.mallmate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    private Double discountPercentage;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    private Boolean isActive;

    @PrePersist
    @PreUpdate
    private void updateActiveStatus() {
        if (expiryDate != null) {
            isActive = !expiryDate.before(new Date());
        } else {
            isActive = false;
        }
    }
}
