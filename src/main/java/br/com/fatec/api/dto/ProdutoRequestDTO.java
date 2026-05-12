package br.com.fatec.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(
        @Schema(description = "Nome do produto", example = "Teclado Mecanico RGB")
        @NotBlank(message = "O nome e obrigatorio")
        @Size(min = 3, message = "O nome deve ter no minimo 3 caracteres")
        String nome,

        @Schema(description = "Preco unitario", example = "250.00")
        @NotNull(message = "O preco e obrigatorio")
        @Positive(message = "O preco deve ser maior que zero")
        Double preco,

        @Schema(description = "ID da categoria vinculada ao produto", example = "1")
        @NotNull(message = "A categoria e obrigatoria")
        Long categoriaId
) {}
