package com.learning.embarkx.StoreAPI.repositories;

import com.learning.embarkx.StoreAPI.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepositroy extends JpaRepository<CartItem, Long> {
}
