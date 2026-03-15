package com.josewolf.estoque_api.controller;

import com.josewolf.estoque_api.dto.request.CategoryRequestDTO;
import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "API for category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @Operation(summary =  "Create a category", description = "Add a new category.")
    @ApiResponse(responseCode = "201", description = "Category created success")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO);
    }

    @GetMapping
    @Operation(summary =  "List all category", description = "List all category.")
    @ApiResponse(responseCode = "200", description = "Categorys listed success")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> categoryResponseDTO = categoryService.findAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO);
    }

    @GetMapping("/search")
    @Operation(summary =  "Find a by category", description = "List a category by your name.")
    @ApiResponse(responseCode = "200", description = "Category found success.")
    @ApiResponse(responseCode = "404", description = "Category with this name not exist.")
    public ResponseEntity<CategoryResponseDTO> getCategoryByCategoryName(@RequestParam String categoryName){
        CategoryResponseDTO categoryResponseDTO = categoryService.findCategoryByCategoryName(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update a category")
    @ApiResponse(responseCode = "200", description = "Category updated success.")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Category not found.")
    public ResponseEntity<CategoryResponseDTO>  updateCategory(@PathVariable Long id,
                                                               @RequestBody @Valid CategoryRequestDTO categoryRequestDTO){

        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(categoryRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Delete a category")
    @ApiResponse(responseCode = "204", description = "Category deleted success.")
    @ApiResponse(responseCode = "404", description = "Category not found.")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
