package com.shopping.mallmate.repository;


import com.shopping.mallmate.entity.Category;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findCategoryById(String id);

    Category findCategoryByStore(Store store);

}
