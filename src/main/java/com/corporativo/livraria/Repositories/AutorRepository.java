package com.corporativo.livraria.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Entities.AutorEntity;

public interface AutorRepository extends JpaRepository<AutorEntity, Long>{
    Optional<AutorEntity> findByNomeAutor(String nomeAutor);

}
