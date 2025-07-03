package com.corporativo.livraria.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Service.Entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
    Optional<Autor> findByNomeAutor(String nomeAutor);

}
