package br.com.fatec.api.controller;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos") // Prefixo da rota
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    // @GetMapping
    // public List<Produto> listar() {
    //
    // return service.listarTodos();
    // }
    // @GetMapping
    // public List<ProdutoResponseDTO> listar() {
    // return service.listarTodos();
    // }

    // @GetMapping
    // ResponseEntity<List<ProdutoResponseDTO>> listarPaginado(
    //         @RequestParam(defaultValue = "0") int page,
    //         @RequestParam(defaultValue = "10") int size) {
    //     return ResponseEntity.ok(service.listarTodosPaginado(page, size).getContent());
    // }
    @Operation(summary = "Listar produtos com paginação", description = "Retorna uma lista paginada de produtos, permitindo filtrar por nome e controlar o número de itens por página.")
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listarPaginado(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.listarTodosPaginado(nome, page, size));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Produto> buscar(@PathVariable Long id) {
    // return service.buscarPorId(id)
    // .map(ResponseEntity::ok)
    // .orElse(ResponseEntity.notFound().build());
    // }

    // Procurar no insomnia = localhost:8081/api/produtos?page=0&size=5
    @Operation(summary = "Buscar um produto por ID", description = "Retorna os detalhes de um produto específico com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @PostMapping
    // public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
    // return ResponseEntity.status(HttpStatus.CREATED)
    // .body(service.salvar(produto));
    // }
    @Operation(summary = "Criar um novo produto", description = "Adiciona um novo produto ao sistema com os detalhes fornecidos.")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid
    // @RequestBody Produto produto) {
    // try {
    // return ResponseEntity.ok(service.atualizar(id, produto));
    // } catch (RuntimeException e) {
    // return ResponseEntity.notFound().build();
    // }
    // }
    @Operation(summary = "Atualizar um produto por ID", description = "Atualiza os detalhes de um produto existente com base no ID fornecido.")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletar(@PathVariable Long id) {
    // service.deletar(id);
    // return ResponseEntity.noContent().build(); // Retorna 204 No Content
    // }
    @Operation(summary = "Deletar um produto por ID", description = "Exclui um produto do sistema com base no ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}