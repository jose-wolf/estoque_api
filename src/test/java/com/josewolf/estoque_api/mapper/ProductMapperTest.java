package com.josewolf.estoque_api.mapper;

import com.josewolf.estoque_api.dto.request.ProductRequestDTO;
import com.josewolf.estoque_api.dto.response.ProductResponseDTO;
import com.josewolf.estoque_api.model.Category;
import com.josewolf.estoque_api.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductMapperTest {

    @Test
    void mapToProductResponseDTOTest() {
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Bebidas");
        category.setProduct(new ArrayList<>());

        Product product = new Product();
        product.setId(1L);
        product.setProductName("Monster");
        product.setDescription("Energy");
        product.setPrice(BigDecimal.valueOf(10));
        product.setQuantity(5);
        product.setCategory(category);

        ProductResponseDTO responseDTO = ProductMapper.toProductResponseDTO(product);

        assertNotNull(responseDTO);
        assertEquals(product.getId(), responseDTO.id());
        assertEquals(product.getProductName(), responseDTO.productName());
        assertEquals(product.getDescription(), responseDTO.description());
        assertEquals(product.getPrice(), responseDTO.price());
        assertEquals(product.getQuantity(), responseDTO.quantity());
        assertEquals(category.getCategoryName(), responseDTO.categoryName());
    }

    @Test
    void mapToProduct(){
        ProductRequestDTO productRequestDTO = new ProductRequestDTO("Monster","Energy", BigDecimal.valueOf(10)
        ,5, 1L);

        Product product = ProductMapper.toProduct(productRequestDTO);

        assertNotNull(product);
        assertEquals("Monster", product.getProductName());
        assertEquals("Energy", product.getDescription());
        assertEquals(BigDecimal.valueOf(10), product.getPrice());
        assertEquals(5, product.getQuantity());
    }
}
