package com.learning.embarkx.StoreAPI.serviceImpl;

import com.learning.embarkx.StoreAPI.DTO.CartDTO;
import com.learning.embarkx.StoreAPI.DTO.CartItemDTO;
import com.learning.embarkx.StoreAPI.repositories.CartItemRepositroy;
import com.learning.embarkx.StoreAPI.repositories.CartRepository;
import com.learning.embarkx.StoreAPI.repositories.ProductRepository;
import com.learning.embarkx.StoreAPI.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    //To add  private final AuthUtil authUtil;
    private final CartItemRepositroy cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        return null;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return List.of();
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
        return null;
    }

    @Override
    public CartDTO deleteProductFromCart(Long cartId, Long productId) {
        return null;
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {

    }

    @Override
    public String createOrUpdateCartWithItems(List<CartItemDTO> cartItems) {
        return "";
    }
}
