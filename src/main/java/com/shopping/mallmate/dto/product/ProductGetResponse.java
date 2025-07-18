package com.shopping.mallmate.dto.product;


import com.shopping.mallmate.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetResponse {

    private String id;

    private String name;

    private String description;

    private double price;

    private List<String> images;

    private int quantity;

    private String storeId;

    private String storeName;

    private String categoryId;

    private String categoryName;

    private String brandName;

    public static ProductGetResponse fromEntity(Product product) {
        return new ProductGetResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImages(),
                product.getQuantity(),
                product.getStore() != null ? product.getStore().getId() : null,
                product.getStore() != null ? product.getStore().getName() : null,
                product.getProductCategory() != null ? product.getProductCategory().getId() : null,
                product.getProductCategory() != null ? product.getProductCategory().getName() : null,
                product.getBrandName()
        );
    }


}
