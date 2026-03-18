package com.josewolf.estoque_api.service;

import com.josewolf.estoque_api.dto.request.ProductRequestDTO;
import com.josewolf.estoque_api.dto.response.ProductResponseDTO;
import com.josewolf.estoque_api.exception.ResourceNotFoundException;
import com.josewolf.estoque_api.model.Category;
import com.josewolf.estoque_api.model.Product;
import com.josewolf.estoque_api.repository.CategoryRepository;
import com.josewolf.estoque_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Category category;
    private Product product;
    private ProductRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Bebidas", new ArrayList<>());
        product = new Product(1L, "Monster", "Energy", new BigDecimal("10.0"), 10, category);
        requestDTO = new ProductRequestDTO("Monster", "Energy", new BigDecimal("10.0"), 10, 1L);
    }

    @Test
    @DisplayName("Should create product successfully")
    void createProduct_Success() {
        when(productRepository.existsByProductNameAndDescription("Monster", "Energy")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO response = productService.createProduct(requestDTO);

        assertNotNull(response);
        assertEquals("Monster", response.productName());
        verify(productRepository, times(1)).existsByProductNameAndDescription("Monster", "Energy");
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when product already exists")
    void createProduct_ThrowsException_WhenDuplicate() {
        when(productRepository.existsByProductNameAndDescription("Monster", "Energy")).thenReturn(true);

        DataIntegrityViolationException ex =  assertThrows(DataIntegrityViolationException.class,
                () -> productService.createProduct(requestDTO));

        assertEquals("Já existe um produto com este nome e descrição cadastrados!",ex.getMessage());

        verify(productRepository, never()).save(any());
    }

    @Test
    void createProduct_ThrowsException_WhenCategoryIdNotFound() {
        when(productRepository.existsByProductNameAndDescription("Monster", "Energy")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () ->  productService.createProduct(requestDTO));

        assertEquals("Categoria não encontrada:1", ex.getMessage());
        verify(productRepository, times(1)).existsByProductNameAndDescription("Monster", "Energy");
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should list all products")
    void listAllProduct_Success() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.listAllProduct();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals( "Monster", result.get(0).productName());
        assertEquals("Energy", result.get(0).description());
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Should list products by category name")
    void listAllProductByCategory_Success() {
        when(categoryRepository.findByCategoryNameIgnoreCase("Bebidas")).thenReturn(Optional.of(category));
        when(productRepository.findByCategoryCategoryNameIgnoreCase("Bebidas")).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.listAllProductByCategory("Bebidas");

        assertFalse(result.isEmpty());
        assertEquals("Bebidas", result.get(0).categoryName());
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase("Bebidas");
        verify(productRepository, times(1)).findByCategoryCategoryNameIgnoreCase("Bebidas");
    }

    @Test
    @DisplayName("Should find product by name containing string")
    void findByProductName_Success() {
        when(productRepository.findByProductNameContainingIgnoreCase("Mon")).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.findByProductName("Mon");

        assertFalse(result.isEmpty());
        assertEquals("Monster", result.get(0).productName());
        verify(productRepository).findByProductNameContainingIgnoreCase("Mon");
    }

    @Test
    @DisplayName("Should throw exception when no product found by name")
    void findByProductName_ThrowsException_WhenEmpty() {
        when(productRepository.findByProductNameContainingIgnoreCase("Inexistente")).thenReturn(List.of());

        ResourceNotFoundException ex =  assertThrows(ResourceNotFoundException.class,
                () -> productService.findByProductName("Inexistente"));

        assertEquals("Nenhum produto encontrado com o nome: Inexistente", ex.getMessage());
        verify(productRepository, times(1)).findByProductNameContainingIgnoreCase("Inexistente");
    }

    @Test
    @DisplayName("Should update product successfully")
    void updateProduct_Success() {
        Long id = 1L;
        ProductRequestDTO newRequest = new ProductRequestDTO("Monster Ultra", "Energy Zero", new BigDecimal("12.0"), 5, 1L);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.updateProduct(id, newRequest);

        assertNotNull(result);
        verify(productRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository).save(any());
    }

    @Test
    void updateProduct_ThrowsException_WhenProductIdNotFound() {
        Long id = 999L;
        ProductRequestDTO newRequest = new ProductRequestDTO("Monster Ultra", "Energy Zero", new BigDecimal("12.0"), 5, 1L);

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =  assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(id, newRequest));

        assertEquals("Produto não encontrado pelo id: 999", ex.getMessage());
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, never()).save(any());

    }

    @Test
    void updateProduct_ThrowsException_WhenCategoryIdNotFound() {
        Long id = 1L;
        ProductRequestDTO newRequest = new ProductRequestDTO("Monster Ultra", "Energy Zero", new BigDecimal("12.0"), 5, 1L);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =  assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(id, newRequest));

        assertEquals("Categoria não encontrada pelo id: 1", ex.getMessage());
        verify(productRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete product successfully")
    void deleteProduct_Success() {
        Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(true);

        productService.deleteProduct(id);

        verify(productRepository,times(1)).existsById(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent product")
    void deleteProduct_ThrowsException_WhenNotFound() {
        Long id = 999L;
        when(productRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException ex =  assertThrows(ResourceNotFoundException.class,
                () -> productService.deleteProduct(id));

        assertEquals("Produto não encontrado pelo id: 999", ex.getMessage());

        verify(productRepository, times(1)).existsById(id);
        verify(productRepository, never()).deleteById(any());
    }

}
