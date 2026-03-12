package com.josewolf.estoque_api.service;

import com.josewolf.estoque_api.dto.request.ProductRequestDTO;
import com.josewolf.estoque_api.dto.response.ProductResponseDTO;
import com.josewolf.estoque_api.exception.ResourceNotFoundException;
import com.josewolf.estoque_api.mapper.ProductMapper;
import com.josewolf.estoque_api.model.Category;
import com.josewolf.estoque_api.model.Product;
import com.josewolf.estoque_api.repository.CategoryRepository;
import com.josewolf.estoque_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada:" + productRequestDTO.categoryId()));

        Product product = ProductMapper.toProduct(productRequestDTO);

        product.setCategory(category);

        return ProductMapper.toProductResponseDTO(productRepository.save(product));
    }
}
