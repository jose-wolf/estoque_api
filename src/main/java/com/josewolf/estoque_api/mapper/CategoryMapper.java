package com.josewolf.estoque_api.mapper;

import com.josewolf.estoque_api.dto.request.CategoryRequestDTO;
import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.model.Category;

import java.util.Collections;

public class CategoryMapper {

    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getProduct() != null ?
                category.getProduct().stream()
                        .map(ProductMapper::toProductResponseDTO)
                        .toList() : Collections.emptyList()
        );
    }

    public static Category toCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setCategoryName(categoryRequestDTO.categoryName());
        return category;
    }

}
