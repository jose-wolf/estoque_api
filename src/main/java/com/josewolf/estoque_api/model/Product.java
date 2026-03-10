package com.josewolf.estoque_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O produto não pode está vazio")
    @Column(nullable = false, unique = true)
    private String productName;

    @NotBlank(message = "O produto não pode está vazio")
    @Column(nullable = false, length = 150)
    private String description;

    @NotBlank(message = "Deve conter algum valor")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que 0.0")
    @NotNull(message = "O campo não pode está nulo")
    @Column(nullable = false)
    private BigDecimal price;

    @NotBlank(message = "Deve conter algum valor")
    @Min(value = 0, message = "A quantidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Column(nullable = false)
    private Category category;
}
