package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct (ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> getAllsProducts();
    ProductResponseDTO getProductId (String id);
    ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO);
    void deleteProduct(String id);
}
