package com.shopping.mallmate.dto.cart;

import com.shopping.mallmate.dto.cart.cartItem.CartItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartAddOrUpdateRequest {

    private String id;

    private String userId;

    private List<CartItemRequest> cartItemRequests;

    private Double totalAmount;
}
