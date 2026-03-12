package com.josewolf.estoque_api.controller;

import com.josewolf.estoque_api.dto.request.ProductRequestDTO;
import com.josewolf.estoque_api.dto.response.ProductResponseDTO;
import com.josewolf.estoque_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProduct(){
        List<ProductResponseDTO> productResponseDTOS = productService.listAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOS);
    }

    @GetMapping("/search/byCategory")
    public ResponseEntity<List<ProductResponseDTO>> getAllProductByCategory(@RequestParam String categoryName){
        List<ProductResponseDTO> productResponseDTOS = productService.listAllProductByCategory(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOS);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> getProductByName(@RequestParam String productName){
        List<ProductResponseDTO> productResponseDTO = productService.findByProductName(productName);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

}
