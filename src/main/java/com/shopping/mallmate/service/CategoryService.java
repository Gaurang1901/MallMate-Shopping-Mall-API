package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.category.CategoryCreateUpdateRequest;
import com.shopping.mallmate.entity.Category;
import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.repository.CategoryRepository;
import com.shopping.mallmate.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findCategoryById(String id) {
        Category category = categoryRepository.findCategoryById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        } else {
            return category;
        }
    }

    public Category createCategory(CategoryCreateUpdateRequest category) {
        Category createdCategory = new Category();
        createdCategory.setName(category.getName());
        return categoryRepository.save(createdCategory);
    }

    public Category updateCategory(CategoryCreateUpdateRequest category, String id) {
        Category updatedCategory = findCategoryById(id);
        updatedCategory.setName(category.getName());
        return categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(String id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

}
