package com.josewolf.estoque_api.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record CategoryResponseDTO(
        Long id,
        String category,
        List<ProductResponseDTO> products
) {
    public BigDecimal getCategoryValue(){
        return products.stream()
                .map(ProductResponseDTO::getTotalStockValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
