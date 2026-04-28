package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.exception.ResourceNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toProduct(productRequestDTO);
        Product productSave = productRepository.save(product);
        log.info("Product {} , guardado ", productSave.getName());
        return productMapper.toProductResponseDTO(productSave);
    }

    @Override
    public List<ProductResponseDTO> getAllsProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductResponseDTO).toList();
    }

    @Override
    public ProductResponseDTO getProductId(String id) {
        return productRepository.findById(id).map(productMapper::toProductResponseDTO).orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }
        productMapper.updateProductRequest(productRequestDTO, product.get());
        Product updateProduct = productRepository.save(product.get());
        log.info("Product {} , actualizado ", updateProduct.getName());
        return productMapper.toProductResponseDTO(updateProduct);
    }

    @Override
    public void deleteProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }
        productRepository.deleteById(id);
        log.info("Product {} , eliminado ", product.get().getName());

    }
}
