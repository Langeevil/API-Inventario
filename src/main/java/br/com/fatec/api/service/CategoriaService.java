package br.com.fatec.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fatec.api.dto.CategoriaRequestDTO;
import br.com.fatec.api.dto.CategoriaResponseDTO;
import br.com.fatec.api.model.Categoria;
import br.com.fatec.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaResponseDTO> listarTodos() {
        return repository.findAll(Sort.by("id").ascending())
                .stream()
                .map(CategoriaResponseDTO::fromEntity)
                .toList();
    }

    public Page<CategoriaResponseDTO> listarTodosPaginado(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Categoria> categorias = (nome != null && !nome.isBlank())
                ? repository.findByNomeContainingIgnoreCase(nome, pageable)
                : repository.findAll(pageable);

        return categorias.map(CategoriaResponseDTO::fromEntity);
    }

    public Optional<CategoriaResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(CategoriaResponseDTO::fromEntity);
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        Categoria salvo = repository.save(categoria);
        return CategoriaResponseDTO.fromEntity(salvo);
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria nao encontrada"));

        existente.setNome(dto.nome());

        Categoria atualizado = repository.save(existente);
        return CategoriaResponseDTO.fromEntity(atualizado);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria nao encontrada");
        }
        repository.deleteById(id);
    }
}
