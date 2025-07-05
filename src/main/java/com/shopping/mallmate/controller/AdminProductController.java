package com.shopping.mallmate.controller;


import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.product.ProductCreateUpdateRequest;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create a new product", description = "Creates a new product in the system.",
        requestBody = @RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = ProductCreateUpdateRequest.class),
                examples = @ExampleObject(value = "{\"name\":\"Sample Product\",\"description\":\"A great product\",\"price\":100.0,\"categoryId\":\"cat123\"}")
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Product created successfully",
                content = @Content(schema = @Schema(implementation = com.shopping.mallmate.dto.ApiResponse.class)))
        }
    )
    @PostMapping("/product")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductCreateUpdateRequest product) {
        Product createdProduct = productService.createProduct(product);
        ApiResponse response = new ApiResponse();
        response.setMessage("Product created successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a product", description = "Updates an existing product.",
        requestBody = @RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = ProductCreateUpdateRequest.class),
                examples = @ExampleObject(value = "{\"name\":\"Updated Product\",\"description\":\"Updated description\",\"price\":120.0,\"categoryId\":\"cat123\"}")
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Product updated successfully",
                content = @Content(schema = @Schema(implementation = com.shopping.mallmate.dto.ApiResponse.class)))
        }
    )
    @PutMapping("/product/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductCreateUpdateRequest product, @PathVariable String id) {
        Product createdProduct = productService.updateProduct(id, product);
        ApiResponse response = new ApiResponse();
        response.setMessage("Product updated successfully");
        response.setStatus(HttpStatus.ACCEPTED.value());
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by ID.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product deleted successfully",
                content = @Content(schema = @Schema(implementation = com.shopping.mallmate.dto.ApiResponse.class)))
        }
    )
    @DeleteMapping("/product/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Product deleted successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
