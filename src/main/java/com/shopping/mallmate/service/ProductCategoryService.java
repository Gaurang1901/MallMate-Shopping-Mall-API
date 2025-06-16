package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.productCategory.ProductCategoryCreateUpdateRequest;
import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public ProductCategory createCategory(ProductCategoryCreateUpdateRequest category) {
        ProductCategory productcategory = new ProductCategory();
        productcategory.setName(category.getName());
        productcategory.setImage(category.getImage());
        return productCategoryRepository.save(productcategory);
    }

    public ProductCategory updateCategory(ProductCategoryCreateUpdateRequest category, String id) {
        ProductCategory updatedCategory = findCategoryById(id);
        updatedCategory.setName(category.getName());
        updatedCategory.setImage(category.getImage());
        return productCategoryRepository.save(updatedCategory);
    }

    public void deleteCategory(String id) {
        ProductCategory category = findCategoryById(id);
        productCategoryRepository.delete(category);
    }

    public List<ProductCategory> findAllCategories() {
        return productCategoryRepository.findAll();
    }
}
