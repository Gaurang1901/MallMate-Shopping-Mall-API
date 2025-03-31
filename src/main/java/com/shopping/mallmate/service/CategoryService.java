package com.shopping.mallmate.service;

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

    public Category createCategory(Category category) {
        Category createdCategory = new Category();
        createdCategory.setName(category.getName());
        createdCategory.setStores(category.getStores());
        return categoryRepository.save(createdCategory);
    }

    public Category updateCategory(Category category) {
        Category updatedCategory = findCategoryById(category.getId());
        updatedCategory.setName(category.getName());
        updatedCategory.setStores(category.getStores());
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
