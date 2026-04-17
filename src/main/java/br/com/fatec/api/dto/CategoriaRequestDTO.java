package br.com.fatec.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de entrada para cadastrar ou atualizar uma categoria.")
public record CategoriaRequestDTO(
        @Schema(description = "Nome da categoria.", example = "Eletronicos")
        @NotBlank(message = "O nome e obrigatorio")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String nome
) {}
