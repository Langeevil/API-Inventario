package br.com.fatec.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Page<Categoria> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
