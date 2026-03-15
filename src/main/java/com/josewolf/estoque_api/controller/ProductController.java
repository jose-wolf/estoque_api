package com.josewolf.estoque_api.controller;

import com.josewolf.estoque_api.dto.request.ProductRequestDTO;
import com.josewolf.estoque_api.dto.response.ProductResponseDTO;
import com.josewolf.estoque_api.service.ProductService;
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
@RequestMapping("/product")
@Tag(name = "Product", description = "API for product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "Create a product", description = "Create a product if category exist")
    @ApiResponse(responseCode = "201", description = "Product created success.")
    @ApiResponse(responseCode = "404", description = "The product was not created, because category not found.")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Find all products", description = "Find all products")
    @ApiResponse(responseCode = "200", description = "Product found success.")
    public ResponseEntity<List<ProductResponseDTO>> getAllProduct(){
        List<ProductResponseDTO> productResponseDTOS = productService.listAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOS);
    }

    @GetMapping("/search/byCategory")
    @Operation(summary = "Find a product for the category", description = "Find a product if category exist")
    @ApiResponse(responseCode = "200", description = "Product found success.")
    @ApiResponse(responseCode = "404", description = "Products not found with this category name.")
    public ResponseEntity<List<ProductResponseDTO>> getAllProductByCategory(@RequestParam String categoryName){
        List<ProductResponseDTO> productResponseDTOS = productService.listAllProductByCategory(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOS);
    }

    @GetMapping("/search")
    @Operation(summary = "Find a product fot the product name", description = "Find an product if exist")
    @ApiResponse(responseCode = "200", description = "Product found success.")
    @ApiResponse(responseCode = "404", description = "Product not found with this name.")
    public ResponseEntity<List<ProductResponseDTO>> getProductByName(@RequestParam String productName){
        List<ProductResponseDTO> productResponseDTO = productService.findByProductName(productName);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Delete a product if exist")
    @ApiResponse(responseCode = "200", description = "Product updated success.")
    @ApiResponse(responseCode = "404", description = "Product not found.")
    @ApiResponse(responseCode = "409", description = "A data conflict ocurred.")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Product", description = "Delete a Product if exist")
    @ApiResponse(responseCode = "204", description = "Product deleted success.")
    @ApiResponse(responseCode = "404", description = "Product not found.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
