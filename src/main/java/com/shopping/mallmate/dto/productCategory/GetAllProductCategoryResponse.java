package com.shopping.mallmate.dto.productCategory;

import com.shopping.mallmate.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductCategoryResponse {

    private List<ProductCategoryModal> data;

    public static GetAllProductCategoryResponse fromEntity(List<ProductCategory> productCategories) {
        List<ProductCategoryModal> models = productCategories.stream()
                .map(ProductCategoryModal::fromEntity)
                .collect(Collectors.toList());
        return new GetAllProductCategoryResponse(models);
    }
}
