package com.josewolf.estoque_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "Categoria deve possuir um nome")
        @Size(min = 3, message = "A categoria deve possuir mais de 3 caracteres")
        @Schema(example = "Electronics", description = "Name complete")
        String categoryName
) {


}
