package br.com.fatec.api.dto;

public record CategoriaResponseDTO(
        Long id,
        String nome
) {
    // Método utilitário para converter de Entity para DTO
    public static CategoriaResponseDTO fromEntity(br.com.fatec.api.model.Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
    }
}
