package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.product.ProductGetResponse;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductGetResponse> findProductById(@PathVariable String id) {
        Product product = productService.findProductById(id);
        ProductGetResponse response = new ProductGetResponse();
        response = ProductGetResponse.fromEntity(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductGetResponse>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        List<ProductGetResponse> response = products.stream().map(ProductGetResponse::fromEntity).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductGetResponse>> searchProduct(@Param("keyword") String keyword) {
        List<Product> products = productService.searchProduct(keyword);
        List<ProductGetResponse> response = products.stream().map(ProductGetResponse::fromEntity).toList();
        return ResponseEntity.ok(response);
    }
}
