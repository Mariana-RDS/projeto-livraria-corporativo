package com.corporativo.livraria.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Service.Entities.Editora;

public interface EditoraRepository extends JpaRepository<Editora, Long>{
    Optional<Editora> findByNomeEditora(String nomeEditora);

}
