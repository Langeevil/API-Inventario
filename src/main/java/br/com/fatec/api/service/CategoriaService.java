package br.com.fatec.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fatec.api.dto.CategoriaRequestDTO;
import br.com.fatec.api.dto.CategoriaResponseDTO;
import br.com.fatec.api.model.Categoria;
import br.com.fatec.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<CategoriaResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(CategoriaResponseDTO::fromEntity)
                .toList();
    }

    public Page<CategoriaResponseDTO> listarTodosPaginado(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Categoria> categorias = (nome != null && !nome.isBlank())
                ? repository.findByNomeContainingIgnoreCase(nome, pageable)
                : repository.findAll(pageable);

        return categorias
                .map(CategoriaResponseDTO::fromEntity);
    }

    // Buscar por ID: Retorna Optional de DTO
    public Optional<CategoriaResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(CategoriaResponseDTO::fromEntity);
    }

    // Salvar: Recebe RequestDTO -> Converte para Entity -> Salva -> Retorna ResponseDTO
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        Categoria salvo = repository.save(categoria);
        return CategoriaResponseDTO.fromEntity(salvo);
    }

    // Atualizar: O ponto onde geralmente ocorre o erro de tipos
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        existente.setNome(dto.nome());

        Categoria atualizado = repository.save(existente);
        return CategoriaResponseDTO.fromEntity(atualizado);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada");
        }
        repository.deleteById(id);
    }

}
