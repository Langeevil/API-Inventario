package br.com.fatec.api.controller;

import br.com.fatec.api.dto.UsuarioRequestDTO;
import br.com.fatec.api.dto.UsuarioResponseDTO;
import br.com.fatec.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gerenciamento de usuarios do sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    @Operation(summary = "Lista todos os usuarios com paginacao")
    public ResponseEntity<Page<UsuarioResponseDTO>> listar(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo usuario")
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody @Valid UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }
}
