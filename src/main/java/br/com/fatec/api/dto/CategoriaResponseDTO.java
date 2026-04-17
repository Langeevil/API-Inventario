package br.com.fatec.api.dto;

import br.com.fatec.api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados retornados pela API para uma categoria.")
public record CategoriaResponseDTO(
        @Schema(description = "Identificador da categoria.", example = "1")
        Long id,

        @Schema(description = "Nome da categoria.", example = "Eletronicos")
        String nome
) {
    public static CategoriaResponseDTO fromEntity(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
    }
}
