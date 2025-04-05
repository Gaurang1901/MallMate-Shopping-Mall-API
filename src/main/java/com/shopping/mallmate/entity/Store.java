package com.shopping.mallmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    private String description;

    @ElementCollection
    @Column(length = 10000)
    private List<String> image;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String phoneNumber;

    private String InstagramLink;

    private String FacebookLink;

    private String TwitterLink;
}
