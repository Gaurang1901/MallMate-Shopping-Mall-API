package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.cart.CartAddOrUpdateRequest;
import com.shopping.mallmate.dto.cart.GetAllCartResponse;
import com.shopping.mallmate.entity.Cart;
import com.shopping.mallmate.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add items to cart
    @PostMapping("/")
    public ResponseEntity<ApiResponse> addItemsToCart(@RequestBody CartAddOrUpdateRequest cartRequest) {
        Cart cart = cartService.addItemsToCart(cartRequest);
        ApiResponse apiResponse = new ApiResponse("Item added to cart successfully", HttpStatus.CREATED.value());
        return ResponseEntity.ok(apiResponse);
    }

    // Update cart
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCart(@RequestBody CartAddOrUpdateRequest cartRequest, @PathVariable String id) {
        Cart cart = cartService.updateCart(cartRequest, id);
        ApiResponse apiResponse = new ApiResponse("Cart Updated Successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    // Get cart by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<GetAllCartResponse> getCartByUserId(@PathVariable String userId) {
        Cart cart = cartService.findCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        GetAllCartResponse cartResponse = GetAllCartResponse.fromEntity(cart);
        return ResponseEntity.ok(cartResponse);
    }

    // Remove a specific item from cart
    @DeleteMapping("/{userId}/item/{productId}")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable String userId, @PathVariable String productId) {
        cartService.removeCartItemFromCart(userId, productId);
        ApiResponse apiResponse = new ApiResponse("Item removed from cart successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    // Empty the entire cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> emptyCart(@PathVariable String userId) {
        cartService.emptyCart(userId);
        ApiResponse apiResponse = new ApiResponse("Item removed from cart successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
}

