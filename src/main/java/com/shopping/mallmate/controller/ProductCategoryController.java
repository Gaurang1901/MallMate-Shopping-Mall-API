package com.shopping.mallmate.controller;


import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.productCategory.ProductCategoryCreateUpdateRequest;
import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.repository.ProductCategoryRepository;
import com.shopping.mallmate.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @PostMapping("product-category")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody ProductCategoryCreateUpdateRequest category) {
        ProductCategory createdCategory = categoryService.createCategory(category);
        ApiResponse response = new ApiResponse();
        response.setMessage("Category created successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("product-category/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody ProductCategoryCreateUpdateRequest category, @PathVariable String id) {
        ProductCategory updatedCategory = categoryService.findCategoryById(id);
        updatedCategory = categoryService.updateCategory(category, id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Category updated successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("product-category/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String id) {
        ProductCategory updatedCategory = categoryService.findCategoryById(id);
        categoryService.deleteCategory(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Category deleted successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("product-categories")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("product-category/{id}")
    public ResponseEntity<ProductCategory> getCategoryById(@PathVariable String id) {
        ProductCategory category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
