package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.review.ReviewRequest;
import com.shopping.mallmate.entity.Review;
import com.shopping.mallmate.service.ReviewService;
import com.shopping.mallmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    // Assuming user is authenticated and their ID is available, e.g., through a token
    // For simplicity, we'll pass userId in the request body for now.
    // In a real application, you would get the user from the security context.

    @PostMapping("/product")
    public ResponseEntity<Review> createProductReview(@RequestParam String userId, @RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.createProductReview(userId, reviewRequest);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @PostMapping("/store")
    public ResponseEntity<Review> createStoreReview(@RequestParam String userId, @RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.createStoreReview(userId, reviewRequest);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable String productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Review>> getStoreReviews(@PathVariable String storeId) {
        List<Review> reviews = reviewService.getReviewsByStoreId(storeId);
        return ResponseEntity.ok(reviews);
    }

     @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable String userId) {
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }
}
