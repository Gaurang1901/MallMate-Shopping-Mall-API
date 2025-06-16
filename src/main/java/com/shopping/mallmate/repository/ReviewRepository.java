package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByProductId(String productId);

    List<Review> findByStoreId(String storeId);

    List<Review> findByUserId(String userId);
} 