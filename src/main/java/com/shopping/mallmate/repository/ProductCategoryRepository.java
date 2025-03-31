package com.shopping.mallmate.repository;


import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    ProductCategory findCategoryById(String id);

    List<Product> findProductsByCategory(ProductCategory category);

}
