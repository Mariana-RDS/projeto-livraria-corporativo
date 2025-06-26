package com.corporativo.livraria.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.corporativo.livraria.Entities.EstoqueEntity;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {

    EstoqueEntity findByLivroId(Long livroId);

    @Transactional
    void deleteByLivroId(Long livroId);
}
