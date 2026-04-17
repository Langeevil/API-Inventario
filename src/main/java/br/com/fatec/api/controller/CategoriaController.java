package br.com.fatec.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Operation(summary = "Listar categorias com paginação", description = "Retorna uma lista de todas as categorias disponíveis.")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarPaginado(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(service.listarTodosPaginado(nome, page, size).getContent());
    }

    @Operation(summary = "Buscar uma categoria por ID", description = "Retorna os detalhes de uma categoria específica com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar uma nova categoria", description = "Permite criar uma nova categoria fornecendo os detalhes necessários.")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @Operation(summary = "Atualizar uma categoria por ID", description = "Permite atualizar os detalhes de uma categoria existente com base no ID fornecido.")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Excluir uma categoria por ID", description = "Permite excluir uma categoria existente com base no ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
