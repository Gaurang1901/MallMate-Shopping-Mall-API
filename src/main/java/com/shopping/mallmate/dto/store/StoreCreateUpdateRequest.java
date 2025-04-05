package com.shopping.mallmate.dto.store;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCreateUpdateRequest {

    private String id;

    @NotNull(message = "Name is required")
    private String name;

    private String description;

    private String phoneNumber;

    private String InstagramLink;

    private String FacebookLink;

    private String TwitterLink;

    private String categoryId;

    private String userId;

    private List<String> images;
}
