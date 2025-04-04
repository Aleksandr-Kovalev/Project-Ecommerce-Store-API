package com.learning.embarkx.StoreAPI.service;

import com.learning.embarkx.StoreAPI.DTO.CartDTO;
import com.learning.embarkx.StoreAPI.DTO.CartItemDTO;
import jakarta.transaction.Transactional;


import java.util.List;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    CartDTO deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);

    String createOrUpdateCartWithItems(List<CartItemDTO> cartItems);

}
