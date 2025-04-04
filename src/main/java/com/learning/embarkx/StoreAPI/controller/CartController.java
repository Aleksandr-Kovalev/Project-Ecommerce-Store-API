package com.learning.embarkx.StoreAPI.controller;

import com.learning.embarkx.StoreAPI.DTO.CartDTO;
import com.learning.embarkx.StoreAPI.DTO.CartItemDTO;
import com.learning.embarkx.StoreAPI.repositories.CartRepository;
import com.learning.embarkx.StoreAPI.service.CartService;
import com.learning.embarkx.StoreAPI.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartRepository cartRepository;
    private final AuthUtil authUtil;

    @PostMapping("/cart/create")
    public ResponseEntity<String> createOrUpdateCart(@RequestBody List<CartItemDTO> cartItems) {

        String response = cartService.createOrUpdateCartWithItems(cartItems);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }






}
