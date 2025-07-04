package com.corporativo.livraria.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.corporativo.livraria.Service.Entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Estoque findByLivroId(Long livroId);

    @Transactional
    void deleteByLivroId(Long livroId);
}
