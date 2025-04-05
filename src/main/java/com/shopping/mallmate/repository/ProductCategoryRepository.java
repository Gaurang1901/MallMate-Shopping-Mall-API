package com.shopping.mallmate.repository;


import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    ProductCategory findCategoryById(String id);

//    List<Product> findProductsByCategory(ProductCategory category);

}
