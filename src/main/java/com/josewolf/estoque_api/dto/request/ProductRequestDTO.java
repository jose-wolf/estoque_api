package com.josewolf.estoque_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
    @NotBlank(message = "O produto não pode está vazio")
    @Schema(example = "Samsung S26", required = true, description = "item name")
    String productName,

    @NotBlank(message = "O produto não pode está vazio")
    @Schema(example = "Colors violet, blue, black and white", required = true, description = "Product description")
    String description,

    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que 0.0")
    @NotNull(message = "O campo não pode está nulo")
    @Schema(example = "1000")
    BigDecimal price,

    @Min(value = 0, message = "A quantidade deve ser maior que zero")
    @Schema(example = "1")
    Integer quantity,

    @NotNull(message = "Não pode ser nulo")
    @Schema(example = "belonging to the category", description = "The iPhone belongs to the electronics category.")
    Long categoryId
) {
}
