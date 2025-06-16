package com.shopping.mallmate.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    private String review;
    private double rating;
    private String productId;
    private String storeId;

} 