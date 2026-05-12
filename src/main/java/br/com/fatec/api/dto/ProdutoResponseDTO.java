package br.com.fatec.api.dto;

import br.com.fatec.api.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados retornados pela API para um produto.")
public record ProdutoResponseDTO(
        @Schema(description = "Identificador do produto.", example = "1")
        Long id,

        @Schema(description = "Nome do produto.", example = "Teclado Mecânico")
        String nome,

        @Schema(description = "Preço do produto.", example = "250.00")
        Double preco,

        @Schema(description = "Categoria do produto.")
        CategoriaResponseDTO categoria) {

    // Método utilitário para converter de Entity para DTO
    public static ProdutoResponseDTO fromEntity(Produto produto) {
        return new ProdutoResponseDTO(
            produto.getId(), 
            produto.getNome(), 
            produto.getPreco(),
            CategoriaResponseDTO.fromEntity(produto.getCategoria())
        );
    }
}
