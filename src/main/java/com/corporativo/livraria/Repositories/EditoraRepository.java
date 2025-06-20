package com.corporativo.livraria.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Entities.EditoraEntity;

public interface EditoraRepository extends JpaRepository<EditoraEntity, Long>{
    Optional<EditoraEntity> findByNomeEditora(String nomeEditora);

}
