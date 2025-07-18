package com.shopping.mallmate.dto.store;


import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseForStores {

    private String id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private String brandName;


    public static ProductResponseForStores fromEntity(Product product) {
        return new ProductResponseForStores(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getBrandName()
        );

    }
}
