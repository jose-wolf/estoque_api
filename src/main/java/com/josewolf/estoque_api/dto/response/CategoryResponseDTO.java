package com.josewolf.estoque_api.dto.response;

import java.util.List;

public record CategoryResponseDTO(
        Long id,
        String category,
        List<ProductResponseDTO> products
) {
}
