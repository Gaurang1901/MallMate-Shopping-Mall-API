package com.shopping.mallmate.controller;


import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.product.ProductCreateUpdateRequest;
import com.shopping.mallmate.dto.product.ProductGetResponse;
import com.shopping.mallmate.dto.productCategory.ProductCategoryCreateUpdateRequest;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductCreateUpdateRequest product) {
        Product createdProduct = productService.createProduct(product);
        ApiResponse response = new ApiResponse();
        response.setMessage("Product created successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductCreateUpdateRequest product, @PathVariable String id) {
        Product createdProduct = productService.updateProduct(id, product);
        ApiResponse response = new ApiResponse();
        response.setMessage("Product updated successfully");
        response.setStatus(HttpStatus.ACCEPTED.value());
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Product deleted successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
