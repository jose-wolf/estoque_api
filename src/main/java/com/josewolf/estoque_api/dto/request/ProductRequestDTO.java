package com.josewolf.estoque_api.dto.request;

import com.josewolf.estoque_api.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
    @NotBlank(message = "O produto não pode está vazio")
    String productName,

    @NotBlank(message = "O produto não pode está vazio")
    String description,

    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que 0.0")
    @NotNull(message = "O campo não pode está nulo")
    BigDecimal price,

    @Min(value = 0, message = "A quantidade deve ser maior que zero")
    Integer quantity,

    @NotNull(message = "Não pode ser nulo")
    Long categoryId
) {
}
