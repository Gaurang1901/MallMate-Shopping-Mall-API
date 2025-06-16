//package com.shopping.mallmate.dto.review;
//
//import com.shopping.mallmate.entity.Review;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ReviewGetResponse {
//
//    private String id;
//
//    private String productId;
//
//    private String userId;
//
//    private String review;
//
//    private double rating;
//
//    public static ReviewGetResponse fromEntity(Review review) {
//        return new ReviewGetResponse(
//                review.getId(),
//                review.getProduct().getId(),
//                review.getUser().getId(),
//                review.getReview(),
//                review.getRating()
//        );
//    }
//
//}
