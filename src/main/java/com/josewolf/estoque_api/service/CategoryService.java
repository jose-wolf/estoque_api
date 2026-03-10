package com.josewolf.estoque_api.service;

import com.josewolf.estoque_api.dto.request.CategoryRequestDTO;
import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.mapper.CategoryMapper;
import com.josewolf.estoque_api.model.Category;
import com.josewolf.estoque_api.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {

        Category category = CategoryMapper.toCategory(categoryRequestDTO);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toCategoryResponseDTO(savedCategory);

    }

}
