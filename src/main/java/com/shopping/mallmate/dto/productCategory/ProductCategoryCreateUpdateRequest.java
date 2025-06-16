package com.shopping.mallmate.dto.productCategory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCategoryCreateUpdateRequest {

    private String id;

    @NotNull(message = "Name is required")
    private String name;

    private String image;

}
