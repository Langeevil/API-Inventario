package br.com.fatec.api.service;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.model.Categoria;
import br.com.fatec.api.model.Produto;
import br.com.fatec.api.repository.CategoriaRepository;
import br.com.fatec.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ProdutoResponseDTO::fromEntity)
                .toList();
    }

    public Page<ProdutoResponseDTO> listarTodosPaginado(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listarPaginado(nome, pageable);
    }

    public Page<ProdutoResponseDTO> listarPaginado(String nome, Pageable pageable) {
        Page<Produto> produtos = (nome != null && !nome.isBlank())
                ? repository.findByNomeContainingIgnoreCase(nome, pageable)
                : repository.findAll(pageable);

        return produtos.map(ProdutoResponseDTO::fromEntity);
    }

    public Page<ProdutoResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(ProdutoResponseDTO::fromEntity);
    }

    public Optional<ProdutoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(ProdutoResponseDTO::fromEntity);
    }

    public Page<ProdutoResponseDTO> listarPorCategoria(Long categoriaId, Pageable pageable) {
        return repository.findByCategoriaId(categoriaId, pageable)
                .map(ProdutoResponseDTO::fromEntity);
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Categoria categoria = buscarCategoria(dto.categoriaId());

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);

        return ProdutoResponseDTO.fromEntity(repository.save(produto));
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto nao encontrado"));

        Categoria categoria = buscarCategoria(dto.categoriaId());

        existente.setNome(dto.nome());
        existente.setPreco(dto.preco());
        existente.setCategoria(categoria);

        return ProdutoResponseDTO.fromEntity(repository.save(existente));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Nao e possivel deletar: Produto nao encontrado com ID " + id);
        }

        repository.deleteById(id);
    }

    private Categoria buscarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria nao encontrada"));
    }
}
