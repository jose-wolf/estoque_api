package com.josewolf.estoque_api.mapper;

import com.josewolf.estoque_api.dto.request.CategoryRequestDTO;
import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.model.Category;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryMapperTest {

    @Test
    void mapToCategoryResponseDTOTest() {
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Bebidas");
        category.setProduct(new ArrayList<>());

        CategoryResponseDTO responseDTO = CategoryMapper.toCategoryResponseDTO(category);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Bebidas", responseDTO.category());
        assertNotNull(responseDTO.products());
    }

    @Test
    void mapToCategory(){
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Bebidas");

        Category category = CategoryMapper.toCategory(requestDTO);
        assertNotNull(category);
        assertEquals("Bebidas", category.getCategoryName());
    }

}
