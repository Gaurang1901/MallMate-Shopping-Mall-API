package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.review.ReviewRequest;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.entity.Review;
import com.shopping.mallmate.entity.Store;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.repository.ProductRepository;
import com.shopping.mallmate.repository.ReviewRepository;
import com.shopping.mallmate.repository.StoreRepository;
import com.shopping.mallmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    public Review createProductReview(String userId, ReviewRequest reviewRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(reviewRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(reviewRequest.getReview());
        review.setRating(reviewRequest.getRating());
        review.setAddedAt(new Date());
        review.setUpdatedAt(new Date());

        return reviewRepository.save(review);
    }

    public Review createStoreReview(String userId, ReviewRequest reviewRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Store store = storeRepository.findById(reviewRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Review review = new Review();
        review.setUser(user);
        review.setStore(store);
        review.setReview(reviewRequest.getReview());
        review.setRating(reviewRequest.getRating());
        review.setAddedAt(new Date());
        review.setUpdatedAt(new Date());

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getReviewsByStoreId(String storeId) {
        return reviewRepository.findByStoreId(storeId);
    }

    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }
}
