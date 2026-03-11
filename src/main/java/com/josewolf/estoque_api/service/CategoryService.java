package com.josewolf.estoque_api.service;

import com.josewolf.estoque_api.dto.request.CategoryRequestDTO;
import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.exception.ResourceNotFoundException;
import com.josewolf.estoque_api.mapper.CategoryMapper;
import com.josewolf.estoque_api.model.Category;
import com.josewolf.estoque_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAllCategory(){
        return categoryRepository.findAll().stream().map(CategoryMapper::toCategoryResponseDTO).toList();
    }

    @Transactional
    public CategoryResponseDTO findCategoryByCategoryName(String categoryName){
        return categoryRepository.findByCategoryNameIgnoreCase(categoryName)
                .map(CategoryMapper::toCategoryResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Nome de categoria não encontrado: " + categoryName));
    }

    @Transactional
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long categoryId) {
        Category categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada pelo id: " + categoryId));

        categoryEntity.setCategoryName(categoryRequestDTO.categoryName());

        return CategoryMapper.toCategoryResponseDTO(categoryRepository.save(categoryEntity));
    }

}
