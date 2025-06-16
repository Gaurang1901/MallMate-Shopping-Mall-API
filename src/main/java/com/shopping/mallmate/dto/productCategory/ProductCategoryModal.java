package com.shopping.mallmate.dto.productCategory;


import com.shopping.mallmate.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryModal {

    private String id;

    private String name;

    public static ProductCategoryModal fromEntity(ProductCategory category) {
        return new ProductCategoryModal(category.getId(), category.getName());
    }
}
