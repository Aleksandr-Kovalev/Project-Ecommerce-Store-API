package com.learning.embarkx.StoreAPI.serviceImpl;

import com.learning.embarkx.StoreAPI.DTO.CartDTO;
import com.learning.embarkx.StoreAPI.DTO.CartItemDTO;
import com.learning.embarkx.StoreAPI.DTO.ProductDTO;
import com.learning.embarkx.StoreAPI.exceptions.APIException;
import com.learning.embarkx.StoreAPI.exceptions.ResourceNotFoundException;
import com.learning.embarkx.StoreAPI.model.Cart;
import com.learning.embarkx.StoreAPI.model.CartItem;
import com.learning.embarkx.StoreAPI.model.Product;
import com.learning.embarkx.StoreAPI.repositories.CartItemRepositroy;
import com.learning.embarkx.StoreAPI.repositories.CartRepository;
import com.learning.embarkx.StoreAPI.repositories.ProductRepository;
import com.learning.embarkx.StoreAPI.service.CartService;
import com.learning.embarkx.StoreAPI.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final AuthUtil authUtil;
    private final CartItemRepositroy cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);

        if(cartItem != null) {
            throw new APIException("Product " + product.getProductName() + " already exists in cart ");
        }

        if(product.getQuantity() == 0) {
            throw new APIException("Product " + product.getProductName() + " is out of stock");
        }

        if(quantity <= product.getQuantity()) {
            throw new APIException("Please, enter a valid quantity. Quantity should be less than or equal to " + quantity);
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();

        // Convert each CartItem into a ProductDTO by mapping its Product and setting the quantity manually
        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        // Collect the stream into a list and set it on the CartDTO
        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {

        List<Cart> carts = cartRepository.findAll();

        if(carts.isEmpty()) {
            throw new APIException("No cart found");
        }

        List<CartDTO> cartDTOs = carts.stream()
                .map(cart -> {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

                    List<ProductDTO> products = cart.getCartItems().stream().map(cartItem -> {
                        ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                        productDTO.setQuantity(cartItem.getQuantity());
                        return productDTO;
                    }).toList();
                    cartDTO.setProducts(products);

                    return cartDTO;
                }).toList();

        return cartDTOs;
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

    @Transactional
    @Override
    public String createOrUpdateCartWithItems(List<CartItemDTO> cartItems) {

        String emailId = authUtil.loggedInEmail();

        Cart existingCart = cartRepository.findCartByEmail(emailId);
        if (existingCart == null) {
            existingCart = new Cart();
            existingCart.setTotalPrice(0.00);
            existingCart.setUser(authUtil.loggedInUser());
            existingCart = cartRepository.save(existingCart);
        } else {
            cartItemRepository.deleteAllByCartId(existingCart.getCartId());
        }

        double totalPrice = 0.00;

        for (CartItemDTO cartItemDTO : cartItems) {
            Long productId = cartItemDTO.getProductId();
            Integer quantity = cartItemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

            totalPrice += product.getSpecialPrice() * quantity;

            CartItem cartItem = new CartItem();
            cartItem.setCart(existingCart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setProductPrice(product.getSpecialPrice());
            cartItem.setDiscount(product.getDiscount());
            cartItemRepository.save(cartItem);
        }

        existingCart.setTotalPrice(totalPrice);
        cartRepository.save(existingCart);

        return "Cart created/updated with new items successfully";
    }

    private Cart createCart(){
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (userCart != null) {
            return userCart;
        }

        Cart newCart = new Cart();
        newCart.setTotalPrice(0.00);
        newCart.setUser(authUtil.loggedInUser());
        newCart = cartRepository.save(newCart);

        return newCart;
    }
}
