package com.learning.embarkx.StoreAPI.repositories;

import com.learning.embarkx.StoreAPI.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
