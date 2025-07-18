package com.shopping.mallmate.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterRequest {

    private String name;

    private String categoryId;

    private String brandName;

    private Integer rating;

    private String storeId;

    private String sortBy = "name";

    private String sortOrder = "asc";

    private int pageNumber = 0;

    private int pageSize = 10;

    private Double minPrice = 0.0;

    private Double maxPrice = 1000000.0;

    private Boolean isAvailable = true;

}
