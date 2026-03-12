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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        if (productRepository.existsByProductNameAndDescription(productRequestDTO.productName(), productRequestDTO.description())) {
            throw new DataIntegrityViolationException("Já existe um produto com este nome e descrição cadastrados!");
        }

        Category category = categoryRepository.findById(productRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada:" + productRequestDTO.categoryId()));

        Product product = ProductMapper.toProduct(productRequestDTO);

        product.setCategory(category);

        return ProductMapper.toProductResponseDTO(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> listAllProduct(){
        return productRepository.findAll().stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }

    @Transactional
    public List<ProductResponseDTO> listAllProductByCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryNameIgnoreCase(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada:" + categoryName));

        return productRepository.findByCategoryCategoryNameIgnoreCase(category.getCategoryName())
                .stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByProductName(String productName) {
        List<ProductResponseDTO> list = productRepository.findByProductNameContainingIgnoreCase(productName)
                .stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum produto encontrado com o nome: " + productName);
        }

        return list;
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado pelo id: " + id));

        Category category = categoryRepository.findById(productRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada pelo id: " + productRequestDTO.categoryId()));

        product.setProductName(productRequestDTO.productName());
        product.setDescription(productRequestDTO.description());
        product.setPrice(productRequestDTO.price());
        product.setQuantity(productRequestDTO.quantity());
        product.setCategory(category);

        return ProductMapper.toProductResponseDTO(productRepository.save(product));

    }
}
