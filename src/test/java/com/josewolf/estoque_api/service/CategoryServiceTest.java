package com.josewolf.estoque_api.service;

import com.josewolf.estoque_api.dto.request.CategoryRequestDTO;
import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.exception.ResourceNotFoundException;
import com.josewolf.estoque_api.model.Category;
import com.josewolf.estoque_api.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createdCategory_success(){
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Ferramentas");
        Category  category = new Category(1L, "Ferramentas", new ArrayList<>());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.createCategory(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Ferramentas", responseDTO.category());
        verify(categoryRepository, times(1)).save(any());

    }

    @Test
    void createdCategory_throwsDataIntegrityViolationException_WhenHaveDBConflict(){
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Ferramenta");

        when(categoryRepository.existsByCategoryNameIgnoreCase("Ferramenta")).thenReturn(true);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                () -> categoryService.createCategory(requestDTO));

        assertEquals("Categoria já existe: Ferramenta", exception.getMessage());

        verify(categoryRepository, never()).save(any());
    }

    //List
    @Test
    void listAllCategory_success(){
        Category  category = new Category(1L, "Ferramentas", new ArrayList<>());
        Category  category2 = new Category(1L, "Bebidas", new ArrayList<>());

        when(categoryRepository.findAll()).thenReturn(List.of(category, category2));

        List<CategoryResponseDTO> responseDTOS = categoryService.findAllCategory();

        assertNotNull(responseDTOS);
        assertEquals(2, responseDTOS.size());
        assertEquals("Ferramentas", responseDTOS.get(0).category());
        assertEquals("Bebidas", responseDTOS.get(1).category());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findCategoryByCategoryName_success(){
        String categoryName = "Ferramentas";
        Category  category = new Category(1L, "Ferramentas", new ArrayList<>());

        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.of(category));

        CategoryResponseDTO responseDTO = categoryService.findCategoryByCategoryName(categoryName);

        assertNotNull(responseDTO);
        assertEquals("Ferramentas", responseDTO.category());

        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase(categoryName);
    }

    @Test
    void findCategoryByCategoryName_throwsException_WhenCategoryNotFound(){
        String categoryName = "Bebidas";

        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () ->  categoryService.findCategoryByCategoryName(categoryName));

        assertEquals("Nome de categoria não encontrado: Bebidas", exception.getMessage());

        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase(categoryName);
    }

    //
    @Test
    void updateCategoryById_success(){
        Long categoryId = 1L;
        String categoryName = "Ferramentas Eletronicas";
        CategoryRequestDTO requestDTO = new CategoryRequestDTO(categoryName);
        Category  category = new Category(categoryId, "Ferramentas", new ArrayList<>());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByCategoryNameIgnoreCase("Ferramentas Eletronicas")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.updateCategory(requestDTO, categoryId);

        assertNotNull(responseDTO);
        assertEquals(categoryName, responseDTO.category());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).existsByCategoryNameIgnoreCase("Ferramentas Eletronicas");
        verify(categoryRepository, times(1)).save(any());

    }

    @Test
    void updateCategoryById_ThrowsException_WhenIdNotFound(){
        Long categoryId = 999L;
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Bebidas");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.updateCategory(requestDTO, categoryId));

        assertEquals("Categoria não encontrada pelo id: 999" ,exception.getMessage());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategoryById_ThrowsException_WhenDataBaseConflict(){
        Long categoryId = 1L;
        String categoryName = "Ferramentas Eletronicas";
        CategoryRequestDTO requestDTO = new CategoryRequestDTO(categoryName);
        Category  category = new Category(categoryId, "Ferramentas+", new ArrayList<>());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByCategoryNameIgnoreCase(categoryName)).thenReturn(true);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                () -> categoryService.updateCategory(requestDTO, categoryId));

        assertEquals("Categoria já existe: Ferramentas Eletronicas", exception.getMessage());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).existsByCategoryNameIgnoreCase("Ferramentas Eletronicas");
        verify(categoryRepository, never()).save(any());
    }

    //delete

    @Test
    void deleteCategoryById_success(){
        Long categoryId = 1L;

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteById_ThrowsException_WhenIdNotFound(){
        Long categoryId = 999L;

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.deleteCategory(categoryId));

        assertEquals("Categoria não encontrada pelo id: 999", exception.getMessage());

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }
}
