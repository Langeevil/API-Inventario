package br.com.fatec.api.dto;

import br.com.fatec.api.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @Schema(example = "James Campos")
        @NotBlank(message = "O nome e obrigatorio")
        String nome,

        @Schema(example = "james@fatec.sp.gov.br")
        @NotBlank(message = "O email e obrigatorio")
        @Email(message = "Formato de email invalido")
        String email,

        @Schema(example = "senha123")
        @NotBlank(message = "A senha e obrigatoria")
        @Size(min = 6, message = "A senha deve ter no minimo 6 caracteres")
        String senha,

        @NotNull(message = "O perfil (role) e obrigatorio")
        Role role
) {}
