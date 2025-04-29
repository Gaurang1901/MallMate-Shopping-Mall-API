package com.shopping.mallmate.dto.cart;

import com.shopping.mallmate.dto.cart.cartItem.CartItemRequest;
import com.shopping.mallmate.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCartResponse {

    private String cartId;

    private String userId;

    private List<CartItemRequest> cartItems;

    private Double totalAmount;


    public static GetAllCartResponse fromEntity(Cart cart) {
        return new GetAllCartResponse(
                cart.getId(),
                cart.getUser().getId(),
                cart.getCartItems().stream().map(CartItemRequest::fromEntity).toList(),
                cart.getTotalAmount()
        );
    }

}
