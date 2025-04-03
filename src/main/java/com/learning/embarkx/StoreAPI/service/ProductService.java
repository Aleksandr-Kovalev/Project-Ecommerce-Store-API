package com.learning.embarkx.StoreAPI.service;

import com.learning.embarkx.StoreAPI.DTO.ProductDTO;
import com.learning.embarkx.StoreAPI.Response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
    ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword, String category);
    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO deleteProduct(Long productId);
    ProductResponse updateProductImage(Long productId, MultipartFile image) throws IOException;

}
