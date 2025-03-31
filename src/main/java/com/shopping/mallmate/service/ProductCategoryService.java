package com.shopping.mallmate.service;

import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    public ProductCategory findCategoryById(String id) {
        ProductCategory category = productCategoryRepository.findCategoryById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        return category;
    }


    public ProductCategory createCategory(ProductCategory category) {
        ProductCategory productcategory = new ProductCategory();
        productcategory.setName(category.getName());
        productcategory.setProducts(category.getProducts());
        return productCategoryRepository.save(productcategory);
    }

}
