package com.shopping.mallmate.controller;


import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.category.CategoryCreateUpdateRequest;
import com.shopping.mallmate.entity.Category;
import com.shopping.mallmate.repository.CategoryRepository;
import com.shopping.mallmate.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("category")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryCreateUpdateRequest category) {
        Category createdCategory = categoryService.createCategory(category);
        ApiResponse response = new ApiResponse();
        response.setMessage("Category created successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("category/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryCreateUpdateRequest category, @PathVariable String id) {
        Category updatedCategory = categoryService.findCategoryById(id);
        updatedCategory = categoryService.updateCategory(category, id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Category updated successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String id) {
        Category updatedCategory = categoryService.findCategoryById(id);
        categoryService.deleteCategory(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Category deleted successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        Category category = categoryService.findCategoryById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
