package br.com.fatec.api.controller;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Gestao do catalogo de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping("/categoria/{id}")
    @Operation(summary = "Lista produtos filtrando por uma categoria especifica")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarPorCategoria(
            @PathVariable Long id,
            @ParameterObject @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC)
            Pageable paginacao) {

        return ResponseEntity.ok(service.listarPorCategoria(id, paginacao));
    }

    @GetMapping("/busca")
    @Operation(summary = "Busca produto por nome")
    public ResponseEntity<Page<ProdutoResponseDTO>> buscarPorNome(
            @RequestParam(required = false) String nome,
            @ParameterObject @PageableDefault(size = 5, sort = "nome", direction = Sort.Direction.ASC)
            Pageable paginacao) {

        return ResponseEntity.ok(service.listarPaginado(nome, paginacao));
    }

    @GetMapping
    @Operation(summary = "Lista produtos com paginacao")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarPaginado(
            @RequestParam(required = false) String nome,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC)
            Pageable paginacao) {

        return ResponseEntity.ok(service.listarPaginado(nome, paginacao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por id")
    public ResponseEntity<ProdutoResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cria um novo produto")
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto por id")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um produto por id")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
