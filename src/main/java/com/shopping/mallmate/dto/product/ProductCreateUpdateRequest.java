package com.shopping.mallmate.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request model for creating or updating a product", example = "{\"name\":\"Sample Product\",\"description\":\"A great product\",\"price\":100.0,\"categoryId\":\"cat123\"}")
public class ProductCreateUpdateRequest {

    private String id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 0, message = "Price must be non-negative")
    private double price;

    private List<String> images;

    @Min(value = 0, message = "Quantity must be non-negative")
    private int quantity;

    @NotNull(message = "Product category ID is required")
    private String categoryId;

    @NotNull(message = "Store ID is required")
    private String storeId;
}
