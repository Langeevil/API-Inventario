package br.com.fatec.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
        String nome
) {}
