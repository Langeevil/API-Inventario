package br.com.fatec.api.controller;

import org.springframework.data.domain.Page;
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

import br.com.fatec.api.dto.CategoriaRequestDTO;
import br.com.fatec.api.dto.CategoriaResponseDTO;
import br.com.fatec.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "cadastro e listagem paginada de categorias.")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar categorias com paginacao",
            description = "Retorna categorias paginadas e ordenadas por nome, com filtro opcional por nome."
    )
    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> listarPaginado(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.listarTodosPaginado(nome, page, size));
    }

    @Operation(summary = "Buscar uma categoria por ID", description = "Retorna os detalhes de uma categoria especifica com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar uma nova categoria", description = "Cadastra uma nova categoria.")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @Operation(summary = "Atualizar uma categoria por ID", description = "Atualiza uma categoria existente com base no ID fornecido.")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Excluir uma categoria por ID", description = "Exclui uma categoria existente com base no ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
