package com.josewolf.estoque_api.dto.response;

import com.josewolf.estoque_api.model.Category;

import java.math.BigDecimal;

public record ProductResponseDTO(
    Long id,
    String productName,
    String description,
    BigDecimal price,
    Integer quantity,
    String categoryName
) {

    public BigDecimal getTotalStockValue() {
        if(price == null || quantity == null){
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
