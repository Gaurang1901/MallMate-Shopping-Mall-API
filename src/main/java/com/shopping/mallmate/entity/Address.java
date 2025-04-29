package com.shopping.mallmate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String AddressLine1;

    private String AddressLine2;

    private String city;

    private String state;

    private String country;

    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
