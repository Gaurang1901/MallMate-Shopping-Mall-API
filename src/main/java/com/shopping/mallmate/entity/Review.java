package com.shopping.mallmate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String review;

    private double rating;

    @ManyToOne
    @JoinColumn(name = "product_id") // Explicitly define column name
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id") // Add relationship to Store
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id") // Explicitly define column name
    private User user;

    private Date addedAt;

    private Date updatedAt;

}
