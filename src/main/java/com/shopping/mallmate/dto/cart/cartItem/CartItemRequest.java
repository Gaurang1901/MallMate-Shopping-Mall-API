package com.shopping.mallmate.dto.cart.cartItem;


import com.shopping.mallmate.entity.Cart;
import com.shopping.mallmate.entity.CartItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

    private String productId;

    private int quantity;

    private Double amount;

    public static CartItemRequest fromEntity(CartItems cartItems) {
        return new CartItemRequest(cartItems.getProduct().getId(), cartItems.getQuantity(), cartItems.getAmount());
    }
}
