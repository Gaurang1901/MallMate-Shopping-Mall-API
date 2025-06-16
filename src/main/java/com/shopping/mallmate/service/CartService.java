package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.cart.CartAddOrUpdateRequest;
import com.shopping.mallmate.dto.cart.cartItem.CartItemRequest;
import com.shopping.mallmate.entity.Cart;
import com.shopping.mallmate.entity.CartItems;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.repository.CartItemRepository;
import com.shopping.mallmate.repository.CartRepository;
import com.shopping.mallmate.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    private List<CartItems> buildCartItems(Cart cartEntity, List<CartItemRequest> itemRequests) {
        List<CartItems> cartItems = new ArrayList<>();
        for (CartItemRequest itemRequest : itemRequests) {
            CartItems cartItem = cartItemRepository.findByCartIdAndProductId(cartEntity.getId(), itemRequest.getProductId());
            if (cartItem != null) {
                Product product = cartItem.getProduct();
                if (product.getQuantity() < cartItem.getQuantity() + itemRequest.getQuantity()) {
                    throw new RuntimeException("Not enough stock available for product: " + product.getName());
                }
                cartItem.setQuantity(cartItem.getQuantity() + itemRequest.getQuantity());
                cartItem.setAmount(cartItem.getAmount() + itemRequest.getAmount());
            } else {
                cartItem = new CartItems();
                cartItem.setCart(cartEntity);
                Product product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));
                if (product.getQuantity() < itemRequest.getQuantity()) {
                    throw new RuntimeException("Not enough stock available for product: " + product.getName());
                }
                cartItem.setProduct(product);
                cartItem.setQuantity(itemRequest.getQuantity());
                cartItem.setAmount(itemRequest.getAmount());
            }
            cartItems.add(cartItem);
        }
        return cartItems;
    }


    public Cart addItemsToCart(CartAddOrUpdateRequest cartRequest) {
        User user = userService.findUserById(cartRequest.getUserId());
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());
        cart = cartRepository.save(cart); // Save first to generate ID

        // Validate all products belong to the same store
        String storeId = null;
        for (CartItemRequest item : cartRequest.getCartItemRequests()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProductId()));
            if (storeId == null) {
                storeId = product.getStore().getId();
            } else if (!storeId.equals(product.getStore().getId())) {
                throw new RuntimeException("All products must belong to the same store");
            }
        }

        List<CartItems> cartItems = buildCartItems(cart, cartRequest.getCartItemRequests());
        double totalAmount = cartItems.stream().mapToDouble(CartItems::getAmount).sum();
        cart.setCartItems(cartItems);
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    public Cart updateCart(CartAddOrUpdateRequest cartRequest, String id) {
        Cart cart = findCartByUserId(cartRequest.getUserId());
        if (cart == null) {
            throw new RuntimeException("Cart not found for user id: " + cartRequest.getUserId());
        }

        List<CartItems> cartItems = buildCartItems(cart, cartRequest.getCartItemRequests());
        cart.setCartItems(cartItems);
        double totalAmount = cartItems.stream().mapToDouble(CartItems::getAmount).sum();
        cart.setTotalAmount(totalAmount);
        cart.setUpdatedAt(new Date());
        return cartRepository.save(cart);
    }

    public Cart findCartByUserId(String userId) {
        return (userId == null) ? null : cartRepository.findCartByUserId(userId);
    }

    public void removeCartItemFromCart(String userId, String productId) {
        Cart cart = findCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user id: " + userId);
        }
        CartItems cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        }
    }

    public void emptyCart(String userId) {
        Cart cart = findCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user id: " + userId);
        }
        cartItemRepository.deleteAllByCartId(cart.getId());
        cartRepository.delete(cart);
    }
}
