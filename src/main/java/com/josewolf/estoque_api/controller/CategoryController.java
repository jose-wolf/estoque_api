package com.josewolf.estoque_api.controller;

import com.josewolf.estoque_api.dto.request.CategoryRequestDTO;
import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> categoryResponseDTO = categoryService.findAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<CategoryResponseDTO> getCategoryByCategoryName(@RequestParam String categoryName){
        CategoryResponseDTO categoryResponseDTO = categoryService.findCategoryByCategoryName(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO);
    }

}
